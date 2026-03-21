package com.pinheiro.ebook.enums;

public enum GrupoUsuario {
    ADMIN("admin"),
    USER("user");

    private String grupo;

    private GrupoUsuario(String grupo) {
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }
}
