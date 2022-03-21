package com.projeto.tracker.model;

import lombok.Data;
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
    private Long id;

    @Column(name = "nome", nullable = false)
    public String name;

    @Column(name = "senha", nullable = false)
    public String senha;

    @Column(name = "CPF", nullable = false)
    public String cpf;

    @Column(name = "ativo", nullable = false)
    public Boolean ativo;

    @ElementCollection
    public List<Card> cartoes = new ArrayList<Card>();

    public User(Long id, String name, String senha, String cpf, Boolean ativo, List<Card> cartoes) {
        this.id = id;
        this.name = name;
        this.senha = senha;
        this.cpf = cpf;
        this.ativo = ativo;
        this.cartoes = cartoes;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Card> cartoes) {
        this.cartoes = cartoes;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
