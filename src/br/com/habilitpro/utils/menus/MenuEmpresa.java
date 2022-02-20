package br.com.habilitpro.utils.menus;

import br.com.habilitpro.classesPrincipais.Empresa;
import br.com.habilitpro.enums.Regional;
import br.com.habilitpro.enums.Segmento;
import br.com.habilitpro.enums.TipoEmpresa;
import br.com.habilitpro.interfaces.AuxilioEnum;

import java.util.*;

import static br.com.habilitpro.utils.Formatador.formatarCNPJ;
import static br.com.habilitpro.utils.Buscador.*;
import static br.com.habilitpro.utils.menus.MenuTrabalhador.obterString;

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
        try{
            String doc = obterString("\nInforme o CNPJ da empresa ou '0' para voltar ao menu anterior: ");
            if(doc.equals("0")) return "";
            final String cnpj = formatarCNPJ(doc);
            Empresa empresa = empresas.stream().filter(e -> e.getCnpj().equals(cnpj))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("\nEmpresa não encontrada!"));
            int index = empresas.indexOf(empresa);
            Empresa emp = criarEmpresa(empresa.getNome(), empresa.getCnpj(),null,null,
                    null,null,null,null);
            empresa.setTipo(emp.getTipo());
            String tipo = emp.getTipo() == TipoEmpresa.MATRIZ ?
                    null : emp.getNomeFilial();
            empresa.setNomeFilial(tipo);
            empresa.setSegmento(emp.getSegmento());
            empresa.setEstado(emp.getEstado());
            empresa.setCidade(emp.getCidade());
            empresa.setRegional(emp.getRegional());
            empresas.set(index, empresa);
            System.out.println("\nEmpresa editada com sucesso!");
            return "";
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return editarEmpresa();
        }
    }

    private static Empresa criarEmpresa(String nome, String cnpj, TipoEmpresa tipo, String nomeFilial,
                                           Segmento segmento, String[] estado, String cidade, Regional regional) {

        try {
            if (nome == null) {
                final String n = obterString("\nInforme o nome da empresa: ");
                boolean existe = empresas.stream().anyMatch(e -> e.getNome().equalsIgnoreCase(n));
                if(existe) {
                    System.out.println("\nJá existe uma empresa cadastrada com esse nome!");
                }
                return criarEmpresa(existe ? null : n,null,null,null,
                                null,null,null,null);
            }
            else if(cnpj == null) {
                String documento = obterString("\nInforme o CNPJ: ");
                final String doc = formatarCNPJ(documento);
                boolean existe = empresas.stream().anyMatch(e -> e.getCnpj().equals(doc));
                if(existe) {
                    System.out.println("\nJá existe uma empresa cadastrada com esse CNPJ!");
                }
                return criarEmpresa(nome, existe ? null : doc, null,null,
                                null,null,null,null);
            }
            else if(tipo == null) {
                tipo = (TipoEmpresa) getEnum(TipoEmpresa.values(), "o tipo da empresa:");
                return criarEmpresa(nome, cnpj, tipo,null,null,
                                        null,null,null);
            }
            else if(nomeFilial == null && tipo == TipoEmpresa.FILIAL) {
                String filial = obterString("\nInforme o nome da filial: ");
                return criarEmpresa(nome, cnpj, tipo, filial,null,
                                        null,null,null);
            }
            else if(segmento == null) {
                segmento = (Segmento) getEnum(Segmento.values(), "o segmento da empresa:");
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
                regional = (Regional) getEnum(Regional.values(), "a regional SENAI da empresa:");
                return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return criarEmpresa(nome, cnpj, tipo, nomeFilial, segmento, estado, cidade, regional);
        }
        return new Empresa(nome,cnpj,tipo,nomeFilial,segmento,estado[0],cidade,regional);
    }

    public static AuxilioEnum getEnum(AuxilioEnum[] array, String texto) {
        System.out.println("\nInforme "+texto);
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
        return Collections.unmodifiableList(empresas);
    }

    public static Empresa getEmpresa() {
        String cnpj = obterString("\nInforme o cnpj da empresa: ");
        final String doc = formatarCNPJ(cnpj);
        return empresas.stream().filter(e -> e.getCnpj().equals(doc))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("\nEmpresa não encontrada!"));
    }

}
