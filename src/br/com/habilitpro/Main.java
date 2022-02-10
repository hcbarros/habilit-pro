package br.com.habilitpro;

import java.io.IOException;
import static br.com.habilitpro.utils.Local.buscarCidades;
import static br.com.habilitpro.utils.Local.buscarEstados;
import static br.com.habilitpro.utils.Validador.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // System.out.println(ehCpfValido("02657226400"));
        System.out.println(buscarEstados());
        System.out.println(buscarCidades("3408096"));
    }

}
