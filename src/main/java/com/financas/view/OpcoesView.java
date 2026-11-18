package com.financas.view;

import com.financas.exception.LimiteUltrapassadoException;
import com.financas.exception.SaldoInsuficienteException;
import com.financas.exception.ValorInvalidoException;
import com.financas.model.Categoria;
import com.financas.model.DadosConta;
import com.financas.model.TipoTransacao;
import com.financas.model.Transacao;
import com.financas.service.ContaService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class OpcoesView {
    private Scanner sc = new Scanner(System.in);
    private ContaService service = new ContaService();

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

        BigDecimal valor = selecionarValor();

        Categoria categoria = selecionarCategoria(TipoTransacao.RECEITA);


        service.registrarReceita(valor, categoria);
    }

    public void registrarNovaDespesaView() {
        try {
            service.bloquearDespesa();
            System.out.println("\n--- REGISTRAR NOVA DESPESA ---");

            BigDecimal valor = selecionarValor();

            Categoria categoria = selecionarCategoria(TipoTransacao.DESPESA);

            try {
                service.registrarDespesa(valor, categoria);
            } catch (LimiteUltrapassadoException e) {
                System.out.println(e.getMessage());
            }
        }catch (SaldoInsuficienteException | LimiteUltrapassadoException e) {
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

    public void exportarRelatorio() {
        String json = "{\"limite-mensal\": 5000.00," +
                "\"saldo-atual\": 1300.00," +
                "\"transacoes\": [{\"id\": \"abcde\"," +
                "\"descricao\": \"salario\"," +
                "\"valor\": 1800.00," +
                "\"data\": \"2026-11-17\"," +
                "\"categoria\": \"Salário\"," +
                "\"tipo-transacao\": \"DESPESA\"}]}";

        ObjectMapper mapper = new ObjectMapper();
        DadosConta contaDto = mapper.readValue(json, new TypeReference<>() {
        });
        System.out.println(contaDto);
    }

    // UTILITÁRIAS

    private BigDecimal selecionarValor() {
        do {
            System.out.print("Valor: ");
            try {
                return new BigDecimal(sc.nextLine().trim().replace(",", "."));

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
