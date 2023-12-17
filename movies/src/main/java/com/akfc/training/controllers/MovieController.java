package com.akfc.training.controllers;

import com.akfc.training.dao.MovieRepo;
import com.akfc.training.data.Movie;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;

@Controller("/movies")
public class MovieController {

    @Inject
    private MovieRepo repo;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<Movie> getAll() throws Exception {
        return repo.findAll();
    }
}
