package com.akfc.training.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class MoviesFactory {

    @Singleton
    public MoviesProvider getProvider() {
        return new MoviesProvider();
    }

}
