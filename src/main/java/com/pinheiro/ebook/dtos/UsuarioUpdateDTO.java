package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDTO(
    @NotBlank String nome,
    @Email String email,
    @NotBlank String senha
) {
}
