package com.akfc.training;

import com.akfc.training.dto.MovieHeader;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@MicronautTest
public class MovieCtrlTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGetAllMovies() {
        HttpRequest<Object> request = HttpRequest.GET("/movies");
        List<MovieHeader> movies = client.toBlocking().retrieve(request, Argument.listOf(MovieHeader.class));
        // Assertions
        assertEquals(89, movies.size()); // Replace 'expectedSize' with the expected number of movies
    }

}
