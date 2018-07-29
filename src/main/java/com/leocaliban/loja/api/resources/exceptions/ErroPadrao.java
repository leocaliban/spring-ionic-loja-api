package com.leocaliban.loja.api.resources.exceptions;

import java.io.Serializable;

/**
 * Classe auxiliar de erro padrão para encapsular os dados do erro de forma personalizada.
 * @author Leocaliban
 *
 * 27 de jul de 2018
 */
public class ErroPadrao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String mensagem;
	private Long timeStamp;
	
	public ErroPadrao(Integer status, String mensagem, Long timeStamp) {
		super();
		this.status = status;
		this.mensagem = mensagem;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
