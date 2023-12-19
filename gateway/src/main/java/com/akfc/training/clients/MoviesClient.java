package com.akfc.training.clients;

import com.akfc.training.data.Movie;
import com.akfc.training.data.TemperatureEvent;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.sse.Event;
import reactor.core.publisher.Flux;

@Client("movies")
public interface MoviesClient {

    @Get("/movies")
    Flux<Movie> fetchMovies();

    @Get(value = "/temperature-stream", processes = MediaType.TEXT_EVENT_STREAM)
    Flux<Event<TemperatureEvent>> fetchTemperatures();

}
