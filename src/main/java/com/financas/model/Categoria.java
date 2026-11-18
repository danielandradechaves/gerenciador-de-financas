package com.financas.model;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;

public enum Categoria {
    // Grupos de despesas/receitas
    ALIMENTACAO("Alimentação", TipoTransacao.DESPESA),
    MORADIA("Moradia", TipoTransacao.DESPESA),
    LAZER("Lazer", TipoTransacao.DESPESA),
    TRANSPORTE("Transporte", TipoTransacao.DESPESA),
    OUTROS("Outros", TipoTransacao.DESPESA),
    SALARIO("Salário", TipoTransacao.RECEITA),
    INVESTIMENTO("Investimento", TipoTransacao.RECEITA),
    VENDA("Venda", TipoTransacao.RECEITA),
    FREELANCER("Trabalho freelancer", TipoTransacao.RECEITA);

    private final String texto;
    private final TipoTransacao tipoTransacao;

    Categoria(String texto, TipoTransacao tipoTransacao) {
        this.texto = texto;
        this.tipoTransacao = tipoTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public static void exibirCategorias (TipoTransacao tipoTransacao) {
        List<Categoria> listaCategorias = Arrays.stream(Categoria.values())
                .filter(c -> c.getTipoTransacao() == tipoTransacao)
                .toList();


        for (int i = 0; i < listaCategorias.size(); i++) {
            System.out.printf("[%d] - %s\n", i + 1, listaCategorias.get(i).texto);
        }
    }

    public static List<Categoria> obterCategoriaPorTipo(TipoTransacao tipoTransacao) {
        return Arrays.stream(Categoria.values())
                .filter(c -> c.tipoTransacao == tipoTransacao)
                .toList();
    }
}
