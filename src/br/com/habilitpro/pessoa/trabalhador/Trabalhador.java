package br.com.habilitpro.pessoa.trabalhador;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.enums.Avaliacao;
import br.com.habilitpro.pessoa.Pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trabalhador extends Pessoa {

    private Empresa empresa;
    private String setor;
    private String funcao;
    private LocalDate dataAlteracao;
    private List<ModuloTrabalhador> modulosTrabalhador;

    public Trabalhador(String nome, String cpf, Empresa empresa, String setor, String funcao) {
        super(nome, cpf);
        setEmpresa(empresa);
        setSetor(setor);
        setFuncao(funcao);
        modulosTrabalhador = new ArrayList<>();
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        if(empresa == null) {
            throw new IllegalArgumentException("Informe a empresa do trabalhador!");
        }
        this.empresa = empresa;
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

    public List<ModuloTrabalhador> getModulosTrabalhador() {
        return Collections.unmodifiableList(modulosTrabalhador);
    }

    public void addModuloTrabalhador(ModuloTrabalhador moduloTrabalhador) {
        if(moduloTrabalhador != null) {
            modulosTrabalhador.add(moduloTrabalhador);
        }
    }

    public void setModuloTrabalhador(String nome, Avaliacao avaliacao, String anotacao) {

        ModuloTrabalhador mt = modulosTrabalhador.stream()
                 .filter(m -> m.getModulo().getNome().equalsIgnoreCase(nome))
                 .findFirst().orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado!"));
        int index = modulosTrabalhador.indexOf(mt);
        mt.setAnotacao(anotacao);
        mt.setAvaliacao(avaliacao);
        modulosTrabalhador.set(index, mt);
    }

}


