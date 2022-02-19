package br.com.habilitpro.pessoa.trabalhador;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Modulo;
import br.com.habilitpro.enums.Avaliacao;
import static br.com.habilitpro.utils.Validador.validarObjeto;


public class ModuloTrabalhador {

    private Modulo modulo;
    private Avaliacao avaliacao;
    private String anotacao;
    private Empresa empresa;
    private String funcao;
    private String setor;

    public ModuloTrabalhador(Modulo modulo, Avaliacao avaliacao, String anotacao, Trabalhador t) {
        setModulo(modulo);
        this.avaliacao = avaliacao;
        this.anotacao = anotacao;
        this.empresa = t.getEmpresa();
        this.funcao = t.getFuncao();
        this.setor = t.getSetor();
    }


    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        validarObjeto(modulo, "Informe o módulo!");
        this.modulo = modulo;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getSetor() {
        return setor;
    }

    @Override
    public String toString() {

        return "\nMódulo: "+ modulo.getNome() +
                (avaliacao == null ? "" : "\nAvaliação: "+avaliacao.getNome()) +
                ((anotacao == null || anotacao.isEmpty()) ? "" : "\nAnotação: "+anotacao) +
                "\nEmpresa que oferece o módulo: "+empresa.getNome() +
                "\nFunção exercida durante o múdulo: "+funcao +
                "\nSetor que contém o trabalhador: "+setor;
    }
}
