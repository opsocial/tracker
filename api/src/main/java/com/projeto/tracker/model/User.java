package com.projeto.tracker.model;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity(name = "usuario")
public class User implements Serializable {
    private static final long serialVersionUID = 1311884942466303825L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(name = "nome", nullable = false)
    public String nome;

    @Column(name = "CPF", nullable = false)
    public String cpf;

    @Column(name = "telefone", nullable = false)
    public String telefone;

    @Column(name = "email")
    public String email;

    @Column(name = "linkedin", nullable = false)
    public String linkedin;

    @Column(name = "ativo")
    public Boolean ativo;


    public User() {

    }

    public User(Long id_usuario, String name, String cpf, String telefone, String email, String linkedin, Boolean ativo) {
        this.id_usuario = id_usuario;
        this.nome = name;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.linkedin = linkedin;
        this.ativo = ativo;
    }


    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
