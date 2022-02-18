package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Modulo;
import br.com.habilitpro.enums.Avaliacao;
import br.com.habilitpro.pessoa.trabalhador.ModuloTrabalhador;
import br.com.habilitpro.pessoa.trabalhador.Trabalhador;
import static br.com.habilitpro.utils.Formatador.formatarCPF;
import static br.com.habilitpro.utils.menus.MenuEmpresa.*;
import static br.com.habilitpro.utils.menus.MenuModulo.getModulo;
import static br.com.habilitpro.utils.menus.MenuModulo.getModulos;
import static br.com.habilitpro.utils.Validador.validarString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuTrabalhador {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Trabalhador> trabalhadores = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar trabalhadores \n2 - Cadastrar trabalhador" +
                "\n3 - Associar módulo a um trabalhador \n4 - Editar avaliação de módulo associado " +
                "\n5 - Mudar de empresa \n6 - Editar função de um trabalhador" +
                "\n7 - Voltar ao menu principal \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {

            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "0";
            case 49:
                if(trabalhadores.isEmpty()) {
                    System.out.println("\nAinda não há trabalhadores cadastrados!");
                    break;
                }
                trabalhadores.forEach(System.out::println);
                break;
            case 50:
                opcao = cadastrarTrabalhador(null,null,null,null,null);
                break;
            case 51:
                opcao = associarModuloATrabalhador(null);
                break;
            case 52:
                opcao = avaliarModuloTrabalhador();
                break;
            case 53:
                opcao = mudarDeEmpresa(null);
                break;
            case 54:
                opcao = mudarDeFuncao(null);
                break;
            case 55: return "";
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

    private static Trabalhador getTrabalhador() {
        if(trabalhadores.isEmpty()) {
            System.out.println("\nPrimeiramente, cadastre algum trabalhador!");
            return null;
        }
        String doc = obterString("\nInforme o CPF do trabalhador: ");
        final String cpf = formatarCPF(doc);
        return trabalhadores.stream().filter(t -> t.getCpf().equals(cpf))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nTrabalhador não encontrado!"));
    }

    private static String mudarDeFuncao(Trabalhador trabalhador) {
        try {
            if(trabalhador == null) {
                trabalhador = getTrabalhador();
                return trabalhador == null ? "" : mudarDeFuncao(trabalhador);
            }
            String funcao = obterString("\nInforme a nova função de "+trabalhador.getNome());
            int index = trabalhadores.indexOf(trabalhador);
            trabalhador.setFuncao(funcao);
            trabalhadores.set(index, trabalhador);
            System.out.println("\nO trabalhador "+trabalhador.getNome()+ " mudou de função!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return mudarDeFuncao(trabalhador);
        }
    }

    private static String mudarDeEmpresa(Trabalhador trabalhador) {
        try {
            if(trabalhador == null) {
                trabalhador = getTrabalhador();
                return trabalhador == null ? "" : mudarDeEmpresa(trabalhador);
            }
            Empresa empresa = getEmpresa();
            int index = trabalhadores.indexOf(trabalhador);
            trabalhador.setEmpresa(empresa);
            trabalhadores.set(index, trabalhador);
            System.out.println("\nO trabalhador "+trabalhador.getNome()+ " mudou de empresa!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return mudarDeEmpresa(trabalhador);
        }
    }

    private static String avaliarModuloTrabalhador() {
        try {
            Trabalhador trabalhador = null;
            ModuloTrabalhador mt = criarModuloTrabalhador(null, trabalhador,null,null);
            if(mt == null) {
                return "";
            }
            int index = trabalhadores.indexOf(trabalhador);
            trabalhador.setModuloTrabalhador(mt.getModulo().getNome(), mt.getAvaliacao(), mt.getAnotacao());
            trabalhadores.set(index, trabalhador);
            System.out.println("\nO módulo "+mt.getModulo().getNome()+
                    " foi associado ao trabalhador "+trabalhador.getNome());
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return avaliarModuloTrabalhador();
        }
    }

    private static String associarModuloATrabalhador(Trabalhador trabalhador) {
        try {
            if(trabalhador == null) {
                trabalhador = getTrabalhador();
                if(trabalhador == null) {
                    return "";
                }
                ModuloTrabalhador mt = criarModuloTrabalhador(null, trabalhador,null,null);
                if(mt == null) {
                    return "";
                }
                int index = trabalhadores.indexOf(trabalhador);
                trabalhador.addModuloTrabalhador(mt);
                trabalhadores.set(index, trabalhador);
                System.out.println("\nO módulo "+mt.getModulo().getNome()+
                        " foi associado ao trabalhador "+trabalhador.getNome());
                return "";
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return associarModuloATrabalhador(trabalhador);
        }
        return "";
    }

    private static ModuloTrabalhador criarModuloTrabalhador(Modulo modulo, Trabalhador trabalhador,
                                                     String avaliacao, String anotacao) {

        try {
            if (modulo == null) {
                if(getModulos().isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre algum módulo!");
                    return null;
                }
                modulo = getModulo();
                return criarModuloTrabalhador(modulo, trabalhador, avaliacao, anotacao);
            }
            else if(trabalhador == null) {
                trabalhador = getTrabalhador();
                if(trabalhador == null) return null;
                if(!trabalhador.getModulosTrabalhador().contains(modulo)) {
                    System.out.println("\nO módulo" + modulo.getNome() +" não está associado a esse trabalhador!");
                    return null;
                }
                return criarModuloTrabalhador(modulo, trabalhador, avaliacao, anotacao);
            }
            else if(avaliacao == null) {
                boolean avaliar = opcaoBooleana("\nDeseja avaliar esse módulo? (S / N)");
                if(avaliar) {
                    Avaliacao av = (Avaliacao) getEnum(Avaliacao.values());
                    return criarModuloTrabalhador(modulo, trabalhador, av.getNome(), anotacao);
                }
                return criarModuloTrabalhador(modulo, trabalhador, "", anotacao);
            }
            else if(anotacao == null) {
                boolean anotar = opcaoBooleana("\nDeseja escrever alguma anotação? (S / N)");
                if(anotar) {
                    anotacao = obterString("\nDigite alguma anotação: ");
                    return criarModuloTrabalhador(modulo, trabalhador, avaliacao, anotacao);
                }
                return criarModuloTrabalhador(modulo, trabalhador, "", "");
            }
            return new ModuloTrabalhador(modulo, Avaliacao.getAvaliacao(avaliacao), anotacao, trabalhador);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return criarModuloTrabalhador(modulo, trabalhador, avaliacao, anotacao);
        }
    }

    private static String cadastrarTrabalhador(Empresa empresa, String cpf, String nome,
                                               String setor, String funcao) {

        try {
            if (empresa == null) {
                List<Empresa> empresas = getEmpresas();
                if(empresas.isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma empresa!");
                    return "0";
                }
                empresa = getEmpresa();
                return cadastrarTrabalhador(empresa, cpf, nome, setor, funcao);
            }
            else if(cpf == null) {
                String doc = obterString("\nInforme o CPF do trabalhador ou '0' para voltar: ");
                if(doc.equals("0")) return "";
                final String documento = formatarCPF(doc);
                Trabalhador tr = trabalhadores.stream().filter(t -> t.getCpf().equals(documento))
                                             .findFirst().orElse(null);
                if(tr != null) {
                    System.out.println("\nJá existe um trabalhador cadastrado com esse CPF!");
                    return cadastrarTrabalhador(empresa, null, nome, setor, funcao);
                }
                return cadastrarTrabalhador(empresa, documento, nome, setor, funcao);
            }
            else if(nome == null) {
                String n = obterString("\nInforme o nome do trabalhador: ");
                return cadastrarTrabalhador(empresa, cpf, n, setor, funcao);
            }
            else if(setor == null) {
                String s = obterString("\nInforme o setor da empresa onde trabalha: ");
                return cadastrarTrabalhador(empresa, cpf, nome, s, funcao);
            }
            else if(funcao == null) {
                String f = obterString("\nInforme a funçao do trabalhador: ");
                return cadastrarTrabalhador(empresa, cpf, nome, setor, f);
            }
            Trabalhador trabalhador = new Trabalhador(nome, cpf, empresa, setor, funcao);
            trabalhadores.add(trabalhador);
            System.out.println("\nTrabalhador cadastrado com sucesso!");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return cadastrarTrabalhador(empresa, cpf, nome, setor, funcao);
        }
        return "";
    }

    private static String obterString(String texto) {
        System.out.print(texto);
        String n = scanner.nextLine();
        return validarString(n, "");
    }

    private static boolean opcaoBooleana(String texto) {
        System.out.println(texto);
        String opt = scanner.nextLine();
        if(opt.equalsIgnoreCase("S")) {
            return true;
        }
        else if(opt.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException("\nOpção inválida!");
    }

}
