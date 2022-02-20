package br.com.habilitpro.classesPrincipais;

import br.com.habilitpro.enums.Satisfacao;

import static br.com.habilitpro.utils.Contador.contar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


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
        int index = IntStream.range(0, modulos.size())
                .filter(i -> modulo != null && modulo.getNome().equalsIgnoreCase(modulos.get(i).getNome()))
                .findFirst().orElse(-1);
        if(index >= 0) {
            Modulo m = modulos.get(index);
            m.setTarefaValidacao(modulo.getTarefaValidacao());
            m.definirStatus(modulo.getStatus());
            m.setPrazo_limite(modulo.getPrazo_limite());
            m.addHabilidades(modulo.getHabilidades().toArray(new String[0]));
            modulos.set(index, m);
        }
        else if(modulo != null) {
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
            textoModulos += "\n\t- Nome: "+ m.getNome() +
                            "\n\t- Status: "+ (m.getStatus() == null ? "" : m.getStatus().getNome());
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
