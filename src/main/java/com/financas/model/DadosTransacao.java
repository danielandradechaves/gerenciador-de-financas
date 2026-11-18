package com.financas.model;


import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosTransacao (String id,
                              String descricao,
                              BigDecimal valor,
                              LocalDate data,
                              Categoria categoria,
                              @JsonAlias("tipo-transacao") TipoTransacao tipoTransacao){
}
