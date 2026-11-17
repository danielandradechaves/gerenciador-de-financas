package com.financas.view;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.Categoria;
import com.financas.model.ContaFinanceira;
import com.financas.model.TipoTransacao;
import com.financas.model.Transacao;
import com.financas.service.ContaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class OpcoesView {
    private Scanner sc = new Scanner(System.in);
    private ContaService service;

    public OpcoesView(ContaFinanceira conta) {
        this.service = new ContaService(conta);
    }

    public void definirLimiteView() {
        System.out.println("\n--- DEFINIR NOVO LIMITE ---");
        do {
            System.out.print("Defina o valor do novo limite: ");
            try {
                BigDecimal novoLimite = new BigDecimal(sc.nextLine().trim().replace(".", ","));
                service.definirLimite(novoLimite);
                break;
            } catch (NumberFormatException e) {
                System.out.println("------------------------------------------");
                System.out.println("Essa operação só aceita valores monetários");
                System.out.println("------------------------------------------");
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void registrarNovaReceitaView() {
        System.out.println("\n--- REGISTRAR NOVA RECEITA ---");

        String descricao = selecionarDescricao();

        BigDecimal valor = selecionarValor();

        Categoria categoria = selecionarCategoria(TipoTransacao.RECEITA);


        service.registrarReceita(descricao, valor, categoria);
    }

    public void registrarNovaDespesaView() {
        System.out.println("\n--- REGISTRAR NOVA DESPESA ---");

        String descricao = selecionarDescricao();

        BigDecimal valor = selecionarValor();

        Categoria categoria = selecionarCategoria(TipoTransacao.DESPESA);

        try {
            service.registrarDespesa(descricao, valor, categoria);
        } catch (LimiteUltrapassadoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarTransacoesView() {
        System.out.println("--- LISTAR TRANSAÇÕES ---");
        List<Transacao> listaTransacao = service.listarTransacoes();
        listaTransacao.forEach(System.out::println);
    }

    public void verResumoFinanceiroView() {
        System.out.println("--- RESUMO DE FINANÇAS ---");
        service.resumoDeFinancas();
    }

    // UTILITÁRIAS

    private String selecionarDescricao() {
        System.out.print("Descrição: ");
        return sc.nextLine();
    }

    private BigDecimal selecionarValor() {
        do {
            System.out.print("Valor: ");
            try {
                return new BigDecimal(sc.nextLine().trim().replace(".", ","));

            } catch (NumberFormatException e) {
                System.out.println("------------------------------------------");
                System.out.println("Essa operação só aceita valores monetários");
                System.out.println("------------------------------------------");
            }
        } while (true);
    }

    private Categoria selecionarCategoria(TipoTransacao tipoTransacao) {
        List<Categoria> categoriasDisponiveis = Categoria.obterCategoriaPorTipo(tipoTransacao);

        System.out.println("Categorias disponíveis:");
        Categoria.exibirCategorias(tipoTransacao);

        do {
            System.out.print("Escolha a categoria (número): ");
            try {
                int indice = Integer.parseInt(sc.nextLine()) - 1;
                if (indice >= 0 && indice < categoriasDisponiveis.size()) {
                    return categoriasDisponiveis.get(indice);
                }
                System.out.println("Opção inválida! Digite um número entre 1 e " + categoriasDisponiveis.size() + ".");
            } catch (NumberFormatException e) {
                System.out.println("------------------------------------------");
                System.out.println("Essa operação só aceita números");
                System.out.println("------------------------------------------");
            }
        } while (true);
    }
}
