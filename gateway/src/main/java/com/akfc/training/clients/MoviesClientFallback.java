package com.akfc.training.clients;

import com.akfc.training.data.Movie;
import com.akfc.training.data.TemperatureEvent;
import io.micronaut.http.sse.Event;
import io.micronaut.retry.annotation.Fallback;
import io.micronaut.retry.annotation.Recoverable;
import reactor.core.publisher.Flux;

@Fallback
@Recoverable
public class MoviesClientFallback implements MoviesClient {

    @Override
    public Flux<Movie> fetchMovies() {
        return Flux.empty();
    }

    @Override
    public Flux<Event<TemperatureEvent>> fetchTemperatures() {
        return Flux.empty();
    }
}
