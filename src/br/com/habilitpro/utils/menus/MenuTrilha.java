package br.com.habilitpro.utils.menus;

import br.com.habilitpro.classesPrincipais.Empresa;
import br.com.habilitpro.classesPrincipais.Modulo;
import br.com.habilitpro.classesPrincipais.Trilha;
import br.com.habilitpro.enums.Satisfacao;

import static br.com.habilitpro.utils.Validador.validarObjeto;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEmpresas;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEmpresa;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEnum;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.obterString;

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
                        "\n4 - Escrever anotação em uma trilha \n5 - Voltar ao menu principal \n0 - Sair");

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
            case 53: return "";
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
            Satisfacao sat = (Satisfacao) getEnum(Satisfacao.values(),"o nível de satisfação com a trilha:");
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
            String anot = obterString("\nEscreva alguma anotação: ");
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
                empresa = getEmpresa();
                return cadastrarTrilha(empresa, ocupacao);
            } else if (ocupacao == null) {
                ocupacao = obterString("\nInforme o nome da ocupação para essa trilha: ");
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
        final String nome = obterString("\nInforme o nome da trilha: ");
        return trilhas.stream().filter(t -> t.getNome().equalsIgnoreCase(nome))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nTrilha não encontrada!"));
    }

    public static void alterarTrilha(Modulo modulo) {
        for(int i = 0; i < trilhas.size(); i++) {
            if(modulo.getTrilha().getNome().equals(trilhas.get(i).getNome())) {
                Trilha t = trilhas.get(i);
                t.addModulo(modulo);
                trilhas.set(i, t);
            }
        }
     }

}
