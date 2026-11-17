package com.financas.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.util.List;

public record DadosConta(@JsonAlias("limite-mensal") BigDecimal limiteMensal,
                         @JsonAlias("saldo-atual") BigDecimal saldoAtual,
                         @JsonAlias("transacoes") List<DadosTransacao> dadosTransacao) {
}
