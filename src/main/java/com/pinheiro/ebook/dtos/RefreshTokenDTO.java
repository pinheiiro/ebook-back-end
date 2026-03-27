package com.pinheiro.ebook.dtos;

import java.time.Instant;

public record RefreshTokenDTO(
    Long id,
    String token,
    Instant dataExpiracao,
    UsuarioDTO usuario
) {
}

