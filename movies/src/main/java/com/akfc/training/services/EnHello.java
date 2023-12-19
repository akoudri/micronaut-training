package com.akfc.training.services;

import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class EnHello implements Hello {

    @Override
    public Mono<String> sayHello(String name) {
        return Mono.just("Bonjour " + name);
    }

}
