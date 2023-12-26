package com.akfc.training.services;

import io.micronaut.context.annotation.Property;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class V2Hello implements Hello {

    //@Value("${movies.message:Hello World}")
    @Property(name = "movies.message")
    private String message;

    @PostConstruct
    public void init() {
        System.out.println("Hello Service loaded");
    }

    @PreDestroy
    public void stop() {
        System.out.println("Hello Service removed");
    }

    @Override
    public Mono<String> sayHello() {
        return Mono.just(message);
    }

}
