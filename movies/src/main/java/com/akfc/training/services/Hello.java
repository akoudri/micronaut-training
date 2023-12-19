package com.akfc.training.services;

import reactor.core.publisher.Mono;

public interface Hello {
    Mono<String> sayHello(String name);
}
