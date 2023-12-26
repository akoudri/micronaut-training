package com.akfc.training.services;

import com.akfc.training.data.MessageEvent;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@Context
public class MessageEmitter {

    @Inject
    private ApplicationEventPublisher<MessageEvent> publisher;

    @Scheduled(fixedDelay = "5s")
    public void publish() {
        publisher.publishEvent(new MessageEvent(LocalDateTime.now(), "Hello world"));
    }

}
