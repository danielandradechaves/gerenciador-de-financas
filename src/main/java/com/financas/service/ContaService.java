package com.financas.service;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.*;

import java.math.BigDecimal;
import java.util.List;

public class ContaService {

    private ContaFinanceira conta;

    public ContaService(ContaFinanceira conta) {
        this.conta = conta;
    }

    public void definirLimite(BigDecimal novoLimite) {
        if (novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        conta.atualizarLimite(novoLimite);
    }

    public void registrarReceita(String descricao, BigDecimal valor, Categoria categoria) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        ;
        conta.atualizarSaldoReceita(valor);

        Transacao receita = new Transacao(descricao, valor, categoria, TipoTransacao.RECEITA);
        conta.addTransacao(receita);

        System.out.println(receita);
        System.out.println("Saldo atual: " + conta.getSaldoAtual() + " R$");
    }

    public void registrarDespesa(String descricao, BigDecimal valor, Categoria categoria) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }

        // Verifica se o saldo é negativo antes de atualizar a conta
        BigDecimal verificaSaldo = conta.getSaldoAtual().subtract(valor);
        if (verificaSaldo.compareTo(BigDecimal.ZERO) < 0) {
            // Se ultrapassar, lança a exceção e não insere a despesa (ou emite o aviso conforme o fluxo desejado).
            throw new LimiteUltrapassadoException("Transação não concluída: O limite Mensal de R$ " +
                    conta.getLimiteMensal() + " foi ultrapassado");
        }

        conta.atualizarSaldoDespesa(valor);

        Transacao despesa = new Transacao(descricao, valor, categoria, TipoTransacao.DESPESA);
        conta.addTransacao(despesa);

        System.out.println(despesa);
        System.out.println("Saldo atual: " + conta.getSaldoAtual() + " R$");
    }

    public List<Transacao> listarTransacoes() {
        return conta.getTransacoes();
    }

    public void resumoDeFinancas() {
        List<Transacao> transacoes = conta.getTransacoes();
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;
        BigDecimal saldoAtual = conta.getSaldoAtual();
        // limite definido vs total gasto até agora
        // gastos agrupados por categoria
        if (transacoes.size() > 0) {
            for (Transacao t : transacoes) {
                if (t.getTipoTransacao() == TipoTransacao.RECEITA) {
                    totalReceitas = totalReceitas.add(t.getValor());
                } else if (t.getTipoTransacao() == TipoTransacao.DESPESA) {
                    totalDespesas = totalDespesas.add(t.getValor());
                }
            }
        }
            System.out.println("Limite mensal: " + conta.getLimiteMensal() + " R$");
            System.out.println("Saldo atual: " + saldoAtual + " R$");
            System.out.println("Total de receitas: " + totalReceitas + " R$");
            System.out.println("Total de despesas: " + totalDespesas + " R$");
    }

    // UTILITÁRIAS

    private void valorInvalido() {
        throw new ValorInvalidoException("O limite não pode ser igual ou menor que 0");
    }
}
