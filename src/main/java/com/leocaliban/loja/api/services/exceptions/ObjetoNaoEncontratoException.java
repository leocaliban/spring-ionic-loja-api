package com.leocaliban.loja.api.services.exceptions;

public class ObjetoNaoEncontratoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoEncontratoException(String mensagem) {
		super(mensagem);
	}
	
	public ObjetoNaoEncontratoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
