package com.akfc.training.dao;

import com.akfc.training.data.Movie;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.H2)
public interface MovieRepo extends ReactorCrudRepository<Movie, Long> {

    @NonNull
    @Override
    Mono<Movie> findById(@NonNull Long id);

    @NonNull
    @Override
    Flux<Movie> findAll();

}
