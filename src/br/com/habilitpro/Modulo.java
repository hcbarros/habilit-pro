package br.com.habilitpro;

import br.com.habilitpro.enums.Status;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static br.com.habilitpro.utils.Validador.*;
import static br.com.habilitpro.utils.Formatador.formatarData;


public class Modulo {

    private Trilha trilha;
    private String nome;
    private List<String> habilidades;
    private String tarefaValidacao;
    private Status status;
    private int prazo_limite = 10;
    private LocalDate inicioAvaliacao;

    public Modulo(Trilha trilha, String nome, Status status,
                  String tarefaValidacao, String ...habilidades) {

        validarObjeto(trilha, "Informe a qual trilha esse módulo pertence!");
        this.trilha = trilha;
        setNome(nome);
        definirStatus(status);
        this.habilidades = new ArrayList<>();
        addHabilidades(habilidades);
        this.trilha.addModulo(this);
    }


    public Trilha getTrilha() {
        return trilha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarString(nome, "Informe o nome do módulo!");
        this.nome = nome;
    }

    public Status getStatus() {
        return status;
    }

    public void definirStatus(Status status) {
        if(inicioAvaliacao != null) {
            int periodo = Period.between(inicioAvaliacao, LocalDate.now()).getDays();
            if(periodo >= prazo_limite) {
                this.status = Status.FINALIZADO;
            }
        }
        if(this.status != Status.FINALIZADO) {
            if(status == Status.EM_FASE_AVALIACAO) {
                inicioAvaliacao = LocalDate.now();
            }
            this.status = status;
        }
    }

    public String getTarefaValidacao() {
        return tarefaValidacao;
    }

    public void setTarefaValidacao(String tarefaValidacao) {
        this.tarefaValidacao = tarefaValidacao;
    }

    public List<String> getHabilidades() {
        return Collections.unmodifiableList(habilidades);
    }

    public void addHabilidades(String ...habilidades) {
        if(habilidades != null) {
            Arrays.asList(habilidades).forEach(h -> {
                if(h != null && !this.habilidades.contains(h.toUpperCase())) {
                    this.habilidades.add(h.toUpperCase());
                }
            });
        }
    }

    public int getPrazo_limite() {
        return prazo_limite;
    }

    public void setPrazo_limite(int prazo_limite) {
        this.prazo_limite = prazo_limite;
    }

    public LocalDate getInicioAvaliacao() {
        return inicioAvaliacao;
    }

    @Override
    public String toString() {
        String textoHabilidades = habilidades.isEmpty() ? "Não há habilidades cadastradas!" : "";
        for(String h: habilidades) {
            textoHabilidades += "\n\t- "+h;
        }
        return "\nNome: "+nome +
                "\nTarefa avaliação: "+tarefaValidacao +
                (status == null ? "" : "\nStatus: "+ status.getNome()) +
                "\nPrazo limite: "+prazo_limite +
                "\nInício da avaliação: "+ formatarData(inicioAvaliacao) +
                "\nNome da trilha: "+trilha.getNome() +
                "\nOcupação da trilha: "+trilha.getOcupacao() +
                "Habilidades: "+ textoHabilidades;
    }
}
