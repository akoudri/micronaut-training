package com.akfc.training.controllers;

import com.akfc.training.data.ClimatEvent;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Controller("/climat-stream")
public class ClimatController {

    private Random random = new Random();

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = MediaType.TEXT_EVENT_STREAM)
    public Flux<ClimatEvent> getClimat() {
        return Flux.generate(sink -> sink.next(new ClimatEvent(LocalDateTime.now(), random.nextDouble(50), random.nextDouble(10))))
                .sample(Duration.ofSeconds(2)).cast(ClimatEvent.class);
    }

}
