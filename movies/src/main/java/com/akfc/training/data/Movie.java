package com.akfc.training.data;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Data;

@Introspected
@MappedEntity
@Data
public class Movie {

        @Id
        @GeneratedValue(GeneratedValue.Type.AUTO)
        private Long id;
        private String title;
        private String director;
        private String genre;
        private String release;

        public Movie(String title, String director, String genre, String release) {
                this.title = title;
                this.director = director;
                this.genre = genre;
                this.release = release;
        }
}
