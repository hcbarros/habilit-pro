package br.com.habilitpro;

import br.com.habilitpro.enums.Regional;
import br.com.habilitpro.enums.Segmento;
import br.com.habilitpro.enums.TipoEmpresa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.habilitpro.utils.Validador.*;

public class Empresa {

    private String nome;
    private String cnpj;
    private TipoEmpresa tipo;
    private String nomeFilial;
    private Segmento segmento;
    private String estado;
    private String cidade;
    private Regional regional;
    private List<Trilha> trilhas;

    public Empresa(String nome, String cnpj, TipoEmpresa tipo, String nomeFilial,
                    Segmento segmento, String estado, String cidade, Regional regional) {

        this.nome = nome;
        setCnpj(cnpj);
        this.tipo = tipo;
        this.nomeFilial = nomeFilial;
        this.segmento = segmento;
        this.estado = estado;
        this.cidade = cidade;
        this.regional = regional;
        this.trilhas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");
        this.cnpj = cnpj.substring(0,2)+"."+ cnpj.substring(2,5)+"."+
                cnpj.substring(5,8)+"/"+ cnpj.substring(8,12)+"-"+ cnpj.substring(12,14);
    }

    public TipoEmpresa getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpresa tipo) {
        this.tipo = tipo;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        if(tipo == TipoEmpresa.FILIAL) {
            this.nomeFilial = nomeFilial;
        }
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Regional getRegional() {
        return regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public List<Trilha> getTrilhas() {
        return Collections.unmodifiableList(trilhas);
    }

    public void addTrilha(Trilha trilha) {
        if(trilha != null && !trilhas.contains(trilha)) {
            trilhas.add(trilha);
        }
    }

    @Override
    public String toString() {
        return "\nNome: "+nome +
                "\nCNPJ: "+cnpj +
                "\nTipo da empresa: "+ tipo.getNome() +
                (tipo == TipoEmpresa.FILIAL ? "\nNome da filial: "+ nomeFilial : "") +
                "\nSegmento: "+ segmento.getNome() +
                "\nEstado: "+estado +
                "\nCidade: "+cidade +
                "\nRegional: "+ regional.getNome();
    }

}
