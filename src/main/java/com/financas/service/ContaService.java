package com.financas.service;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.SaldoInsuficienteException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class ContaService {

    private final ContaFinanceira conta;

    public ContaService(ContaFinanceira conta) {
        this.conta = conta;
    }

    public void definirLimite(BigDecimal novoLimite) {
        // define o máximo de dinheiro que o usuário que gastar
        try {
            valorInvalido(novoLimite);
            conta.atualizarLimite(novoLimite);
            System.out.println("Limite atualizado com sucesso");
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registrarReceita(BigDecimal valor, String descricao, Categoria categoria) {
        // registra o dinheiro que foi adicionado à conta
        try {
            valorInvalido(valor);
            Transacao receita = new Transacao(valor, descricao, categoria, TipoTransacao.RECEITA);
            conta.addTransacao(receita);
            System.out.println("Operação finalizada com sucesso");
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registrarDespesa(BigDecimal valor, String descricao, Categoria categoria) {
        // registra o dinheiro que foi gasto à conta
        try {
            valorInvalido(valor);
            Transacao despesa = new Transacao(valor, descricao, categoria, TipoTransacao.DESPESA);
            conta.addTransacao(despesa);
            System.out.println("Operação finalizada com sucesso");
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<YearMonth, List<Transacao>> listarTransacoes() {
        return conta.getTransacoesPorMesEAno();
    }

    public DadosResumo resumoDeFinancas() {
        YearMonth mesAno = YearMonth.now();
        return new DadosResumo(conta.getLimiteMensal(),
                conta.calcularMesAno(mesAno, TipoTransacao.RECEITA),
                conta.calcularMesAno(mesAno, TipoTransacao.DESPESA),
                conta.getSaldoAtual(),
                conta.calcularTotal(TipoTransacao.RECEITA),
                conta.calcularTotal(TipoTransacao.DESPESA));
    }

    public void exportarRelatorio() {
        DadosConta contaDto = new DadosConta(conta.getLimiteMensal(),
                conta.getSaldoAtual(),
                conta.getDadosTransacoes());
        JsonService.exportarJson(contaDto);
    }

    public void bloquearDespesa(BigDecimal valor) {
        if (conta.limiteUltrapassado(valor)) {
            throw new LimiteUltrapassadoException("O seu limite de gastos mensais foi ultrapassado," +
                    " redefina o limite ou espere o próximo mês para adicionar mais despesas.");
        } else if (conta.saldoNegativo(valor)) {
            throw new SaldoInsuficienteException("ATENÇÃO! Seu saldo é insuficiente para adicionar novas despesas.\n"
                    + " Saldo atual: " + conta.getSaldoAtual());
        }
    }

    // UTILITÁRIAS

    private void valorInvalido(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor deve ser maior que 0");
        }
    }
}
