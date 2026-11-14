package com.financas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacao {
    //Responsabilidades: Validações básicas no construtor

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Categoria categoria;
    private TipoTransacao tipoTransacao;

    // (valor não pode ser negativo ou nulo, descrição não pode ser vazia)
    // e toString() formatado para exibição.
}
