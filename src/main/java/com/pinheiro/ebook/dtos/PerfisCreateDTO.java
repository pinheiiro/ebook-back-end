package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.NotBlank;

public record PerfisCreateDTO(
    @NotBlank String nome
) {
}

