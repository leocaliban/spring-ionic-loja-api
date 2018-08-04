package com.leocaliban.loja.api.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErroDoValidador extends ErroPadrao {
	
	private static final long serialVersionUID = 1L;
	
	private List<MensagemDoCampo> erros = new ArrayList<>();

	public ErroDoValidador(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);

	}

	public List<MensagemDoCampo> getErros() {
		return erros;
	}

	//Adiciona os erros na lista um por um. [No lugar do set]
	public void adicionarErro(String nomeDoCampo, String mensagem) {
		erros.add(new MensagemDoCampo(nomeDoCampo, mensagem));
	}

}
