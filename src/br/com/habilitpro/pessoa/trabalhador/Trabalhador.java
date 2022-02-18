package br.com.habilitpro.pessoa.trabalhador;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Trilha;
import br.com.habilitpro.enums.Avaliacao;
import br.com.habilitpro.pessoa.Pessoa;
import static br.com.habilitpro.utils.Validador.*;

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
    private List<Trilha> trilhas;

    public Trabalhador(String nome, String cpf, Empresa empresa, String setor, String funcao) {
        super(nome, cpf);
        setEmpresa(empresa);
        setSetor(setor);
        setFuncao(funcao);
        modulosTrabalhador = new ArrayList<>();
        trilhas = new ArrayList<>();
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        validarObjeto(empresa, "Informe a empresa do trabalhador!");
        this.empresa = empresa;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        validarString(setor, "Informe o setor da empresa!");
        this.setor = setor;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        validarString(funcao, "Informe a função do trabalhador!");
        this.funcao = funcao;
        dataAlteracao = LocalDate.now();
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public List<Trilha> getTrilhas() {
        return Collections.unmodifiableList(trilhas);
    }

    public List<ModuloTrabalhador> getModulosTrabalhador() {
        return Collections.unmodifiableList(modulosTrabalhador);
    }

    public void addModuloTrabalhador(ModuloTrabalhador moduloTrabalhador) {
        if(moduloTrabalhador != null) {
            modulosTrabalhador.add(moduloTrabalhador);
            Trilha trilha = moduloTrabalhador.getModulo().getTrilha();
            for(Trilha t: trilhas) {
                if(t.getNome().equalsIgnoreCase(trilha.getNome())) {
                    return;
                }
            }
            trilhas.add(trilha);
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


