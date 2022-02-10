package br.com.habilitpro.utils;

import java.math.BigDecimal;


public class Validador {

    public static boolean ehCpfValido(String cpf, boolean inicio) {
        return ehDocumentoValido(cpf, true, 11);
    }

    public static boolean ehCnpjValido(String cnpj, boolean inicio) {
        return ehDocumentoValido(cnpj, true, 14);
    }

    private static boolean ehDocumentoValido(String doc, boolean inicio, int size) {
        doc = doc.replaceAll("[^\\d]", "");
        if(doc.length() != size) return false;
        int soma = 0;
        int count = size == 11 ? 11 : 6;
        int desc = inicio ? count : (count + 1);
        BigDecimal big = BigDecimal.ZERO;
        for (int j = 0; j < (inicio ? (size-2) : (size-1)); j++) {
            if(big.toString().equals(doc)) return false;
            big = big.add(new BigDecimal("11111111111"));
            System.out.println(big);
            soma += (doc.charAt(j) - '0') * (desc = (--desc) == 1 ? 9 : desc);
        }
        int resp = (soma % 11) < 2 ? 0 : (11 - (soma % 11));
        int dig = doc.charAt(inicio ? (size-2) : (size-1)) - '0';
        return inicio ? ((dig == resp) && ehDocumentoValido(doc,false, size)) : (dig == resp);
    }


}
