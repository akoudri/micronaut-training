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

    @Inject
    private MovieRepo repo;

    public Flux<Movie> getAll() {
        return repo.findAll();
    }

    public Mono<Movie> getById(Long id) {
        return repo.findById(id);
    }

    public Flux<Movie> getByDirector(String director) {
        return repo.findByDirector(director);
    }

    public Mono<Movie> addMovie(Movie movie) {
        return repo.save(movie);
    }

    public void deleteMoviesByDirector(String director) {
        repo.findByDirector(director)
                .flatMap(movie -> repo.deleteById(movie.getId()))
                .then()
                .subscribe();
    }
}
