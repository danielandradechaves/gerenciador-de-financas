package com.financas.model;

import java.math.BigDecimal;

public record DadosResumo(BigDecimal limiteMensal,
                          BigDecimal totalReceitaDoMes,
                          BigDecimal totalDespesasDoMes,
                          BigDecimal saldoAtual,
                          BigDecimal totalReceitas,
                          BigDecimal totalDespesas) {
}
