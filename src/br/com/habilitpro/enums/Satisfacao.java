package br.com.habilitpro.enums;

public enum Satisfacao {

    NIVEL_1(1),
    NIVEL_2(2),
    NIVEL_3(3),
    NIVEL_4(4),
    NIVEL_5(5);

    private int nivel;

    Satisfacao(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

}
