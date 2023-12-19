package com.akfc.training.components;

import com.akfc.training.clients.MoviesClient;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

@Context
public class TemperatureActuator {

    private final Logger LOGGER = LoggerFactory.getLogger(TemperatureActuator.class);

    private final MoviesClient client;

    public TemperatureActuator(MoviesClient client) {
        this.client = client;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Listening for temperatures");
        client.fetchTemperatures().subscribe(e -> System.out.println("Actuator: receiving temperature " +
                new DecimalFormat("##.##").format(e.getData().value())));
    }

}
