package com.akfc.training.controllers;

import com.akfc.training.config.MoviesConfig;
import com.akfc.training.services.Hello;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import reactor.core.publisher.Mono;

@Controller("/hello")
public class HelloController {

    @Value("${movies.title}")
    private String title;

    @Inject
    private MoviesConfig moviesConfig;

    @Inject
    @Named("fr")
    private Hello hello;

    @Get(produces = MediaType.TEXT_PLAIN)
    public Mono<String> sayHello() {
        //return moviesConfig.getTitle();
        return hello.sayHello("Ali");
    }

}
