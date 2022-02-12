package br.com.habilitpro;

import br.com.habilitpro.enums.Regional;
import br.com.habilitpro.enums.Segmento;
import br.com.habilitpro.enums.TipoEmpresa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.habilitpro.utils.Validador.ehCnpjValido;

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

        setNome(nome);
        setCnpj(cnpj);
        setTipo(tipo);
        setNomeFilial(nomeFilial);
        setSegmento(segmento);
        setEstado(estado);
        setCidade(cidade);
        setRegional(regional);
        this.trilhas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("Informe o nome da empresa!");
        }
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if(cnpj == null || !ehCnpjValido(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido!");
        }
        cnpj = cnpj.replaceAll("[^\\d]", "");
        this.cnpj = cnpj.substring(0,2)+"."+ cnpj.substring(2,5)+"."+
                cnpj.substring(5,8)+"/"+ cnpj.substring(8,12)+"-"+ cnpj.substring(12,14);
    }

    public TipoEmpresa getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpresa tipo) {
        if(tipo == null) {
            throw new IllegalArgumentException("Informe o tipo da empresa!");
        }
        this.tipo = tipo;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        if(tipo == TipoEmpresa.FILIAL) {
            if(nomeFilial == null || nomeFilial.isEmpty() || nomeFilial.isBlank()) {
                throw new IllegalArgumentException("Informe o nome da filial!");
            }
            this.nomeFilial = nomeFilial;
        }
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        if(segmento == null) {
            throw new IllegalArgumentException("Informe o segmento da empresa!");
        }
        this.segmento = segmento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if(estado == null || estado.isBlank() || estado.isEmpty()) {
            throw new IllegalArgumentException("Informe em que estado fica a empresa!");
        }
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if(cidade == null || cidade.isEmpty() || cidade.isBlank()) {
            throw new IllegalArgumentException("Informe em que cidade fica a empresa!");
        }
        this.cidade = cidade;
    }

    public Regional getRegional() {
        return regional;
    }

    public void setRegional(Regional regional) {
        if(regional == null) {
            throw new IllegalArgumentException("Informe a Regional SENAI!");
        }
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

}
