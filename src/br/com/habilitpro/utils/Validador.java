package br.com.habilitpro.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validador {

    public static boolean ehCpfValido(String cpf) {
        return ehDocumentoValido(cpf, true, 11);
    }

    public static boolean ehCnpjValido(String cnpj) {
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
            soma += (doc.charAt(j) - '0') * (desc = (--desc) == 1 ? 9 : desc);
        }
        int resp = (soma % 11) < 2 ? 0 : (11 - (soma % 11));
        int dig = doc.charAt(inicio ? (size-2) : (size-1)) - '0';
        return inicio ? ((dig == resp) && ehDocumentoValido(doc,false, size)) : (dig == resp);
    }

    public static boolean ehEmailValido(String email) {
        if(email != null) {
            Pattern pattern = Pattern
                    .compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher("    ");
            return pattern.matcher(email).matches();
        }
        return false;
    }

    public static boolean ehSenhaValida(String senha) {
        boolean temNumero = false;
        boolean temLetra = false;
        if(senha != null) {
            for(char c: senha.toCharArray()) {
                if(Character.isDigit(c)) {
                    temNumero = true;
                }
                if(Character.isLetter(c)) {
                    temLetra = true;
                }
            }
            return temLetra && temNumero;
        }
        return false;
    }

}
