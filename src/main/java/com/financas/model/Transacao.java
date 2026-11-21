package com.financas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transacao {
    //Responsabilidades: Validações básicas no construtor

    private final String id;
    private final String descricao;
    private final BigDecimal valor;
    private final LocalDate data;
    private final Categoria categoria;
    private final TipoTransacao tipoTransacao;

    public Transacao(
                     BigDecimal valor,
                     String descricao,
                     Categoria categoria,
                     TipoTransacao tipoTransacao) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da receita deve ser maior que zero");
        }
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.descricao = descricao;
        this.data = LocalDate.now();
        this.categoria = categoria;
        this.tipoTransacao = tipoTransacao;
    }

    public Transacao(DadosTransacao dadosTransacao) {
        // para dados vindos do json
        this.id = dadosTransacao.id();
        this.descricao = dadosTransacao.descricao();
        this.valor = dadosTransacao.valor();
        this.data = dadosTransacao.data();
        this.categoria = dadosTransacao.categoria();
        this.tipoTransacao = dadosTransacao.tipoTransacao();
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    @Override
    public String toString() {
        return "[Valor: " + valor + " R$" +
                " | Data: " + data +
                " | Categoria: " + categoria.getTexto() +
                " | Descrição: " + descricao +
                " | Tipo da transação: " + tipoTransacao + "]";
    }
}
