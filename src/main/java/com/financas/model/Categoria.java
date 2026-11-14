package com.financas.model;

public enum Categoria {
    // Grupos de despesas/receitas
    ALIMENTACAO("Alimentação"),
    MORADIA("Moradia"),
    LAZER("Lazer"),
    TRANSPORTE("Transporte"),
    OUTROS("Outros");

    private final String texto;

    Categoria(String texto) {
        this.texto = texto;
    }
}
