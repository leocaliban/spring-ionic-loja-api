package com.leocaliban.loja.api.domain.enums;

/**
 * Enum que representa um tipo de cliente, 
 * implementação feita de modo que se mantém o controle dos tipos através do código colocado manualmente em cada um,
 * permitindo salvar o código em int no banco e ao recuperar, converter para o tipo String para visualização do mesmo.
 * @author Leocaliban
 *
 * 28 de jul de 2018
 */
public enum TipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"), 
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		for(TipoCliente tipo : TipoCliente.values()) {
			if(codigo.equals(tipo.getCodigo())) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
