package com.projeto.tracker.model;

public class Email {

    private String name;
    private String email;
    private String mensagem;
    private String nomeEmpresa;

    public Email() {}

    public Email(String name, String email, String mensagem, String nomeEmpresa) {
        this.name = name;
        this.email = email;
        this.mensagem = mensagem;
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
