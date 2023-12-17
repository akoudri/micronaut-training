package com.akfc.training.config;

import com.akfc.training.data.Movie;
import io.micronaut.retry.annotation.CircuitBreaker;
import io.micronaut.retry.annotation.Retryable;
import io.micronaut.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

public class MoviesProvider {

    @CircuitBreaker(reset = "30s")
    public Flux<Movie> findMovies() {
        return Flux.empty();
    }

    @Retryable(attempts = "3", delay = "1s")
    public Flux<Movie> getAll() {
        return Flux.empty();
    }

    @Scheduled(fixedRate = "5m")
    public void updateMovies() {
        //TODO
    }

}
