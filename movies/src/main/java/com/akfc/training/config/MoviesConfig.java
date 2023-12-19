package com.akfc.training.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.runtime.context.scope.Refreshable;
import lombok.Data;

@Refreshable
@ConfigurationProperties("movies")
@Data
public class MoviesConfig {
    private String title;
    private String description;
}
