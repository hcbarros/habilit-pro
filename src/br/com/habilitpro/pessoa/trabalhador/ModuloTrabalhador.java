package br.com.habilitpro.pessoa.trabalhador;

import br.com.habilitpro.Modulo;
import br.com.habilitpro.enums.Avaliacao;

public class ModuloTrabalhador {

    private Modulo modulo;
    private Avaliacao avaliacao;
    private String anotacao;

    public ModuloTrabalhador(Modulo modulo, Avaliacao avaliacao, String anotacao) {
        setModulo(modulo);
        this.avaliacao = avaliacao;
        this.anotacao = anotacao;
    }


    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        if(modulo == null) {
            throw new IllegalArgumentException("Informe o módulo!");
        }
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

}
