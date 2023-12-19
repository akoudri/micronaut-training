package com.akfc.training.filters;

import io.micronaut.http.HttpRequest;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class TraceService {

    public void trace(HttpRequest<?> request) {
        //log.debug("Tracing request: {}", request.getUri());
        System.out.println("Tracing request: " + request.getUri());
    }
}
