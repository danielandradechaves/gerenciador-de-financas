package com.financas.model;

public enum TipoTransacao {
    // Diferenciar entradas e saídas
    RECEITA(1),
    DESPESA(2);

    private final int id;

    TipoTransacao(int id) {
        this.id = id;
    }
}
