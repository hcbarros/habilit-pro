package br.com.habilitpro;

import br.com.habilitpro.enums.Perfil;
import br.com.habilitpro.enums.Regional;
import br.com.habilitpro.enums.Segmento;
import br.com.habilitpro.enums.TipoEmpresa;
import br.com.habilitpro.interfaces.AuxilioEnum;
import br.com.habilitpro.utils.menus.MenuEmpresa;
import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static br.com.habilitpro.utils.Local.buscarCidades;
import static br.com.habilitpro.utils.Local.buscarEstados;
import static br.com.habilitpro.utils.Validador.*;

public class Main {

    public static void main(String[] args) {

//         System.out.println(ehCpfValido("02657226400"));
//         System.out.println(buscarEstados());
//           System.out.println(buscarCidades("3392268"));

        //buscarEstados();
//        String valor = MenuEmpresa.getLocal("3392268");
//        System.out.println(valor);

        //Segmento segmento = (Segmento) MenuEmpresa.getEnum(Segmento.values());

        //System.out.println(segmento);

        MenuEmpresa.menu();
    }

}
