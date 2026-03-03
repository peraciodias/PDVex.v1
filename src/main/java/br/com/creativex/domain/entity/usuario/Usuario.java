package br.com.creativex.domain.entity.usuario;

public class Usuario {

    private long id;
    private String nome;
    private String login;
    private String senha;
    private String perfil;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(long id, String nome, String perfil) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
