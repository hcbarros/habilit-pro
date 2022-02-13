package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Trilha;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEmpresas;
import static br.com.habilitpro.utils.Formatador.formatarCNPJ;
import static br.com.habilitpro.utils.Validador.validarString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTrilha {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Trilha> trilhas = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar trilhas \n2 - Cadastrar trilhas" +
                        "\n3 - Editar trilha \n4 - Voltar ao menu principal \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {

            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "0";
            case 49:
                if(trilhas.isEmpty()) {
                    System.out.println("\nAinda não há trilhas cadastradas!");
                    break;
                }
                trilhas.forEach(System.out::println);
                break;
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return menu();
    }

    private static Trilha criarTrilha(Empresa empresa, String ocupacao) {

        try {
            if (empresa == null) {
                List<Empresa> empresas = getEmpresas();
                if(empresas.isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma empresa!");
                    return "";
                }
                System.out.print("\nInforme o cnpj da empresa em que será criada a trilha: ");
                String cnpj = scanner.nextLine();
                String doc = formatarCNPJ(cnpj);
                Empresa em = empresas.stream().filter(e -> e.getCnpj().equals(doc))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("\nEmpresa não encontrada!"));
                return criarTrilha(em, ocupacao);
            } else if (ocupacao == null) {
                System.out.print("\nInforme o nome da ocupação para essa trilha: ");
                ocupacao = scanner.nextLine();
                validarString(ocupacao,"");
                return new Trilha(empresa, ocupacao);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return criarTrilha(empresa, ocupacao);
        }
        return criarTrilha(empresa, ocupacao);
    }

}
