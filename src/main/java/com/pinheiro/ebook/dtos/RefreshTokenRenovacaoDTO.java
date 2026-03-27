package com.pinheiro.ebook.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRenovacaoDTO(
    @NotBlank String refreshToken
) {
}

