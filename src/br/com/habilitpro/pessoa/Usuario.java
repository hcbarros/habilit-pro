package br.com.habilitpro.pessoa;

import br.com.habilitpro.enums.Perfil;

import static br.com.habilitpro.utils.Validador.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario extends Pessoa {

    private String email;
    private String senha;
    private List<Perfil> perfis;


    public Usuario(String nome, String cpf, String email, String senha, Perfil ...perfis) {
        super(nome, cpf);
        setEmail(email);
        setSenha(senha);
        this.perfis = new ArrayList<>();
        addPerfis(perfis);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!ehEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido!");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(!ehSenhaValida(senha)) {
            throw new IllegalArgumentException("Senha inválida!");
        }
        this.senha = senha;
    }

    public List<Perfil> getPerfis() {
        return Collections.unmodifiableList(perfis);
    }

    public void addPerfis(Perfil ...perfis) {
        if(perfis != null) {
            Arrays.asList(perfis).forEach(p -> {
                if(p != null && !this.perfis.contains(p)) {
                    this.perfis.add(p);
                }
            });
        }
    }


}
