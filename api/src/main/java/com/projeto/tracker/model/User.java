package com.projeto.tracker.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "usuario")
public class User implements Serializable {
    private static final long serialVersionUID = 1311884942466303825L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    public String name;

    @ElementCollection
    public List<Card> cartoes = new ArrayList<Card>();

    public User(Long id, String name, List<Card> cartoes) {
        this.id = id;
        this.name = name;
        this.cartoes = cartoes;
    }

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
}
