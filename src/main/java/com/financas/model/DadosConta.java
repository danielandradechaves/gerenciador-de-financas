package com.financas.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosConta(@JsonAlias("limite-mensal") BigDecimal limiteMensal,
                         @JsonAlias("saldo-atual") BigDecimal saldoAtual,
                         @JsonAlias("transacoes") List<DadosTransacao> dadosTransacao) {
}
