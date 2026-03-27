package com.pinheiro.ebook.excecoes;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String mensagem) {
        super(mensagem);
    }

    public TokenExpiradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

