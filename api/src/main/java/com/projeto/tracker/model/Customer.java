package com.projeto.tracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "comprador")
@Getter
@Setter
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pagador;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    public Customer() {}

    public Customer(Long id_pagador, String nome, String email) {
        this.id_pagador = id_pagador;
        this.nome = nome;
        this.email = email;
    }
}
