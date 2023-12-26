package com.akfc.training.data;

import java.time.LocalDateTime;

public record MessageEvent (
   LocalDateTime timestamp,
   String message
) {}
