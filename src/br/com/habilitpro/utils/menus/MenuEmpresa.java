package br.com.habilitpro.utils.menus;

import br.com.habilitpro.Empresa;
import br.com.habilitpro.enums.Regional;
import br.com.habilitpro.enums.Segmento;
import br.com.habilitpro.enums.TipoEmpresa;
import br.com.habilitpro.interfaces.AuxilioEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static br.com.habilitpro.utils.Validador.validarString;
import static br.com.habilitpro.utils.Formatador.formatarCNPJ;
import static br.com.habilitpro.utils.Buscador.*;

public class MenuEmpresa {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Empresa> empresas = new ArrayList<>();

    public static String menu() {

        System.out.println("\nEscolha uma opção: \n1 - Listar empresas \n2 - Cadastrar empresa" +
                            "\n3 - Editar empresa \n4 - Voltar ao menu principal \n0 - Sair");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 48:
                System.out.println("\nAté logo! Volte sempre.");
                return "0";
            case 49:
                if(empresas.isEmpty()) {
                    System.out.println("\nAinda não há empresas cadastradas!");
                    break;
                }
                empresas.forEach(System.out::println);
                break;
            case 50:
                cadastrarEmpresa();
                break;
            case 51:
                editarEmpresa();
                break;
            case 52: return "";
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
        return menu();
    }

    private static String cadastrarEmpresa() {
        Empresa e = criarEmpresa(null,null,null,null,
                null,null,null,null);
        System.out.println("\nEmpresa cadastrada com sucesso!");
        empresas.add(e);
        return "";
    }

    private static String editarEmpresa() {
        System.out.print("\nInforme o CNPJ da empresa ou '0' para voltar ao menu anterior: ");
        String doc = scanner.nextLine();
        if(doc.equals("0")) return "";
        Empresa em = null;
        try{
            final String cnpj = formatarCNPJ(doc);
            em = empresas.stream().filter(e -> e.getCnpj().equals(cnpj))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("\nEmpresa não encontrada!"));
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return editarEmpresa();
        }
        int index = empresas.indexOf(em);
        Empresa emp = criarEmpresa(em.getNome(), em.getCnpj(),null,null,
                null,null,null,null);

        empresas.set(index, emp);
        System.out.println("\nEmpresa editada com sucesso!");
        return "";
    }

    private static Empresa criarEmpresa(String nome, String cnpj, TipoEmpresa tipo, String nomeFilial,
                                           Segmento segmento, String[] estado, String cidade, Regional regional) {

        try {
            if (nome == null) {
                System.out.print("\nInforme o nome da empresa: ");
                final String n = scanner.nextLine();
                long c = empresas.stream().filter(e -> e.getNome().equalsIgnoreCase(n)).count();
                if(c > 0) {
                    System.out.println("Já existe uma empresa cadastrada com esse nome!");
                }
                validarString(n, "");
                return criarEmpresa(c == 0 ? n : null,null,null,null,
                                null,null,null,null);
            }
            else if(cnpj == null) {
                System.out.print("\nInforme o CNPJ: ");
                String documento = scanner.nextLine();
                final String doc = formatarCNPJ(documento);
                long c = empresas.stream().filter(e -> e.getCnpj().equalsIgnoreCase(doc)).count();
                if(c > 0) {
                    System.out.println("Já existe uma empresa cadastrada com esse CNPJ!");
                }
                return criarEmpresa(nome, c == 0 ? doc : null, null,null,
                                null,null,null,null);
            }
            else if(tipo == null) {
                tipo = (TipoEmpresa) getEnum(TipoEmpresa.values());
                return criarEmpresa(nome, cnpj, tipo,null,null,
                                        null,null,null);
            }
            else if(nomeFilial == null && tipo == TipoEmpresa.FILIAL) {
                System.out.print("\nInforme o nome da filial: ");
                String filial = scanner.nextLine();
                validarString(filial,"");
                return criarEmpresa(nome, cnpj, tipo, filial,null,
                                        null,null,null);
            }
            else if(segmento == null) {
                segmento = (Segmento) getEnum(Segmento.values());
                return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento,
                                        null,null,null);
            }
            else if(estado == null) {
                estado = getLocal(null);
                return criarEmpresa(nome,cnpj,tipo,nomeFilial,segmento, estado,null,null);
            }
            else if(cidade == null) {
                String[] city = getLocal(estado[1]);
                cidade = city == null ? null : city[0];
                return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade,null);
            }
            else if(regional == null) {
                regional = (Regional) getEnum(Regional.values());
                return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.print("\nOpção inválida!");
            return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional);
        }
        return new Empresa(nome,cnpj,tipo,nomeFilial,segmento,estado[0],cidade,regional);
    }

    public static AuxilioEnum getEnum(AuxilioEnum[] array) {
        System.out.println("\nInforme o(a) "+array[0].getClass().getSimpleName()+ " da empresa: ");
        for(int i = 0; i < array.length; i++) {
            System.out.print((i%4 == 0 ? "\n" : "") + "    " + (i+1) + " - " + array[i].getNome());
        }
        String opt = scanner.nextLine();
        int i = Integer.parseInt(opt);
        if(i > 0 && i <= array.length) {
            return array[i - 1];
        }
        System.out.println("\nOpção inválida!");
        return null;
    }

    private static String[] getLocal(String valor) {
        System.out.println("\nInforme em que "+(valor == null ? "Estado" : "cidade")+" se localiza a empresa: ");
        Object[] array = null;
        TreeMap<String, String> estados = null;
        if(valor != null) {
            array = buscarCidades(valor).toArray();
        }
        else {
            estados = buscarEstados();
            array = estados.keySet().toArray();
        }
        for(int i = 0; i < array.length; i++) {
            System.out.print((i % 5 == 0 ? "\n" : "") + "    "+(i+1) + " - " + array[i]);
        }
        String opt = scanner.nextLine();
        int i = Integer.parseInt(opt);
        if(i > 0 && i <= array.length) {
            String v = (String) array[i - 1];
            String[] result = {v, valor == null ? estados.get(v) : ""};
            return result;
        }
        System.out.println("\nOpção inválida!");
        return null;
    }

    public static List<Empresa> getEmpresas() {
        return empresas;
    }
}
