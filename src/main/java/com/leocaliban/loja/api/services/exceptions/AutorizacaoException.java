package com.leocaliban.loja.api.services.exceptions;

public class AutorizacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AutorizacaoException(String mensagem) {
		super(mensagem);
	}
	
	public AutorizacaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
