package br.com.habilitpro.pessoa;

import br.com.habilitpro.enums.Perfil;

import static br.com.habilitpro.utils.Validador.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Usuario extends Pessoa {

    private String email;
    private String senha;
    private List<Perfil> perfis;


    public Usuario(String nome, String cpf, String email, String senha, Perfil perfil) {
        super(nome, cpf);
        setEmail(email);
        setSenha(senha);
        this.perfis = new ArrayList<>();
        addPerfil(perfil);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarEmail(email);
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        validarSenha(senha);
        this.senha = senha;
    }

    public List<Perfil> getPerfis() {
        return Collections.unmodifiableList(perfis);
    }

    public void addPerfil(Perfil perfil) {
        if(perfil != null && !perfis.contains(perfil)) {
            perfis.add(perfil);
        }
    }


}
