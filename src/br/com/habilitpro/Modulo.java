package br.com.habilitpro;

import br.com.habilitpro.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modulo {

    private Trilha trilha;
    private String nome;
    private List<String> habilidades;
    private String tarefaValidacao;
    private Status status;
    private int prazo_limite = 10;

    public Modulo(Trilha trilha, String nome,
                  List<String> habilidades, String tarefaValidacao) {

        this.trilha = trilha;
        this.habilidades = new ArrayList<>();


        this.trilha.addModulo(this);
    }

}
