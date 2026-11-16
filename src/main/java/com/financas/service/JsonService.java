package com.financas.service;

import com.financas.model.ContaFinanceira;
import com.financas.model.DadosConta;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonService {

    public static ContaFinanceira importarJson () {
        ObjectMapper mapper = new ObjectMapper();
        try(FileReader reader = new FileReader("contas/conta.json")){
            DadosConta dadosConta = mapper.readValue(reader, new TypeReference<>() {});
            return new ContaFinanceira(dadosConta);
        } catch (FileNotFoundException e) {
            return new ContaFinanceira();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void exportarJson(DadosConta dadosConta) {

    }
}
