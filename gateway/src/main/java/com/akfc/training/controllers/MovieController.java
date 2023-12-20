package com.akfc.training.controllers;

import com.akfc.training.clients.MoviesClient;
import com.akfc.training.data.Movie;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.retry.annotation.Retryable;
import reactor.core.publisher.Flux;

@Controller
public class MovieController {

    private final MoviesClient moviesClient;

    public MovieController(MoviesClient moviesClient) {
        this.moviesClient = moviesClient;
    }

    @Get("/movies")
    @Retryable(attempts = "5", delay = "200ms", multiplier = "1.5", includes = {Exception.class})
    public Flux<Movie> findAll() {
        return moviesClient.fetchMovies();
    }
}
