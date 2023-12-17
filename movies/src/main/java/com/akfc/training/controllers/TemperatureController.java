package com.akfc.training.controllers;

import com.akfc.training.dto.TemperatureEvent;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Controller("/temperature-stream")
public class TemperatureController {

    private Random random = new Random();

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = MediaType.TEXT_EVENT_STREAM)
    public Flux<Object> index() {
        return Flux.generate(sink -> sink.next(Event.of(new TemperatureEvent(random.nextDouble(50.0)))))
                .sample(Duration.ofSeconds(1));
    }
}
