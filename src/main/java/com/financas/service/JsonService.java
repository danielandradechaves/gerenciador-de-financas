package com.financas.service;

import com.financas.model.ContaFinanceira;
import com.financas.model.DadosConta;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.*;

public class JsonService {
    private static final ObjectMapper mapper = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    private static final String CAMINHO = "contas/conta.json";

    public static ContaFinanceira importarJson() {
        File file = new File(CAMINHO);

        if (!file.exists()) {
            return new ContaFinanceira();
        }
        DadosConta dadosConta = mapper.readValue(file, new TypeReference<>() {});
        return new ContaFinanceira(dadosConta);
    }

    public static void exportarJson(DadosConta dadosConta) {
        File file = new File(CAMINHO);

        if(file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        mapper.writeValue(file, dadosConta);
    }
}
