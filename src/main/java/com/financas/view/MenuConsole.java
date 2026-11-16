package com.financas.view;

import com.financas.model.ContaFinanceira;
import com.financas.service.JsonService;

import java.util.Scanner;

public class MenuConsole {
    private Scanner sc = new Scanner(System.in);
    private ContaFinanceira conta;
    private OpcoesView opcoesView;

    public MenuConsole() {
        conta = JsonService.importarJson();
        opcoesView = new OpcoesView(conta);
    }

    public void iniciar() {
        do {
            exibirMenu();
            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 0 -> System.exit(0);
                case 1 -> opcoesView.definirLimiteView();
                case 2 -> opcoesView.registrarNovaReceitaView();
                case 3 -> opcoesView.registrarNovaDespesaView();
                case 4 -> opcoesView.listarTransacoesView();
            }
        } while (true);
    }

    private void exibirMenu() {
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
