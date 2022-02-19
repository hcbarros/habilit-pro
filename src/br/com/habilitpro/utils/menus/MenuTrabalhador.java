package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.Modulo;
import br.com.habilitpro.enums.Avaliacao;
import br.com.habilitpro.enums.Status;
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
                "\n3 - Editar trabalhador \n4 - Associar módulo a um trabalhador " +
                "\n5 - Editar avaliação de módulo associado \n6 - Mudar de empresa " +
                "\n7 - Listar módulos associados a um trabalhador \n8 - Voltar ao menu principal \n0 - Sair");

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
                opcao = cadastrarTrabalhador();
                break;
            case 51:
                opcao = editarTrabalhador(null);
                break;
            case 52:
                opcao = associarModuloATrabalhador(null);
                break;
            case 53:
                opcao = avaliarModuloTrabalhador();
                break;
            case 54:
                opcao = mudarDeEmpresa(null);
                break;
            case 55:
                opcao = listarModulosTrabalhador(null);
                break;
            case 56: return "";
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return opcao.equals("0") ? "" : menu();
    }

    private static String listarModulosTrabalhador(Trabalhador trabalhador) {
        try {
            if(trabalhador == null) {
                trabalhador = getTrabalhador();
                return trabalhador == null ? "" : listarModulosTrabalhador(trabalhador);
            }
            if(trabalhador.getModulosTrabalhador().isEmpty()) {
                System.out.println("\nNão há módulos associados a esse trabalhador!");
            }
            trabalhador.getModulosTrabalhador().forEach(System.out::println);
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return listarModulosTrabalhador(trabalhador);
        }
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
                String cnpj = modulo.getTrilha().getEmpresa().getCnpj();
                if(trabalhador != null) {
                    if(trabalhador.getEmpresa().getCnpj() != cnpj){
                        System.out.println("\nEsse módulo pertence a empresa " + cnpj + " e " +
                                trabalhador.getNome() + " pertence a " + trabalhador.getEmpresa().getCnpj());
                        return null;
                    }
                    if(trabalhador.possuiModulo(modulo)) {
                        System.out.println("\nO módulo "+modulo.getNome() + " já está associado a esse trabalhador!");
                        return null;
                    }
                }
                return criarModuloTrabalhador(modulo, trabalhador, avaliacao, anotacao);
            }
            else if(trabalhador == null) {
                if(modulo.getStatus() == Status.FINALIZADO) {
                    System.out.println("\nA avaliação deste módulo está bloqueada para edição!");
                    return null;
                }
                trabalhador = getTrabalhador();
                if(trabalhador == null) return null;
                if(!trabalhador.possuiModulo(modulo)) {
                    System.out.println("\nO módulo " + modulo.getNome() +" não está associado a esse trabalhador!");
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

    private static String editarTrabalhador(Trabalhador trabalhador) {

         try {
            if(trabalhador == null) {
                 String doc = obterString("\nInforme o CPF do trabalhador ou '0' para voltar: ");
                 if(doc.equals("0")) return "";
                 final String documento = formatarCPF(doc);
                 trabalhador = trabalhadores.stream().filter(t -> t.getCpf().equals(documento)).findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("\nTrabalhador não encontrado!"));
                 return editarTrabalhador(trabalhador);
             }
             Trabalhador tr = criarTrabalhador(trabalhador.getEmpresa(), trabalhador.getCpf(),
                                                null,null,null);
             if(tr == null) return "";
             int index = trabalhadores.indexOf(trabalhador);
             trabalhador.setNome(tr.getNome());
             trabalhador.setFuncao(tr.getFuncao());
             trabalhador.setSetor(tr.getSetor());
             trabalhadores.set(index, trabalhador);
             System.out.println("\nO trababalhador "+trabalhador.getNome() +" foi editado com sucesso!");
             return "";
         }
         catch (IllegalArgumentException e) {
             System.out.println(e.getMessage());
             return editarTrabalhador(trabalhador);
         }
    }

    private static String cadastrarTrabalhador() {
        Trabalhador trabalhador = criarTrabalhador(null,null,null,null,null);
        if(trabalhador == null) return "";
        trabalhadores.add(trabalhador);
        System.out.println("\nTrabalhador cadastrado com sucesso!");
        return "";
    }

    private static Trabalhador criarTrabalhador(Empresa empresa, String cpf, String nome,
                                               String setor, String funcao) {

        try {
            if (empresa == null) {
                List<Empresa> empresas = getEmpresas();
                if(empresas.isEmpty()) {
                    System.out.println("\nPrimeiramente, cadastre alguma empresa!");
                    return null;
                }
                empresa = getEmpresa();
                return criarTrabalhador(empresa, cpf, nome, setor, funcao);
            }
            else if(cpf == null) {
                String doc = obterString("\nInforme o CPF do trabalhador ou '0' para voltar: ");
                if(doc.equals("0")) return null;
                final String documento = formatarCPF(doc);
                Trabalhador tr = trabalhadores.stream().filter(t -> t.getCpf().equals(documento))
                                             .findFirst().orElse(null);
                if(tr != null) {
                    System.out.println("\nJá existe um trabalhador cadastrado com esse CPF!");
                    return criarTrabalhador(empresa, null, nome, setor, funcao);
                }
                return criarTrabalhador(empresa, documento, nome, setor, funcao);
            }
            else if(nome == null) {
                String n = obterString("\nInforme o nome do trabalhador: ");
                return criarTrabalhador(empresa, cpf, n, setor, funcao);
            }
            else if(setor == null) {
                String s = obterString("\nInforme o setor da empresa onde trabalha: ");
                return criarTrabalhador(empresa, cpf, nome, s, funcao);
            }
            else if(funcao == null) {
                String f = obterString("\nInforme a funçao do trabalhador: ");
                return criarTrabalhador(empresa, cpf, nome, setor, f);
            }
            return new Trabalhador(nome, cpf, empresa, setor, funcao);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return criarTrabalhador(empresa, cpf, nome, setor, funcao);
        }
    }

    private static String obterString(String texto) {
        System.out.print(texto);
        String n = scanner.nextLine();
        return validarString(n, "");
    }

    private static boolean opcaoBooleana(String texto) {
        System.out.print(texto);
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
