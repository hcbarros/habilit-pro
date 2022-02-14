package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum Satisfacao implements AuxilioEnum {

    NIVEL_1(1, "Nível 1"),
    NIVEL_2(2, "Nível 2"),
    NIVEL_3(3, "Nível 3"),
    NIVEL_4(4, "Nível 4"),
    NIVEL_5(5, "Nível 5");

    private int nivel;
    private String nome;

    Satisfacao(int nivel, String nome) {
        this.nivel = nivel;
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public String getNome() {
        return nome;
    }

}
