package com.akfc.training.services;

import io.micronaut.context.LocalizedMessageSource;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Singleton
public class EnHello implements Hello {

    private final LocalizedMessageSource messageSource;

    @Inject
    public EnHello(LocalizedMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Mono<String> sayHello() {
        return Mono.just(messageSource.getMessage("hello.world", Locale.ENGLISH).orElse("Hello"));
    }
}
