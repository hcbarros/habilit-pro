package br.com.habilitpro.enums;

import br.com.habilitpro.interfaces.AuxilioEnum;

public enum Segmento implements AuxilioEnum {

    ALIMENTOS_E_BEBIDAS("Alimentos e bebidas"),
    CELULOSE_E_PAPEL("Celulose e papel"),
    CONSTRUCAO("Construção"),
    EQUIPAMENTOS_ELETRICOS("Equipamentos elétricos"),
    FARMACOS_E_EQUIPAMENTOS_SAUDE("Fármacos e equipamentos de saúde"),
    FUMO("Fumo"),
    INDUSTRIA_AUTOMOTIVA("Indústria automotiva"),
    INDUSTRIA_CERAMICA("Indústria cerâmica"),
    INDUSTRIA_DIVERSA("Indústria diversa"),
    INDUSTRIA_EXTRATIVA("Indústria extrativa"),
    INDUSTRIA_GRAFICA("Indústria gráfica"),
    MADEIRAS_E_MOVEIS("Madeiras e móveis"),
    MAQUINAS_E_EQUIPAMENTOS("Máquinas e equipamentos"),
    METALMECANICA_E_METALURGIA("Metalmecânica e matalurgia"),
    OLEO_GAS_E_ELETRICIDADE("òleo, gás e eletricidade"),
    PRODUTOS_QUIMICOS_E_PLASTICOS("Produtos químicos e plásticos"),
    SANEAMENTO_BASICO("Saneamento básico"),
    TIC("TIC"),
    TEXTIL_CONFECCAO_COURO_E_CALCADOS("Têxtil, confecção, couro e calçados");


    private String nome;

    Segmento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
