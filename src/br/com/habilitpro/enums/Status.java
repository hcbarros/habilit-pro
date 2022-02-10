package br.com.habilitpro.enums;

public enum Status {

    EM_ANDAMENTO("Curso em andamento"),
    EM_FASE_AVALIACAO("Em fase de avaliação"),
    FINALIZADO("Fase de avaliação finalizada");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
