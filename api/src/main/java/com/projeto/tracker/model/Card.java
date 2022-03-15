package com.projeto.tracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "cartao")
@Getter
@Setter
@Embeddable
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    public String numero;

    @Column(name = "nome_usuario")
    public String nome_usuario;

    @Column(name = "id_usuario")
    public Long id_usuario;

    @Column(name = "data_vencimento")
    public Date data_vencimento;

    @Column(name = "cod_verificador")
    public Integer cod_verificador;

    public Card(Long id, String numero, String nome_usuario, Long id_usuario, Date data_vencimento, Integer cod_verificador) {
        this.id = id;
        this.numero = numero;
        this.nome_usuario = nome_usuario;
        this.id_usuario = id_usuario;
        this.data_vencimento = data_vencimento;
        this.cod_verificador = cod_verificador;
    }


    public Card() {}
}
