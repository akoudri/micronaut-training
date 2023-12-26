package com.akfc.training.mq;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class Consumer {

    @Topic("movies")
    public void receive(@KafkaKey String director, String title) {
        System.out.println("Movie " + title + " created");
    }
}
