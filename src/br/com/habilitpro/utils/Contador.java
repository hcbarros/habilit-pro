package br.com.habilitpro.utils;

import br.com.habilitpro.Empresa;

import java.util.HashMap;
import java.util.Map;


public class Contador {

    private static Map<String, Integer> nomes = new HashMap<>();

    public static int contar(String ocupacao, Empresa empresa) {
        if(empresa == null || ocupacao == null || ocupacao.isBlank() || ocupacao.isEmpty()) {
            throw new IllegalArgumentException("Informe a ocupação e a empresa!");
        }
        String nome = (ocupacao + empresa.getNome()).toLowerCase();
        Integer count = nomes.putIfAbsent(nome, 1);
        if(count != null) {
            return nomes.put(nome, count + 1) + 1;
        }
        return 1;
    }

}
