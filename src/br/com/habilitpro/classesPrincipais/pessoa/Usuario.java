package br.com.habilitpro.classesPrincipais.pessoa;

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
        this.email = validarEmail(email);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = validarSenha(senha);
    }

    public List<Perfil> getPerfis() {
        return Collections.unmodifiableList(perfis);
    }

    public void addPerfil(Perfil perfil) {
        if(perfil != null && !perfis.contains(perfil)) {
            perfis.add(perfil);
        }
    }

    @Override
    public String toString() {
        String perfis = "";
        for(Perfil p: this.perfis) {
            perfis += "\n\t- "+p.getNome();
        }
        return "\nNome: "+getNome() +
                "\nCPF: "+getCpf() +
                "\nEmail: "+email +
                "\nSenha: "+senha +
                (perfis.isEmpty() ? "" : "\nPerfis: "+ perfis);
    }

}
