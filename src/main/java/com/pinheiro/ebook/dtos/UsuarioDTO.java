package com.pinheiro.ebook.dtos;

import java.time.LocalDateTime;

public record UsuarioDTO(
    Long id,
    String nome,
    String email,
    LocalDateTime dataCriacao,
    LocalDateTime ultimaAtualizacao
) {
}
