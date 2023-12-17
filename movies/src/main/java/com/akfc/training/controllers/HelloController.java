package com.akfc.training.controllers;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

    @Value("${movies.title}")
    private String title;

    @Get(produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return title;
    }

}
