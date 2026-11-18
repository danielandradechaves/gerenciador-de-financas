package com.financas.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    // limite de gastos
    private BigDecimal limiteMensal;
    // saldo na conta
    private BigDecimal saldoAtual;
    private List<Transacao> transacoes;

    public ContaFinanceira() {
        this.limiteMensal = BigDecimal.ZERO;
        this.saldoAtual = BigDecimal.ZERO;
        this.transacoes = new ArrayList<>();
    }

    public ContaFinanceira(DadosConta dadosConta) {
        this.limiteMensal = dadosConta.limiteMensal();
        this.saldoAtual = dadosConta.saldoAtual();
        if (dadosConta.dadosTransacao() == null || dadosConta.dadosTransacao().isEmpty()) {
            this.transacoes = new ArrayList<>();
        } else {
            this.transacoes = dadosConta.dadosTransacao().stream()
                    .map(Transacao::new).toList();
        }
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public List<Transacao> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }

    public void atualizarLimite(BigDecimal novoLimite) {
        limiteMensal = novoLimite;
    }

    public void addTransacao(Transacao novaTransacao) {
        transacoes.add(novaTransacao);
    }

    public void calcularSaldo() {
        BigDecimal receitas = calcularTotalReceitas();
        BigDecimal despesas = calcularTotalDespesas();

        saldoAtual = receitas.subtract(despesas);
    }

    public BigDecimal calcularTotalReceitas() {
        BigDecimal totalReceitas = BigDecimal.ZERO;
        for (Transacao t : transacoes) {
            if (t.getTipoTransacao() == TipoTransacao.RECEITA) {
                totalReceitas = totalReceitas.add(t.getValor());
            }
        }
        return totalReceitas;
    }

    public BigDecimal calcularTotalDespesas() {
        BigDecimal totalDespesas = BigDecimal.ZERO;
        for (Transacao t: transacoes)
        if (t.getTipoTransacao() == TipoTransacao.DESPESA) {
            totalDespesas = totalDespesas.add(t.getValor());
        }
        return totalDespesas;
    }

    public boolean limiteUltrapassado() {
        if(limiteMensal == null || limiteMensal.compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }
        return calcularTotalDespesas().compareTo(limiteMensal) > 0;
    }

    public boolean saldoNegativo() {
        if (saldoAtual.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }
        return false;
    }

    //Métodos utilitários de cálculo:
    //calcularTotalReceitas()
    //calcularTotalDespesas()
    //calcularSaldo() (Receitas - Despesas).
}
