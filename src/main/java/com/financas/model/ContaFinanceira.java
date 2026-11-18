package com.financas.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    // limite de gastos
    private BigDecimal limiteMensal;
    // gastos do mês (volta a zero todo mês)
    private BigDecimal totalGasto;
    // saldo na conta
    private BigDecimal saldoAtual;
    private List<Transacao> transacoes;

    public ContaFinanceira() {
        this.limiteMensal = BigDecimal.ZERO;
        this.totalGasto = BigDecimal.ZERO;
        this.saldoAtual = BigDecimal.ZERO;
        this.transacoes = new ArrayList<>();
    }

    public ContaFinanceira(DadosConta dadosConta) {
        this.limiteMensal = dadosConta.limiteMensal();
        this.totalGasto = dadosConta.totalGasto();
        this.saldoAtual = dadosConta.saldoAtual();
        if(dadosConta.dadosTransacao() == null || dadosConta.dadosTransacao().isEmpty()) {
            this.transacoes = new ArrayList<>();
        }else{
            this.transacoes = dadosConta.dadosTransacao().stream()
                    .map(Transacao::new).toList();
        }
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public BigDecimal getTotalGasto() {
        return totalGasto;
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
        totalGasto = totalGasto.add(valor);
    }

    public void addTransacao(Transacao novaTransacao) {
        transacoes.add(novaTransacao);
    }

    public boolean limiteUltrapassado() {
        BigDecimal limiteAtual = transacoes.stream()
                .filter(t -> t.getTipoTransacao() == TipoTransacao.DESPESA)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Retorna true apenas se limiteAtual for MAIOR que limiteMensal
        if(limiteAtual.compareTo(limiteMensal) >= 0) {
            return true;
        }
        return false;
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
