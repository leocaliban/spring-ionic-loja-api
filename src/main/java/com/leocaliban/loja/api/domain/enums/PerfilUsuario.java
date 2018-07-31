package com.leocaliban.loja.api.domain.enums;

public enum PerfilUsuario {
	
	//ROLE - É exigência do framework para o reconhecimento de autorização.
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private PerfilUsuario(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static PerfilUsuario toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		for(PerfilUsuario tipo : PerfilUsuario.values()) {
			if(codigo.equals(tipo.getCodigo())) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
