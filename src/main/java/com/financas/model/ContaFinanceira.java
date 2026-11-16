package com.financas.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    private BigDecimal limiteMensal;
    private List<Transacao> transacoes;

    public ContaFinanceira() {
        limiteMensal = BigDecimal.ZERO;
        transacoes = new ArrayList<>();
    }

    public ContaFinanceira(DadosConta dadosConta) {
        this.limiteMensal = dadosConta.limiteMensal();

        this.transacoes = dadosConta.dadosTransacao().stream()
                .map(Transacao::new).toList();
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void atualizarLimite(BigDecimal novoLimite) {
        limiteMensal = novoLimite;
    }

    public void addTransacao(Transacao novaTransacao) {
        transacoes.add(novaTransacao);
    }

    //Métodos utilitários de cálculo:
    //calcularTotalReceitas()
    //calcularTotalDespesas()
    //calcularSaldo() (Receitas - Despesas).
}
