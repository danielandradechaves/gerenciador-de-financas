package com.financas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transacao {
    //Responsabilidades: Validações básicas no construtor

    private String id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Categoria categoria;
    private TipoTransacao tipoTransacao;

    public Transacao(
                     String descricao,
                     BigDecimal valor,
                     Categoria categoria,
                     TipoTransacao tipoTransacao) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da receita deve ser maior que zero");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia");
        }
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.valor = valor;
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
        this.categoria = Categoria.valueOf(dadosTransacao.categoria());
        this.tipoTransacao = TipoTransacao.valueOf(dadosTransacao.tipoTransacao());
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                ", categoria=" + categoria +
                ", tipoTransacao=" + tipoTransacao +
                '}';
    }
}
