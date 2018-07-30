package com.leocaliban.loja.api.services.validators;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.leocaliban.loja.api.domain.enums.TipoCliente;
import com.leocaliban.loja.api.dto.ClienteNovoDTO;
import com.leocaliban.loja.api.resources.exceptions.MensagemDoCampo;
import com.leocaliban.loja.api.services.validators.utils.CPF_CNPJ;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {
	
	@Override
	public void initialize(ClienteInsert anotacao) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objetoDTO, ConstraintValidatorContext context) {
		List<MensagemDoCampo> lista = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !CPF_CNPJ.isValidCPF(objetoDTO.getCpfOuCnpj())) {
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CPF inválido."));
		}
		
		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !CPF_CNPJ.isValidCNPJ(objetoDTO.getCpfOuCnpj())) {
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CNPJ inválido."));
		}
		
		//Percorrer a lista para capturar os erros e enviá-las ao validador do framework - método validador do handler.
		for (MensagemDoCampo erro : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(erro.getMensagem()).addPropertyNode(erro.getNomeDoCampo())
					.addConstraintViolation();
		}
		return lista.isEmpty();
	}
}

