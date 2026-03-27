package com.pinheiro.ebook.dtos;

public record LoginComTokenDTO(
    String token,
    String refreshToken,
    UsuarioDTOComPerfis usuario
) {
}

