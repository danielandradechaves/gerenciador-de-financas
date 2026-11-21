package com.financas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ContaFinanceira {
    // Manter a lista de transações protegida.

    // limite de gastos
    private BigDecimal limiteMensal;
    // saldo na conta
    private BigDecimal saldoAtual;
    private final List<Transacao> transacoes;

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
            this.transacoes = new ArrayList<>(dadosConta.dadosTransacao()
                    .stream()
                    .map(Transacao::new)
                    .toList());
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

    public List<DadosTransacao> getDadosTransacoes() {
        return transacoes.stream()
                .map(t -> new DadosTransacao(t.getId(),
                        t.getDescricao(),
                        t.getValor(),
                        t.getData(),
                        t.getCategoria(),
                        t.getTipoTransacao()))
                .toList();
    }

    public void atualizarLimite(BigDecimal novoLimite) {
        limiteMensal = novoLimite;
    }

    public void addTransacao(Transacao novaTransacao) {
        transacoes.add(novaTransacao);
        if(novaTransacao.getTipoTransacao() == TipoTransacao.RECEITA) {
            saldoAtual = saldoAtual.add(novaTransacao.getValor());
        } else {
            saldoAtual = saldoAtual.subtract(novaTransacao.getValor());
        }
    }

    public BigDecimal calcularMesAno(YearMonth mesAno, TipoTransacao tipo) {
        // soma todas as RECEITAS/DESPESAS conforme o mês e o ano
        return transacoes.stream()
                .filter(t -> t.getTipoTransacao() == tipo)
                .filter(t -> YearMonth.from(t.getData()).equals(mesAno))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotal(TipoTransacao tipo) {
        // soma todas as RECEITAS/DESPESAS da conta
        return transacoes.stream()
                .filter(t -> t.getTipoTransacao() == tipo)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<YearMonth, List<Transacao>> getTransacoesPorMesEAno() {
        return transacoes.stream()
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.getData()),
                        TreeMap::new,
                        Collectors.toList()));
    }

    public boolean limiteUltrapassado(BigDecimal valor) {
        if (limiteMensal == null || limiteMensal.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return calcularMesAno(YearMonth.now(), TipoTransacao.DESPESA).add(valor).compareTo(limiteMensal) > 0;
    }

    public boolean saldoNegativo(BigDecimal valor) {
        return saldoAtual.subtract(valor).compareTo(BigDecimal.ZERO) < 0;
    }
}
