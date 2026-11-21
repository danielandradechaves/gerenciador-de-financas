# 💰 Gerenciador de Finanças Pessoais

Aplicação console desenvolvida em **Java** com foco em Orientação a Objetos (POO), regras de negócio financeiras, manipulação de dados em disco via JSON e testes unitários automatizados.

---

## 🎯 Funcionalidades

- **Registro de Transações:** Cadastro de receitas e despesas com descrição, valor, data e categoria.
- **Controle de Orçamento Mensal:** Definição de limite de gastos mensais com verificação automática (`LimiteUltrapassadoException`).
- **Proteção de Saldo:** Validação preventiva para evitar saldo insuficiente (`SaldoInsuficienteException`).
- **Histórico Agrupado por Período:** Listagem de transações agrupadas e ordenadas cronologicamente por Mês e Ano (`YearMonth`).
- **Resumo Financeiro Consolidado:** Consulta rápida de saldo atual, receitas e despesas do mês corrente e acumulado histórico.
- **Persistência em JSON:** Exportação e importação do estado da conta financeira em arquivo `.json` via Jackson Databind.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Maven** (Gerenciamento de dependências e build)
- **Jackson Databind** (Serialização e desserialização JSON)
- **JUnit 5 (Jupiter)** (Testes unitários automatizados)

---

## 📂 Estrutura do Projeto

```text
src/
├── main/java/com/financas/
│   ├── FinancasApplication.java   # Ponto de entrada da aplicação
│   ├── model/                     # Entidades de domínio (ContaFinanceira, Transacao, Enums e Records)
│   ├── service/                   # Regras de negócio e integração I/O (ContaService, JsonService)
│   ├── view/                      # Interface via terminal (MenuConsole, OpcoesView)
│   └── exception/                 # Exceções de negócio personalizadas
└── test/java/com/financas/        # Testes unitários com JUnit 5
```

---

## 🚀 Como Executar

### Pré-requisitos
- JDK 17 ou superior instalado
- Maven instalado (ou utilizar a IDE de sua preferência)

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SEU_USUARIO/gerenciador-de-financas.git
   cd gerenciador-de-financas
   ```

2. **Execute os testes unitários:**
   ```bash
   mvn clean test
   ```

3. **Execute a aplicação:**
   ```bash
   mvn compile exec:java -Dexec.mainClass="com.financas.FinancasApplication"
   ```
   *(Ou execute o método `main` da classe `FinancasApplication` diretamente pela sua IDE).*

---

## 🧪 Testes Automatizados

O projeto conta com testes unitários cobrindo:
- Atualização e consistência do saldo após adição de transações.
- Verificação da lógica de limite mensal ultrapassado.
- Lançamento de exceções ao tentar cadastrar transações com valores inválidos ($\le 0$).

---

## 👤 Autor

Desenvolvido por **Daniel Andrade Chaves** para fins de estudo.
