package com.akfc.training.services;

import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class FrHello implements Hello {

    @Override
    public Mono<String> sayHello(String name) {
        return Mono.just("Bonjour " + name);
    }
}
