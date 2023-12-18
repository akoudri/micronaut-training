package com.akfc.training.controllers;

import com.akfc.training.dto.MovieHeader;
import com.akfc.training.data.Movie;
import com.akfc.training.services.MovieManager;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/movies")
public class MovieController {

    @Inject
    private MovieManager manager;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<MovieHeader> getAll() {
        return manager.getAll()
                .map(movie -> new MovieHeader(movie.getId(), movie.getTitle()));
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<Movie> getById(Long id) {
        return manager.getById(id);
    }

    @Get(value = "/director", produces = MediaType.APPLICATION_JSON)
    public Flux<Movie> getByDirector(@QueryValue String name) {
        return manager.getByDirector(name);
    }

    @Post(value = "/create", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public Mono<Movie> addMovie(@Body Movie movie) {
        return manager.addMovie(movie);
    }

    @Delete(value = "/director")
    public void deleteMoviesByDirector(@QueryValue String name) {
        manager.deleteMoviesByDirector(name);
    }
}
