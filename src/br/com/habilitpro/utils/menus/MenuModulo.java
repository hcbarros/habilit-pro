package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Modulo;
import br.com.habilitpro.Trilha;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.habilitpro.utils.menus.MenuTrilha.getTrilha;
import static br.com.habilitpro.utils.menus.MenuTrilha.getTrilhas;
import static br.com.habilitpro.utils.Validador.validarString;

public class MenuModulo {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Modulo> modulos = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar módulos \n2 - Cadastrar módulo" +
                "\n3 - Avaliar módulo \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {

            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "0";
            case 49:
                if(modulos.isEmpty()) {
                    System.out.println("\nAinda não há módulos cadastrados!");
                    break;
                }
                modulos.forEach(System.out::println);
                break;
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

    private static String cadastrarModulo(Trilha trilha, String nome) {

        try {
            if (trilha == null) {
                if (getTrilhas().isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma trilha!");
                    return "0";
                }
                trilha = getTrilha();
                return cadastrarModulo(trilha,nome);
            }
            else if(nome == null) {
                System.out.print("\nInforme o nome do módulo: ");
                String n = scanner.nextLine();
                validarString(n,"");
                return cadastrarModulo(trilha, n);
            }

        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return cadastrarModulo(trilha,nome);
        }

        return "";
    }

}
