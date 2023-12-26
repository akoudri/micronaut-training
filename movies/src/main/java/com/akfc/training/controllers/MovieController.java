package com.akfc.training.controllers;

import com.akfc.training.dto.MovieHeader;
import com.akfc.training.data.Movie;
import com.akfc.training.services.MovieManager;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller("/movies")
public class MovieController {

    @Inject
    private MovieManager manager;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<MovieHeader> getAll() {
        /*return manager.getAll()
                .map(movie -> new MovieHeader(movie.getId(), movie.getTitle()));*/
        return Mono.delay(Duration.ofSeconds(5))
                .thenMany(manager.getAll()
                        .map(movie -> new MovieHeader(movie.getId(), movie.getTitle())));
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<Movie>> getById(Long id) {
        return manager.getById(id)
                .map(HttpResponse::ok)  // Wrap the Movie in an HttpResponse
                .switchIfEmpty(Mono.just(HttpResponse.notFound()))  // Return HttpResponse.notFound for empty results
                .onErrorResume(e -> Mono.just(HttpResponse.<Movie>status(HttpStatus.INTERNAL_SERVER_ERROR)  // Ensure HttpResponse<Movie> is returned
                        .body(null)));  // You can choose to not send a body or send a custom error message object
    }

    @Get(value = "/director", produces = MediaType.APPLICATION_JSON)
    public Flux<Movie> getByDirector(@QueryValue String name) {
        return manager.getByDirector(name);
    }

    @Post(value = "/create", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Movie>> addMovie(@Body @Valid Movie movie) {
        return manager.addMovie(movie).map(HttpResponse::created);
    }

    @Delete(value = "/director")
    public void deleteMoviesByDirector(@QueryValue String name) {
        manager.deleteMoviesByDirector(name);
    }
}
