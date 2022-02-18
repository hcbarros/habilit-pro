package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Modulo;
import br.com.habilitpro.Trilha;
import br.com.habilitpro.enums.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static br.com.habilitpro.utils.menus.MenuEmpresa.getEnum;
import static br.com.habilitpro.utils.Validador.validarString;
import static br.com.habilitpro.utils.menus.MenuTrilha.*;

public class MenuModulo {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Modulo> modulos = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar módulos \n2 - Cadastrar módulo" +
                "\n3 - Definir status \n4 - Definir prazo de avaliação de módulo " +
                "\n5 - Adicionar habilidade \n6 - Voltar ao menu principal \n0 - Sair");

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
            case 50:
                opcao = cadastrarModulo(null,null,null,null);
                break;
            case 51:
                definirStatus(null);
                break;
            case 52:
                definirPrazo(null);
                break;
            case 53:
                addHabilidade(null);
                break;
            case 54: return "";
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

    private static String definirPrazo(Modulo modulo) {
        try {
            if (modulo == null) {
                if(modulos.isEmpty()) {
                    System.out.println("\nAinda não há módulos cadastrados!");
                    return "";
                }
                modulo = getModulo();
            }
            System.out.print("\nInforme o prazo limite para avaliação do módulo: ");
            String num = scanner.nextLine();
            int prazo = Integer.parseInt(num);
            if (prazo < 0) {
                System.out.println("\nInforme um valor positivo!");
                return definirPrazo(modulo);
            }
            int index = modulos.indexOf(modulo);
            modulo.setPrazo_limite(prazo);
            modulos.set(index, modulo);
            alterarTrilha(modulo.getTrilha());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return definirPrazo(modulo);
        }
        return "";
    }

    private static String definirStatus(Modulo modulo) {
        if (modulo == null) {
            if(modulos.isEmpty()) {
                System.out.println("\nAinda não há módulos cadastrados!");
                return "";
            }
            try {
                modulo = getModulo();
                return definirStatus(modulo);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return definirStatus(modulo);
            }
        }
        int index = modulos.indexOf(modulo);
        String nome = definirStatus();
        Status status = Status.getStatus(nome);
        modulo.definirStatus(status);
        modulos.set(index, modulo);
        alterarTrilha(modulo.getTrilha());
        return "";
    }

    private static String definirStatus() {
        System.out.print("\nDeseja informar o status? (S / N)");
        String opt = scanner.nextLine();
        try {
            if (opt.equalsIgnoreCase("S")) {
                Status s = (Status) getEnum(Status.values());
                if(s != null) return s.getNome();
            } else if (opt.equalsIgnoreCase("N")) {
                return "";
            }
            else {
                System.out.println("\nOpção inválida!");
            }
            return definirStatus();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return definirStatus();
        }
    }

    private static String addHabilidade(Modulo modulo) {
        try {
            if(modulo == null) {
                if(modulos.isEmpty()) {
                    System.out.println("\nAinda não há módulos cadastrados!");
                    return "";
                }
                modulo = getModulo();
            }
            System.out.print("\nInforme uma habilidade ou '0' para continuar: ");
            String hab = scanner.nextLine();
            validarString(hab, "");
            if(hab.equals("0")) return "";
            int index = modulos.indexOf(modulo);
            modulo.addHabilidades(hab);
            modulos.set(index, modulo);
            alterarTrilha(modulo.getTrilha());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return addHabilidade(modulo);
        }
        return "";
    }

    private static String cadastrarModulo(Trilha trilha, String nome, String tarefa, String status) {

        try {
            if (trilha == null) {
                if (getTrilhas().isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma trilha!");
                    return "0";
                }
                trilha = getTrilha();
                return cadastrarModulo(trilha,nome,tarefa, status);
            }
            else if(nome == null) {
                System.out.print("\nInforme o nome do módulo: ");
                String n = scanner.nextLine();
                validarString(n,"");
                return cadastrarModulo(trilha, n, tarefa, status);
            }
            else if(tarefa == null) {
                System.out.print("\ninformar a tarefa de validação ou aperte 'enter' para pular: ");
                tarefa = scanner.nextLine();
                return cadastrarModulo(trilha, nome, tarefa, status);
            }
            else if(status == null) {
                status = definirStatus();
                return cadastrarModulo(trilha, nome, tarefa,status);
            }
            Modulo modulo = new Modulo(trilha, nome, Status.getStatus(status), tarefa);
            modulos.add(modulo);
            alterarTrilha(modulo.getTrilha());
            addHabilidade(modulo);
            System.out.println("\nMódulo cadastrado com sucesso!");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return cadastrarModulo(trilha,nome,tarefa,status);
        }
        return "";
    }

    public static Modulo getModulo() {
        System.out.print("\nInforme o nome do módulo: ");
        final String nome = scanner.nextLine();
        return modulos.stream().filter(t -> t.getNome().equalsIgnoreCase(nome))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nMódulo não encontrado!"));
    }

    public static List<Modulo> getModulos() {
        return Collections.unmodifiableList(modulos);
    }

}
