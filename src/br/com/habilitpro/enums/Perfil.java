package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum Perfil implements AuxilioEnum {

    ADMINISTRATIVO("Administrativo"),
    OPERACIONAL("Operacional"),
    RH("RH");

    private String nome;

    Perfil(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}