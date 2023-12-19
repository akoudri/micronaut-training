package com.akfc.training.services;

import com.akfc.training.config.MoviesConfig;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Singleton
@Primary
public class FrHello implements Hello {
    //private final LocalizedMessageSource messageSource;
    private final MoviesConfig config;

    @Inject
    public FrHello(MoviesConfig config) {
        this.config = config;
    }

    @Override
    public Mono<String> sayHello() {
        return Mono.just(config.getTitle());
    }
}
