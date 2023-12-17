package com.akfc.training.data;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@Introspected
@MappedEntity
public record Movie (
        @Id
        @AutoPopulated
        Long id,
        String title,
        String director,
        String genre,
        String release
){}
