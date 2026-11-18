package com.financas.service;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.SaldoInsuficienteException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.*;

import java.math.BigDecimal;
import java.util.List;

public class ContaService {

    private final ContaFinanceira conta;

    public ContaService(ContaFinanceira conta) {
        this.conta = conta;
//        this.testes();
    }

    public void definirLimite(BigDecimal novoLimite) {
        // define o máximo de dinheiro que o usuário que gastar
        if (novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        conta.atualizarLimite(novoLimite);
    }

    public void registrarReceita(BigDecimal valor, String descricao, Categoria categoria) {
        // registra o dinheiro que foi adicionado à conta
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }

        Transacao receita = new Transacao(valor, descricao, categoria, TipoTransacao.RECEITA);
        conta.addTransacao(receita);
        conta.calcularSaldo();
    }

    public void registrarDespesa(BigDecimal valor, String descricao, Categoria categoria) {
        // registra o dinheiro que foi gasto à conta
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }

        Transacao despesa = new Transacao(valor, descricao, categoria, TipoTransacao.DESPESA);
        conta.addTransacao(despesa);
        conta.calcularSaldo();
        conta.calcularTotalDespesas();

        // Verifica se o saldo é negativo
        if (conta.limiteUltrapassado()) {
            // Se ultrapassar, lança um aviso.
            System.out.println("ATENÇÃO! O limite Mensal de R$ " +
                    conta.getLimiteMensal() + " foi ultrapassado!");
        }
    }

    public List<Transacao> listarTransacoes() {
        return conta.getTransacoes();
    }

    public DadosResumo resumoDeFinancas() {
        return new DadosResumo(conta.getLimiteMensal(),
                conta.calcularTotalDespesas(),
                conta.getSaldoAtual(),
                conta.calcularTotalReceitas(),
                conta.calcularTotalDespesas());
    }

    public DadosConta exportarRelatorio() {
        DadosConta contaDto = new DadosConta(conta.getLimiteMensal(),
                conta.getSaldoAtual(),
                conta.getDadosTransacoes());
        JsonService.exportarJson(contaDto);
        return null;
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

//    private void testes() {
//        DadosConta mockDados = new DadosConta(new BigDecimal("1000"),
//                BigDecimal.ZERO,
//                new BigDecimal("1000"),
//                null);
//        conta = new ContaFinanceira(mockDados);
//    }
}
