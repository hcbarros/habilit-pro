package br.com.habilitpro;

import br.com.habilitpro.enums.Perfil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.habilitpro.utils.Local.buscarCidades;
import static br.com.habilitpro.utils.Local.buscarEstados;
import static br.com.habilitpro.utils.Validador.*;

public class Main {

    static List<Perfil> list = new ArrayList<>();

    public static void main(String[] args) {

//         System.out.println(ehCpfValido("02657226400"));
//         System.out.println(buscarEstados());
//         System.out.println(buscarCidades("3392268"));


        addPerfis(Perfil.RH, Perfil.ADMINISTRATIVO, null);
        list.forEach(System.out::println);
    }

    public static void addPerfis(Perfil...perfis) {
        if(perfis != null) {
            Arrays.asList(perfis).forEach(p -> {
                if(p != null) list.add(p);
            });
        }
    }

}
