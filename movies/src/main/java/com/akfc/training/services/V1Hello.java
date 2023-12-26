package com.akfc.training.services;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Prototype
@Primary
public class V1Hello implements Hello {

    private String message = "Bonjour Micronaut";
    private LocalDateTime date;

    @PostConstruct
    public void init() {
        date = LocalDateTime.now();
    }

    @PreDestroy
    public void stop() {
        System.out.println("Hello Service supprimé");
    }

    @Override
    public Mono<String> sayHello() {
        return Mono.just(message + " " + date);
    }

}
