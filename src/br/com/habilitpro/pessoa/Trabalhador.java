package br.com.habilitpro.pessoa;

import br.com.habilitpro.Empresa;

import java.time.LocalDate;

public class Trabalhador extends Pessoa {

    private Empresa empresa;
    private String setor;
    private String funcao;
    private LocalDate dataAlteracao;

    public Trabalhador(String nome, String cpf, String setor, String funcao) {
        super(nome, cpf);
        setSetor(setor);
        setFuncao(funcao);
    }


    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        if(setor == null || setor.isBlank() || setor.isEmpty()) {
            throw new IllegalArgumentException("Informe o setor da empresa!");
        }
        this.setor = setor;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        if(funcao == null || funcao.isBlank() || funcao.isEmpty()) {
            throw new IllegalArgumentException("Informe a função do trabalhador!");
        }
        this.funcao = funcao;
        dataAlteracao = LocalDate.now();
    }


}


