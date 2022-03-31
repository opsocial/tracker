package com.projeto.tracker.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity(name = "projeto")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1311884942466303825L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProjeto")
	private Long idProjeto;

	@Column(name = "nome")
	private String nome;

	@Column(name = "segmento")
	private String segmento;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
	private User idUsuario;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "deckArqName")
	private String deckNomeDoArquivo;

	public Projeto() {
	}

	public Projeto(Long id_projeto, String nome, String segmento, User id_usuario, String descricao, String deckNomeDoArquivo) {
		this.idProjeto = id_projeto;
		this.nome = nome;
		this.segmento = segmento;
		this.idUsuario = id_usuario;
		this.descricao = descricao;
		this.deckNomeDoArquivo = deckNomeDoArquivo;
	}

	public Long getId_projeto() {
		return idProjeto;
	}

	public void setId_projeto(Long id_projeto) {
		this.idProjeto = id_projeto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public User getId_usuario() {
		return idUsuario;
	}

	public void setId_usuario(User id_usuario) {
		this.idUsuario = id_usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDeckNomeDoArquivo() {
		return deckNomeDoArquivo;
	}

	public void setDeckNomeDoArquivo(String deckNomeDoArquivo) {
		this.deckNomeDoArquivo = deckNomeDoArquivo;
	}
}
