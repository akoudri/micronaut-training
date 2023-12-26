package com.akfc.training.services;

import com.akfc.training.dao.MovieRepo;
import com.akfc.training.data.Movie;
import com.akfc.training.mq.Producer;
import io.micronaut.cache.annotation.Cacheable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class MovieManager {

    @Inject
    private MovieRepo repo;

    @Inject
    private Producer producer;

    //private Cache<Long, Mono<Movie>> movieCache;

    /*@PostConstruct
    void init() {
        movieCache = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }*/

    public Flux<Movie> getAll() {
        return repo.findAll();
    }

    @Cacheable("movieCache")
    public Mono<Movie> getById(Long id) {
        /*Mono<Movie> cachedMovie = movieCache.getIfPresent(id);
        if (cachedMovie != null) {
            return cachedMovie;
        }
        Mono<Movie> movieMono = repo.findById(id).cache();
        movieCache.put(id, movieMono);
        return movieMono;*/
        return repo.findById(id);
    }


    @Cacheable(cacheNames = "directorMoviesCache", parameters = {"director"}) //Not compatible with reactor
    public Flux<Movie> getByDirector(String director) {
        return repo.findByDirector(director);
    }

    public Mono<Movie> addMovie(Movie movie) {
        return repo.save(movie)
                .doOnNext(m -> producer.sendMovie(movie.getDirector(), movie.getTitle()));
    }

    public void deleteMoviesByDirector(String director) {
        repo.findByDirector(director)
                .flatMap(movie -> repo.deleteById(movie.getId()))
                .then()
                .subscribe();
    }
}
