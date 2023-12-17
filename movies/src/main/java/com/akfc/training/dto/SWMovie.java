package com.akfc.training.dto;

import java.util.List;

public record SWMovie(String title, String director, List<String> genre, String release) {
}
