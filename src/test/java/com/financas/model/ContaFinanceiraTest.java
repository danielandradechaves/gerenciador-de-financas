package com.financas.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ContaFinanceiraTest {

    @Test
    void deveAumentarOSaldoAoAdicionarUmaReceita() {
        // deve aumentar o saldo ao adicionar uma receita

        // cenário
        ContaFinanceira conta = new ContaFinanceira();
        Transacao receita = new Transacao(new BigDecimal("100.00"), "Salário",
                Categoria.SALARIO, TipoTransacao.RECEITA);

        // ação
        conta.addTransacao(receita);

        // verificação
        Assertions.assertEquals(new BigDecimal("100.00"), conta.getSaldoAtual());
    }

    @Test
    void limiteUltrapassadoDeveRetornarFalse() {
        // limite ultrapassado deve retornar false

        // cenário
        ContaFinanceira conta = new ContaFinanceira();
        conta.atualizarLimite(new BigDecimal("500.00"));

        Transacao despesaExistente = new Transacao(new BigDecimal("200.00"), "Mercado",
                Categoria.ALIMENTACAO, TipoTransacao.DESPESA);
        conta.addTransacao(despesaExistente);

        // ação
        BigDecimal novaDespesa = new BigDecimal("150.00");
        boolean resultado = conta.limiteUltrapassado(novaDespesa);

        // verificação
        Assertions.assertFalse(resultado,"O limite não deveria ser considerado ultrapassado");
    }
}
