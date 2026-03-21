package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.NotBlank;

public record EbookCreateDTO(
    @NotBlank String titulo,
    @NotBlank String autor,
    @NotBlank String categoria,
    String urlCapa,
    @NotBlank String texto
) {
}
