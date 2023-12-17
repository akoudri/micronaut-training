package com.akfc.training.services;

import com.akfc.training.dao.MovieRepo;
import com.akfc.training.data.Movie;
import com.akfc.training.dto.SWMovie;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.micronaut.context.annotation.Value;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class MovieManager {

    private boolean initizalized = false;
    private long movieCount = 0;

    @Inject
    private MovieRepo repo;

    @Value("${r2dbc.datasources.default.url}")
    private String dbUrl;

    @Value("${r2dbc.datasources.default.username}")
    private String dbUser;

    @Value("${r2dbc.datasources.default.password}")
    private String dbPassword;

    private ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions
                .builder()
                .option(ConnectionFactoryOptions.DRIVER, "h2")
                .option(ConnectionFactoryOptions.PROTOCOL, "mem") // for in-memory database
                .option(ConnectionFactoryOptions.DATABASE, "multimedia;DB_CLOSE_DELAY=-1") // DB name and options
                .option(ConnectionFactoryOptions.USER, dbUser)
                .option(ConnectionFactoryOptions.PASSWORD, dbPassword)
                .build();
        return ConnectionFactories.get(options);
    }

    private void init() {
        System.out.println("Feeding database");
        Flux.just("Quentin Tarantino", "Steven Spielberg", "David Lynch", "David Fincher")
                .flatMap(director -> {
                    try {
                        return findMoviesByDirector(director);
                    } catch (Exception ex) {
                        return Flux.error(ex); // Convert the exception to a Flux error
                    }
                })
                .flatMap(m -> repo.save(new Movie(++movieCount, m.title().replace("'", "''"),
                        m.director(), String.join(", ", m.genre()),
                        m.release())))
                .doOnError(err -> System.err.println("Error: " + err.getMessage()))
                .doOnComplete(() -> initizalized = true)
                .subscribe();
    }

    private Flux<SWMovie> findMoviesByDirector(String director) throws Exception {
        return getMoviesByDirector(director)
                .map(s -> {
                    JsonArray res = JsonParser.parseReader(new StringReader(s)).getAsJsonObject().getAsJsonObject("results").getAsJsonArray("bindings");
                    List<SWMovie> movies = new ArrayList<>();
                    Optional<SWMovie> current;
                    for (int i  = 0; i < res.size(); i++) {
                        JsonObject m = (JsonObject) res.get(i);
                        String title = m.getAsJsonObject("movieLabel").getAsJsonPrimitive("value").getAsString();
                        String genre = m.getAsJsonObject("genreLabel").getAsJsonPrimitive("value").getAsString();
                        String release = m.getAsJsonObject("release").getAsJsonPrimitive("value").getAsString();
                        current = movies.stream().filter(e -> e.title().equalsIgnoreCase(title)).findFirst();
                        if (current.isPresent()) {
                            if (! current.get().genre().contains(genre)) current.get().genre().add(genre);
                        } else {
                            Pattern p = Pattern.compile("^Q\\d+");
                            Matcher matcher = p.matcher(title);
                            if (matcher.matches()) continue;
                            movies.add(new SWMovie(title, director, new ArrayList<>(Arrays.asList(genre)), release));
                        }
                    }
                    return movies;
                }).flatMapIterable(e -> e);
    }

    private Mono<String> getMoviesByDirector(String director) throws Exception {
        String[] names = director.split(" ");
        String request = String.format("""
                SELECT DISTINCT ?movieLabel ?directorLabel ?genreLabel ?release WHERE {
                                        SERVICE wikibase:label { bd:serviceParam wikibase:language "fr". }
                                        {
                                            SELECT DISTINCT ?movie ?director ?genre ?release WHERE {
                                                ?movie wdt:P57 ?director; wdt:P136 ?genre; wdt:P577 ?release.
                                                ?director wdt:P735 ?gn; wdt:P734 ?fn.
                                                ?gn rdfs:label "%s"@en.
                                                ?fn rdfs:label "%s"@en.
                                            } LIMIT 500
                                        }
                                    }
                """, names[0], names[1]);
        URL url = new URL("https://query.wikidata.org/sparql?query=" + URLEncoder.encode(request, "UTF-8"));
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpRequest req = HttpRequest.newBuilder().GET().uri(url.toURI()).header("Accept", "application/json").build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return Mono.just(response.body());
    }

    public Flux<Movie> getAll() throws Exception {
        if (! initizalized) init();
        return repo.findAll();
    }
}
