package br.com.habilitpro.classesPrincipais.pessoa.trabalhador;

import br.com.habilitpro.classesPrincipais.Empresa;
import br.com.habilitpro.classesPrincipais.Modulo;
import br.com.habilitpro.classesPrincipais.Trilha;
import br.com.habilitpro.enums.Avaliacao;
import br.com.habilitpro.classesPrincipais.pessoa.Pessoa;
import static br.com.habilitpro.utils.Validador.*;
import static br.com.habilitpro.utils.Formatador.formatarData;

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
        this.setor = validarString(setor, "Informe o setor da empresa!");
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        validarString(funcao, "Informe a função do trabalhador!");
        if(this.funcao == null || !this.funcao.equalsIgnoreCase(funcao)) {
            dataAlteracao = LocalDate.now();
        }
        this.funcao = funcao;
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

    public boolean possuiModulo(Modulo modulo) {
        return modulosTrabalhador.stream()
                .anyMatch(m -> m.getModulo().getNome().equalsIgnoreCase(modulo.getNome())
                && m.getModulo().getTrilha().getNome().equalsIgnoreCase(modulo.getTrilha().getNome()));
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

    @Override
    public String toString() {
        return "\nNome: "+getNome() +
                "\nCPF: "+getCpf() +
                "\nEmpresa atual: "+empresa.getNome() +
                "\nFunção atual: "+ funcao +
                "\nInício da função atual: "+formatarData(dataAlteracao) +
                "\nSetor da empresa: "+setor;
    }

}


