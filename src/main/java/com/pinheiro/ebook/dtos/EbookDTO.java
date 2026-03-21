package com.pinheiro.ebook.dtos;

import java.time.LocalDateTime;

public record EbookDTO(
    Long id,
    String titulo,
    String autor,
    String categoria,
    String urlCapa,
    String texto,
    LocalDateTime dataCriacao,
    LocalDateTime ultimaAtualizacao,
    UsuarioDTO usuario
) {
}
