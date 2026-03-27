package com.pinheiro.ebook.dtos;

import java.time.LocalDateTime;

public record ErrorDTO(
    LocalDateTime timestamp,
    int status,
    String error,
    String message
) {
}

