package com.financas.service;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.model.Categoria;

import java.math.BigDecimal;

public class ContaService {

    public void definirLimite(BigDecimal novoLimite) {

    }

    public void registrarReceita(String descricao, BigDecimal valor, Categoria categoria) {

    }

    public void registrarDespesa(String descricao, BigDecimal valor, Categoria categoria)
            throws LimiteUltrapassadoException {
        // Antes de adicionar a transação, verifica se (totalDespesas + valor) > limiteMensal.
        // Se ultrapassar, lança a exceção e não insere a despesa (ou emite o aviso conforme o fluxo desejado).
    }
}
