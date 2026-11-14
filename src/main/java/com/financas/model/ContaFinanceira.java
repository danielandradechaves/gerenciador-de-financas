package com.financas.model;

import java.math.BigDecimal;
import java.util.List;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    private BigDecimal limiteMensal;
    private List<Transacao> transacoes;

    //Métodos utilitários de cálculo:
    //calcularTotalReceitas()
    //calcularTotalDespesas()
    //calcularSaldo() (Receitas - Despesas).
}
