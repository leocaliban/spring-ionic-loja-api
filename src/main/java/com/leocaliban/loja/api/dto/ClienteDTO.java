package com.leocaliban.loja.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.leocaliban.loja.api.domain.Cliente;

public class ClienteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "O preenchimento do nome é obrigatório.")
	@Length(min = 5, max = 120, message = "O tamanho do nome deve ser entre 5 e 120 caracteres.")
	private String nome;
	
	@NotEmpty(message = "O preenchimento do E-mail é obrigatório.")
	@Email(message = "Este E-mail está inválido.")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
		this.email = objeto.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
