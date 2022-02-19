package br.com.habilitpro;

import br.com.habilitpro.enums.Satisfacao;

import static br.com.habilitpro.utils.Validador.*;
import static br.com.habilitpro.utils.Contador.contar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Trilha {

    private Empresa empresa;
    private String ocupacao;
    private String nome;
    private String apelido;
    private List<Modulo> modulos;
    private Satisfacao satisfacao;
    private String anotacoes;

    public Trilha(Empresa empresa, String ocupacao) {
        this.empresa = empresa;
        int count = contar(ocupacao, empresa);
        nome = ocupacao + empresa.getNome() + count + LocalDate.now().getYear();
        apelido = ocupacao + count;
        this.ocupacao = ocupacao;
        modulos = new ArrayList<>();
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }

    public List<Modulo> getModulos() {
        return Collections.unmodifiableList(modulos);
    }

    public void addModulo(Modulo modulo) {
        if(modulo != null){
            modulos.add(modulo);
        }
    }

    public Satisfacao getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(Satisfacao satisfacao) {
        if(satisfacao != null) {
            this.satisfacao = satisfacao;
        }
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    @Override
    public String toString() {
        String textoModulos = modulos.isEmpty() ? "Não há módulos cadastrados!" : "";
        for(Modulo m: modulos) {
            textoModulos += "\t\n- Nome: "+ m.getNome() +
                            "\t\n- Status: "+ (m.getStatus() == null ? "" : m.getStatus().getNome());
        }
        return "\nNome da empresa: "+empresa.getNome() +
                "\nCNPJ da empresa: "+empresa.getCnpj() +
                "\nNome da trilha: "+nome +
                "\nApelido da trilha: "+apelido +
                "\nOcupação: "+ocupacao +
                (satisfacao == null ? "" : "\nNível de satisfação: "+ satisfacao.getNivel()) +
                (anotacoes == null ? "" : "\nAnotações: "+ anotacoes) +
                "\nModulos: "+ textoModulos;
    }
}
