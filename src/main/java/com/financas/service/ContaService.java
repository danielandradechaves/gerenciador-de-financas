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
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        BigDecimal limiteAtualizado = conta.getLimiteMensal().add(valor);
        conta.atualizarLimite(limiteAtualizado);

        Transacao receita = new Transacao(descricao, valor, categoria, TipoTransacao.RECEITA);
        conta.addTransacao(receita);

        System.out.println(receita);
        System.out.println("Limite Mensal: " + limiteAtualizado + " R$");
    }

    public void registrarDespesa(String descricao, BigDecimal valor, Categoria categoria) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            valorInvalido();
        }
        BigDecimal limiteAtualizado = conta.getLimiteMensal().subtract(valor);
        if (limiteAtualizado.compareTo(BigDecimal.ZERO) < 0) {
            // Se ultrapassar, lança a exceção e não insere a despesa (ou emite o aviso conforme o fluxo desejado).
            throw new LimiteUltrapassadoException("Transação não concluída: O limite Mensal de R$ " +
                    conta.getLimiteMensal() + " foi ultrapassado");
        }
        conta.atualizarLimite(limiteAtualizado);

        Transacao despesa = new Transacao(descricao, valor, categoria, TipoTransacao.DESPESA);
        conta.addTransacao(despesa);

        System.out.println(despesa);
        System.out.println("Limite atual: " + conta.getLimiteMensal() + " R$");
    }

    public List<Transacao> listarTransacoes() {
        return conta.getTransacoes();
    }

    private void valorInvalido() {
        throw new ValorInvalidoException("O limite não pode ser igual ou menor que 0");
    }
}
