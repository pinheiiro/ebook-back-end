package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.NotBlank;

public record PerfisUpdateDTO(
    @NotBlank String nome
) {
}

