package com.akfc.training;

import com.akfc.training.dao.MovieRepo;
import com.akfc.training.dto.SWMovie;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import jakarta.inject.Inject;
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

public class Application implements ApplicationEventListener<StartupEvent> {

    @Inject
    private MovieRepo repo;

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        init();
    }

    private void init() {
        System.out.println("Feeding database");
        //TODO: Feed the database for the following directors:
        //"Quentin Tarantino", "Steven Spielberg", "David Lynch", "David Fincher"
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
}