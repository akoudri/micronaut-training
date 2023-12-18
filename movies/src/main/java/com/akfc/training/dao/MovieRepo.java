package com.akfc.training.dao;

import com.akfc.training.data.Movie;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;

@R2dbcRepository(dialect = Dialect.H2)
public interface MovieRepo extends ReactorCrudRepository<Movie, Long> {}
