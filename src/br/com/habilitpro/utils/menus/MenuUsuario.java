package br.com.habilitpro.utils.menus;

import br.com.habilitpro.enums.Perfil;
import br.com.habilitpro.pessoa.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.habilitpro.utils.Validador.*;
import static br.com.habilitpro.utils.menus.MenuEmpresa.getEnum;

import static br.com.habilitpro.utils.Formatador.formatarCPF;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.obterString;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.opcaoBooleana;

public class MenuUsuario {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Usuario> usuarios = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar usuários \n2 - Cadastrar usuário" +
                "\n3 - Editar usuário \n4 - Adicionar perfil a um usuário" +
                "\n5 - Voltar ao menu principal \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "0";
            case 49:
                if(usuarios.isEmpty()) {
                    System.out.println("\nAinda não há usuários cadastrados!");
                    break;
                }
                usuarios.forEach(System.out::println);
                break;
            case 50:
                cadastrarUsuario();
                break;
            case 51:
                opcao = editarUsuario();
                break;
            case 52:
                opcao = addPerfil(null);
                break;
            case 53: return "";
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return menu();
    }

    private static String addPerfil(Usuario usuario) {
        try {
            if(usuarios.isEmpty()) {
                System.out.println("\nPrimeiramente, cadastre algum usuário!");
                return "";
            }
            if(usuario == null) {
                usuario = getUsuario();
                if(usuario == null) return "";
            }
            Perfil perfil = (Perfil) getEnum(Perfil.values(),"um perfil para o usuário:");
            int index = usuarios.indexOf(usuario);
            usuario.addPerfil(perfil);
            usuarios.set(index, usuario);
            System.out.println("\nPerfil adicionado com sucesso!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return addPerfil(usuario);
        }
    }

    private static String editarUsuario() {

        try {
            if(usuarios.isEmpty()) {
                System.out.println("\nPrimeiramente, cadastre algum usuário!");
                return "";
            }
            Usuario usuario = getUsuario();
            if(usuario == null) return "";
            Usuario u = criarUsuario(usuario.getCpf(),null,null,null,null);
            int index = usuarios.indexOf(usuario);
            usuario.setNome(u.getNome());
            usuario.setSenha(u.getSenha());
            usuario.setEmail(u.getEmail());
            if(!u.getPerfis().isEmpty()) {
                usuario.addPerfil(u.getPerfis().get(0));
            }
            usuarios.set(index, usuario);
            System.out.println("\nUsuário editado com sucesso!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return editarUsuario();
        }
    }

    private static void cadastrarUsuario() {
        Usuario u = criarUsuario(null,null,null,null,null);
        if(u == null) return;
        usuarios.add(u);
        System.out.println("\nUsuário cadastrado com sucesso!");
    }

    private static Usuario criarUsuario(String cpf, String nome, String email, String senha, Perfil perfil) {

        try {
            if(cpf == null) {
                String doc = obterString("\nInforme o CPF do usuário ou '0' para voltar: ");
                if(doc.equals("0")) return null;
                final String documento = formatarCPF(doc);
                Usuario usuario = usuarios.stream().filter(t -> t.getCpf().equals(documento))
                        .findFirst().orElse(null);
                if(usuario != null) {
                    System.out.println("\nJá existe um usuário cadastrado com esse CPF!");
                    return criarUsuario(null, nome, email, senha, perfil);
                }
                return criarUsuario(documento, nome, email, senha, perfil);
            }
            else if(nome == null) {
                String n = obterString("\nInforme o nome do usuário: ");
                return criarUsuario(cpf, n, email, senha, perfil);
            }
            else if(email == null) {
                String e = obterString("\nInforme o email do usuário: ");
                email = validarEmail(e);
                return criarUsuario(cpf, nome, email, senha, perfil);
            }
            else if(senha == null) {
                String s = obterString("\nInforme a senha do usuário: ");
                senha = validarSenha(s);
                return criarUsuario(cpf, nome, email, senha, perfil);
            }
            boolean adicionar = opcaoBooleana("\nDeseja adicionar um perfil ao usuário? (S / N) ");
            if(adicionar) {
                perfil = (Perfil) getEnum(Perfil.values(), "um perfil para o usuário:");
            }

            return new Usuario(nome, cpf, email, senha, perfil);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return criarUsuario(cpf, nome, email, senha, perfil);
        }
    }

    private static Usuario getUsuario() {
        System.out.print("\nInforme o CPF do usuário ou '0' para voltar: ");
        String doc = scanner.nextLine();
        if(doc.equals("0")) return null;
        final String cpf = formatarCPF(doc);
        return usuarios.stream().filter(t -> t.getCpf().equals(cpf)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("\nUsuário não encontrado!"));
    }

}
