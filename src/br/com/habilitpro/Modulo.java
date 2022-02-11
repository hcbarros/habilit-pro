package br.com.habilitpro;

import br.com.habilitpro.enums.Status;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        if(trilha == null) {
            throw new IllegalArgumentException("Informe a qual trilha esse módulo pertence!");
        }
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
        if(nome == null || nome.isEmpty() || nome.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do módulo!");
        }
        this.nome = nome;
    }

    public Status getStatus() {
        return status;
    }

    public void definirStatus(Status status) {
        if(inicioAvaliacao != null &&
                Period.between(inicioAvaliacao, LocalDate.now()).getDays() >= prazo_limite) {
            this.status = Status.FINALIZADO;
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
        if(habilidades == null) return;
        this.habilidades.addAll(Arrays.asList(habilidades));
        this.habilidades.removeIf(h -> h == null);
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

}
