package com.akfc.training.controllers;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;

@Controller("/config")
public class ConfigController {

    private final ApplicationEventPublisher<RefreshEvent> publisher;

    public ConfigController(ApplicationEventPublisher<RefreshEvent> publisher) {
        this.publisher = publisher;
    }

    @Post("/refresh")
    public void refreshConfig() {
        publisher.publishEvent(new RefreshEvent());
    }

}
