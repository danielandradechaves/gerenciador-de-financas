package com.financas.service;

import com.financas.exception.ValorInvalidoException;
import com.financas.model.Categoria;
import com.financas.model.ContaFinanceira;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ContaServiceTest {
    private final ContaFinanceira conta = new ContaFinanceira();
    private final ContaService service = new ContaService(conta);

    @Test
    void receitaDeveLancarExcecaoSeValorMenorOuIgualAZero() {
        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.registrarReceita(BigDecimal.ZERO, "Salário", Categoria.SALARIO));

        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.registrarReceita(new BigDecimal("-100.00"), "Salário", Categoria.SALARIO));
    }

    @Test
    void definirLimiteDeveLancarExcecaoSeValorMenorOuIgualAZero() {
        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.definirLimite(BigDecimal.ZERO));

        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.definirLimite(new BigDecimal("-100.00")));
    }

    @Test
    void despesaDeveLancarExcecaoSeValorMenorOuIgualAZero() {
        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.registrarDespesa(BigDecimal.ZERO, "Almoço", Categoria.ALIMENTACAO));

        Assertions.assertThrows(ValorInvalidoException.class, () ->
                service.registrarDespesa(new BigDecimal("-100.00"), "Almoço", Categoria.ALIMENTACAO));
    }
}
