package com.financas.view;

public class MenuConsole {

    public void iniciar() {

    }

    private void menuPrincipal() {
        System.out.println("===============");
        System.out.println("Menu Principal");
        System.out.println("===============\n");
        System.out.println("Escolha o número da operação");
        System.out.println("""
                0 - Sair
                1 - Definir/Alterar limite mensal de orçamento
                2 - Registrar Receita
                3 - Registrar Despesa
                4 - Listar todas as transações
                5 - Visualizar resumo financeiro
                6 - Exportar relatório""");
    }
}
