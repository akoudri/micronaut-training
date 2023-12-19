package com.akfc.training.controllers;

import com.akfc.training.clients.MoviesClient;
import com.akfc.training.data.Movie;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;

@Controller
public class MovieController {

    private final MoviesClient moviesClient;

    public MovieController(MoviesClient moviesClient) {
        this.moviesClient = moviesClient;
    }

    @Get("/movies")
    public Flux<Movie> findAll() {
        return moviesClient.fetchMovies();
    }
}
