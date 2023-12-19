package com.akfc.training.data;

import java.time.LocalDate;

public record Movie(long id, String title, String director, String genre, LocalDate release) {}
