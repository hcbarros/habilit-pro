package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Trilha;
import br.com.habilitpro.enums.Satisfacao;

import static br.com.habilitpro.utils.Validador.validarObjeto;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEmpresas;
import static br.com.habilitpro.utils.Formatador.formatarCNPJ;
import static br.com.habilitpro.utils.Validador.validarString;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuTrilha {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Trilha> trilhas = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar trilhas \n2 - Cadastrar trilha" +
                        "\n3 - Definir nível de satisfação de uma trilha " +
                        "\n4 - Escrever anotação em uma trilha \n0 - Sair");

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
            case 50:
                opcao = cadastrarTrilha(null,null);
                break;
            case 51:
                opcao = definirSatisfacao(null);
                break;
            case 52:
                escreverAnotacao(null);
                break;
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

    private static String definirSatisfacao(Trilha trilha) {
        try {
            if(trilha == null) {
                if(trilhas.isEmpty()) {
                    System.out.println("\nNão há trilhas cadastradas!");
                    return "";
                }
                trilha = getTrilha();
                return definirSatisfacao(trilha);
            }
            int index = trilhas.indexOf(trilha);
            Satisfacao sat = (Satisfacao) getEnum(Satisfacao.values());
            validarObjeto(sat, "\nOpção inválida!");
            trilha.setSatisfacao(sat);
            trilhas.set(index, trilha);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return trilha == null ? "" : definirSatisfacao(trilha);
        }
        return "";
    }

    private static String escreverAnotacao(Trilha trilha) {
        try {
            if(trilha == null) {
                if(trilhas.isEmpty()) {
                    System.out.println("\nNão há trilhas cadastradas!");
                    return "";
                }
                trilha = getTrilha();
                return escreverAnotacao(trilha);
            }
            int index = trilhas.indexOf(trilha);
            System.out.print("\nEscreva alguma anotação: ");
            String anot = scanner.nextLine();
            validarString(anot, "");
            trilha.setAnotacoes(anot);
            trilhas.set(index, trilha);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return trilha == null ? "" : escreverAnotacao(trilha);
        }
        return "";
    }

    private static String cadastrarTrilha(Empresa empresa, String ocupacao) {
        try {
            if (empresa == null) {
                List<Empresa> empresas = getEmpresas();
                if(empresas.isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma empresa!");
                    return "0";
                }
                System.out.print("\nInforme o cnpj da empresa em que será criada a trilha: ");
                String cnpj = scanner.nextLine();
                String doc = formatarCNPJ(cnpj);
                Empresa em = empresas.stream().filter(e -> e.getCnpj().equals(doc))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("\nEmpresa não encontrada!"));
                return cadastrarTrilha(em, ocupacao);
            } else if (ocupacao == null) {
                System.out.print("\nInforme o nome da ocupação para essa trilha: ");
                ocupacao = scanner.nextLine();
                validarString(ocupacao,"");
                trilhas.add(new Trilha(empresa, ocupacao));
                System.out.println("\nTrilha cadastrada com sucesso!");
                return "";
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return cadastrarTrilha(empresa, null);
        }
        return cadastrarTrilha(empresa, ocupacao);
    }

    public static List<Trilha> getTrilhas() {
        return Collections.unmodifiableList(trilhas);
    }

    public static Trilha getTrilha() {
        System.out.print("\nInforme o nome da trilha a ser editada: ");
        final String nome = scanner.nextLine();
        return trilhas.stream().filter(t -> t.getNome().equalsIgnoreCase(nome))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nTrilha não encontrada!"));
    }

    public static int indiceTrilha(Trilha trilha) {
        return trilhas.indexOf(trilha);
    }

    public static void setTrilha(int index, Trilha trilha) {
        trilhas.set(index, trilha);
    }

}
