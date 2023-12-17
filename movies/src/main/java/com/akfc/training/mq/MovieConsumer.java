package com.akfc.training.mq;

import com.akfc.training.data.Movie;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class MovieConsumer {

    @Queue("mqueue")
    public void receive(Movie movie) {
        System.out.println("Movie received :" + movie);
    }

}
