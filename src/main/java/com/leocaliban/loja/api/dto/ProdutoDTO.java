package com.leocaliban.loja.api.dto;

import java.io.Serializable;

import com.leocaliban.loja.api.domain.Produto;

public class ProdutoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private Double valor;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
		this.valor = objeto.getValor();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
