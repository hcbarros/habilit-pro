package br.com.habilitpro.pessoa;

import static br.com.habilitpro.utils.Validador.*;


public abstract class Pessoa {

    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarString(nome, "Informe o nome da pessoa!");
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        validarCpf(cpf);
        cpf = cpf.replaceAll("[^\\d]", "");
        this.cpf = cpf.substring(0,3)+"."+ cpf.substring(3,6)+"."+
                   cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }
}
