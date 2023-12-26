package com.akfc.training.data;

import java.time.LocalDateTime;

public record ClimatEvent(LocalDateTime timestamp, double temperature, double pressure) {}
