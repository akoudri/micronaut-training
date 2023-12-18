package com.akfc.training.controllers;

import com.akfc.training.config.MoviesConfig;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/hello")
public class HelloController {

    @Value("${movies.title}")
    private String title;

    @Inject
    private MoviesConfig moviesConfig;

    @Get(produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return moviesConfig.getTitle();
    }

}
