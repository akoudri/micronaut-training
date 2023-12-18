package com.akfc.training.services;

import com.akfc.training.dao.MovieRepo;
import com.akfc.training.data.Movie;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Singleton
public class MovieManager {

    @Inject
    private MovieRepo repo;

    public Flux<Movie> getAll() throws Exception {
        return repo.findAll();
    }
}
