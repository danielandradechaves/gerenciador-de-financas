package com.financas.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    private BigDecimal limiteMensal;
    private BigDecimal saldoAtual;
    private List<Transacao> transacoes;

    public ContaFinanceira() {
        this.limiteMensal = BigDecimal.ZERO;
        this.saldoAtual = limiteMensal;
        this.transacoes = new ArrayList<>();
    }

    public ContaFinanceira(DadosConta dadosConta) {
        this.limiteMensal = dadosConta.limiteMensal();
        this.saldoAtual = dadosConta.saldoAtual();
        this.transacoes = dadosConta.dadosTransacao().stream()
                .map(Transacao::new).toList();
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void atualizarLimite(BigDecimal novoLimite) {
        limiteMensal = novoLimite;
    }

    public void atualizarSaldoReceita(BigDecimal valor) {
        saldoAtual = saldoAtual.add(valor);
    }

    public void atualizarSaldoDespesa(BigDecimal valor) {
        saldoAtual = saldoAtual.subtract(valor);
    }

    public void addTransacao(Transacao novaTransacao) {
        transacoes.add(novaTransacao);
    }

    //Métodos utilitários de cálculo:
    //calcularTotalReceitas()
    //calcularTotalDespesas()
    //calcularSaldo() (Receitas - Despesas).
}
