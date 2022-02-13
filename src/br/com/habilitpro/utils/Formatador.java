package br.com.habilitpro.utils;

import static br.com.habilitpro.utils.Validador.validarCnpj;
import static br.com.habilitpro.utils.Validador.validarCpf;

public class Formatador {

    public static String formatarCPF(String cpf) {
        validarCpf(cpf);
        cpf = cpf.replaceAll("[^\\d]", "");
        return cpf.substring(0,3)+"."+ cpf.substring(3,6)+"."+
                    cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

    public static String formatarCNPJ(String cnpj) {
        validarCnpj(cnpj);
        cnpj = cnpj.replaceAll("[^\\d]", "");
        return cnpj.substring(0,2)+"."+ cnpj.substring(2,5)+"."+
               cnpj.substring(5,8)+"/"+ cnpj.substring(8,12)+"-"+ cnpj.substring(12,14);
    }


}
