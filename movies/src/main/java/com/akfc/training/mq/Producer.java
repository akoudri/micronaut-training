package com.akfc.training.mq;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface Producer {

    @Topic("movies")
    void sendMovie(@KafkaKey String director, String title);

}
