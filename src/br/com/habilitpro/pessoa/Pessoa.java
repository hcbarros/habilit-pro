package br.com.habilitpro.pessoa;

import static br.com.habilitpro.utils.Validador.ehCpfValido;


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
        if(nome == null || nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("Informe o nome da pessoa!");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(cpf == null || !ehCpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        cpf = cpf.replaceAll("[^\\d]", "");
        this.cpf = cpf.substring(0,3)+"."+ cpf.substring(3,6)+"."+
                   cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }
}
