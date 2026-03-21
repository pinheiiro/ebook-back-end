package com.pinheiro.ebook.dtos;

public record EbookUpdateDTO(
    String titulo,
    String autor,
    String categoria,
    String urlCapa,
    String texto
) {
}
