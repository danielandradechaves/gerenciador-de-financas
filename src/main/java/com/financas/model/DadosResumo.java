package com.financas.model;

import java.math.BigDecimal;

public record DadosResumo(BigDecimal limiteMensal,
                          BigDecimal totalGasto,
                          BigDecimal saldoAtual,
                          BigDecimal totalReceitas,
                          BigDecimal totalDespesas) {
}
