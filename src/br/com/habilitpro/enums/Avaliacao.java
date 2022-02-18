package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum Avaliacao implements AuxilioEnum {

    NOTA_1("Nota_1", 1),
    NOTA_2("Nota_2", 2),
    NOTA_3("Nota_3", 3),
    NOTA_4("Nota_4", 4),
    NOTA_5("Nota_5", 5);

    private String nome;
    private int valor;

    Avaliacao(String nome, int valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public static Avaliacao getAvaliacao(String nome) {
        for(Avaliacao a: values()) {
            if(nome.equals(a.nome)) {
                return a;
            }
        }
        return null;
    }

}
