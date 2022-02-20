package br.com.habilitpro.pessoa;

import static br.com.habilitpro.utils.Validador.validarString;
import static br.com.habilitpro.utils.Formatador.formatarCPF;

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
        this.nome = validarString(nome, "Informe o nome da pessoa!");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = formatarCPF(cpf);
    }
}
