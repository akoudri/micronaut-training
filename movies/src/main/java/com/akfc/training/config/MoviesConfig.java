package com.akfc.training.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("movies")
public class MoviesConfig {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
