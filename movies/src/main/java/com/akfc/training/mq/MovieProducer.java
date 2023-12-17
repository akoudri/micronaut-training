package com.akfc.training.mq;

import com.akfc.training.data.Movie;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("movies")
public interface MovieProducer {

    @Binding("mkey")
    void send(Movie movie);
}
