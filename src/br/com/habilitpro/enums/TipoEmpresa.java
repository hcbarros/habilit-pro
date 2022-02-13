package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum TipoEmpresa implements AuxilioEnum {

    MATRIZ("Matriz"),
    FILIAL("Filial");

    private String nome;

    TipoEmpresa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
