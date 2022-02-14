package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum Status implements AuxilioEnum {

    EM_ANDAMENTO("Curso em andamento"),
    EM_FASE_AVALIACAO("Em fase de avaliação"),
    FINALIZADO("Fase de avaliação finalizada");

    private String nome;

    Status(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
