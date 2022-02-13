package br.com.habilitpro.utils.menus;

import java.util.Scanner;

public class MenuPrincipal {

    public static String menu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEscolha uma opção: \n1 - Empresa \n2 - Trilha \n3 - Módulo" +
                            "\n4 - Trabalhador \n5 - Usuário \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "";
            case 49:
                opcao = MenuEmpresa.menu();
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

}
