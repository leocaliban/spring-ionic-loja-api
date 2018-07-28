package com.leocaliban.loja.api.domain;

import javax.persistence.Entity;

import com.leocaliban.loja.api.domain.enums.StatusPagamento;

@Entity
public class PagamentoCartao extends Pagamento {

	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;
	
	public PagamentoCartao() {
		
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public PagamentoCartao(Integer id, StatusPagamento status, Pedido pedido, Integer numeroParcelas) {
		super(id, status, pedido);
		this.numeroParcelas = numeroParcelas;
	}

}
