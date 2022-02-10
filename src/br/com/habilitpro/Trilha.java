package br.com.habilitpro;

import java.time.LocalDate;

public class Trilha {

    private Empresa empresa;
    private String ocupacao;
    private String nome;
    private String apelido;
    private static int sequencial;

    public Trilha(Empresa empresa, String ocupacao) {

        if(empresa == null) {
            throw new IllegalArgumentException("A trilha deve estar associada a uma empresa!");
        }
        if(ocupacao == null || ocupacao.isEmpty() || ocupacao.isBlank()) {
            throw new IllegalArgumentException("Informe uma ocupação para a trilha!");
        }
        nome = ocupacao + empresa.getNome() +
                (++sequencial) + LocalDate.now().getYear();
        apelido = ocupacao + sequencial;
    }

}
