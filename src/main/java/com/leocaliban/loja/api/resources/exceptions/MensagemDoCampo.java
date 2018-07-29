package com.leocaliban.loja.api.resources.exceptions;

import java.io.Serializable;

/**
 * Classe auxiliar para carregar os dados dos erros de validação.
 * @author Leocaliban
 *
 * 29 de jul de 2018
 */
public class MensagemDoCampo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nomeDoCampo;
	
	private String mensagem;
	
	public MensagemDoCampo() {
		
	}

	public MensagemDoCampo(String nomeDoCampo, String mensagem) {
		super();
		this.nomeDoCampo = nomeDoCampo;
		this.mensagem = mensagem;
	}

	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

	public void setNomeDoCampo(String nomeDoCampo) {
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
