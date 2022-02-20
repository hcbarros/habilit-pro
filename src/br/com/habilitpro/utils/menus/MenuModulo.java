package br.com.habilitpro.utils.menus;

import br.com.habilitpro.classesPrincipais.Modulo;
import br.com.habilitpro.classesPrincipais.Trilha;
import br.com.habilitpro.enums.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static br.com.habilitpro.utils.menus.MenuEmpresa.getEnum;
import static br.com.habilitpro.utils.menus.MenuTrilha.*;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.obterString;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.opcaoBooleana;

public class MenuModulo {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Modulo> modulos = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar módulos \n2 - Cadastrar módulo" +
                "\n3 - Editar módulo \n4 - Definir prazo de avaliação de módulo " +
                "\n5 - Voltar ao menu principal \n0 - Sair");

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
                opcao = cadastrarModulo();
                break;
            case 51:
                opcao = editarModulo(null);
                break;
            case 52:
                definirPrazo(null);
                break;
            case 53: return "";
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
                return definirPrazo(modulo);
            }
            String num = obterString("\nInforme o prazo limite para avaliação do módulo: ");
            int prazo = Integer.parseInt(num);
            if (prazo < 0) {
                System.out.println("\nInforme um valor positivo!");
                return definirPrazo(modulo);
            }
            int index = modulos.indexOf(modulo);
            modulo.setPrazo_limite(prazo);
            modulos.set(index, modulo);
            alterarTrilha(modulo);
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return definirPrazo(modulo);
        }
    }

    private static String editarModulo(Modulo modulo) {
        if (modulos.isEmpty()) {
            System.out.println("\nNão há módulos cadastrados!");
            return "";
        }
        try {
            if(modulo == null) {
                modulo = getModulo();
                return editarModulo(modulo);
            }
            Modulo m = criarModulo(modulo.getTrilha(), modulo.getNome(), null, null);
            int index = modulos.indexOf(modulo);
            modulo.setTarefaValidacao(m.getTarefaValidacao());
            modulo.definirStatus(m.getStatus());
            modulo.addHabilidades(m.getHabilidades().toArray(new String[0]));
            modulos.set(index, modulo);
            alterarTrilha(modulo);
            System.out.println("\nMódulo editado com sucesso!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return editarModulo(modulo);
        }
    }

    private static String cadastrarModulo() {
        if (getTrilhas().isEmpty()) {
            System.out.println("\nPrimeiramente, cadastre alguma trilha!");
            return "0";
        }
        Modulo modulo = criarModulo(null, null, null, null);
        modulos.add(modulo);
        alterarTrilha(modulo);
        System.out.println("\nMódulo cadastrado com sucesso!");
        return "";
    }

    private static Modulo criarModulo(Trilha trilha, String nome, String tarefa, String status) {
        try {
            if (trilha == null) {
                trilha = getTrilha();
                return criarModulo(trilha,nome,tarefa, status);
            }
            else if(nome == null) {
                final String n = obterString("\nInforme o nome do módulo: ");
                boolean contem = trilha.getModulos().stream().anyMatch(m -> m.getNome().equalsIgnoreCase(n));
                if(contem) {
                    System.out.println("\nA trilha "+trilha.getNome() +" já possui um módulo com esse nome!");
                    return criarModulo(trilha,null, tarefa, status);
                }
                return criarModulo(trilha, n, tarefa, status);
            }
            else if(tarefa == null) {
                tarefa = obterString("\ninforme a tarefa de validação ou '0' para pular: ");
                return criarModulo(trilha, nome, tarefa, status);
            }
            else if(status == null) {
                boolean definir = opcaoBooleana("\nDeseja informar o status do módulo? (S / N) ");
                if(definir) {
                    Status s = (Status) getEnum(Status.values(), "o status do módulo:");
                    return criarModulo(trilha, nome, tarefa, s == null ? null : s.getNome());
                }
                return criarModulo(trilha, nome, tarefa, "");
            }
            String hab = obterString("\nInforme uma habilidade ou '0' para continuar: ");
            Modulo modulo = new Modulo(trilha, nome, Status.getStatus(status),
                    tarefa.equals("0") ? null : tarefa);
            if(!hab.equals("0")) {
                modulo.addHabilidades(hab);
            }
            return modulo;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return criarModulo(trilha,nome,tarefa,status);
        }
    }

    public static Modulo getModulo() {
        Trilha trilha = getTrilha();
        final String nome = obterString("\nInforme o nome do módulo: ");
        return trilha.getModulos().stream().filter(t -> t.getNome().equalsIgnoreCase(nome))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nMódulo não encontrado!"));
    }

    public static List<Modulo> getModulos() {
        return Collections.unmodifiableList(modulos);
    }

}
