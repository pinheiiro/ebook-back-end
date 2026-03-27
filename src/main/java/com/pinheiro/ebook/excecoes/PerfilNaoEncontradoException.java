package com.pinheiro.ebook.excecoes;

public class PerfilNaoEncontradoException extends RuntimeException {
    public PerfilNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PerfilNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

