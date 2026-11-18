package com.financas.service;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.SaldoInsuficienteException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.*;

import java.math.BigDecimal;
import java.util.List;

public class ContaService {

    private ContaFinanceira conta;

    public ContaService() {
//        conta = JsonService.importarJson();
        this.testes();
    }

    public void definirLimite(BigDecimal novoLimite) {
        // define o máximo de dinheiro que o usuário que gastar
        if (novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        conta.atualizarLimite(novoLimite);
    }

    public void registrarReceita(BigDecimal valor, Categoria categoria) {
        // registra o dinheiro que foi adicionado à conta
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        conta.atualizarSaldoReceita(valor);

        Transacao receita = new Transacao(valor, categoria, TipoTransacao.RECEITA);
        System.out.println(receita);

        conta.addTransacao(receita);

        System.out.println("Saldo atual: " + conta.getSaldoAtual() + " R$");
    }

    public void registrarDespesa(BigDecimal valor, Categoria categoria) {
        // registra o dinheiro que foi gasto à conta
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }

        conta.atualizarSaldoDespesa(valor);

        Transacao despesa = new Transacao(valor, categoria, TipoTransacao.DESPESA);
        conta.addTransacao(despesa);

        // Verifica se o saldo é negativo
        if (conta.limiteUltrapassado()) {
            // Se ultrapassar, lança um aviso.
            System.out.println("ATENÇÃO! O limite Mensal de R$ " +
                    conta.getLimiteMensal() + " foi ultrapassado!");
        }

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
        System.out.println("Gastos do mês: " + conta.getTotalGasto() + " R$");
        System.out.println("Saldo atual: " + saldoAtual + " R$");
        System.out.println("Total de receitas: " + totalReceitas + " R$");
        System.out.println("Total de despesas: " + totalDespesas + " R$");
    }

    public void bloquearDespesa() {
        if (conta.limiteUltrapassado()) {
            throw new LimiteUltrapassadoException("O seu limite de gastos mensais foi ultrapassado," +
                    " redefina o limite ou espere o proximo mês para adicionar mais despesas.");
        } else if (conta.saldoNegativo()) {
            throw new SaldoInsuficienteException("ATENÇÃO! Seu saldo é insuficiente adicionar novas despesas.\n"
                    + " Saldo atual: " + conta.getSaldoAtual());
        }
    }

    // UTILITÁRIAS

    private void valorInvalido() {
        throw new ValorInvalidoException("O limite não pode ser igual ou menor que 0");
    }

    private void testes() {
        DadosConta mockDados = new DadosConta(new BigDecimal("1000"),
                BigDecimal.ZERO,
                new BigDecimal("1000"),
                null);
        conta = new ContaFinanceira(mockDados);
    }
}
