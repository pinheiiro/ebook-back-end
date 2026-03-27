package com.pinheiro.ebook.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioDTOComPerfis(
    Long id,
    String nome,
    String email,
    LocalDateTime dataCriacao,
    LocalDateTime ultimaAtualizacao,
    List<PerfisDTO> perfis
) {
}

