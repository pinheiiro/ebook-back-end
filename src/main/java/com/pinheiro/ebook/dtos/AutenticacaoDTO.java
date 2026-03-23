package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String senha
) {
}
