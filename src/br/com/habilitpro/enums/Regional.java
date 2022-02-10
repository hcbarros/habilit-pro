package br.com.habilitpro.enums;

public enum Regional {

    ALTO_URUGUAI_CATARINENSE("Alto Uruguai Catarinense"),
    ALTO_VALE_DO_ITAJAI("Alto Vale do Itajaí"),
    CENTRO_NORTE("Centro-Norte"),
    CENTRO_OESTE("Centro-Oeste"),
    EXTREMO_OESTE("Extremo Oeste"),
    FOZ_DO_RIO_ITAJAI("Foz do Rio Itajaí"),
    LITORAL_SUL("Litoral Sul"),
    NORTE_NORDESTE("Norte-Nordeste"),
    OESTE("Oeste"),
    PLANALTO_NORTE("Planalto Norte"),
    SERRA_CATARINENSE("Serra Catarinense"),
    SUDESTE("Sudeste"),
    SUL("SUL"),
    VALE_DO_ITAJAI("Vale do Itajaí"),
    VALE_ITAJAI_MIRIM("Vale do Itajaí Mirim"),
    VALE_DO_ITAPOCU("Vale do Itapocu");

    private String nome;

    Regional(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
