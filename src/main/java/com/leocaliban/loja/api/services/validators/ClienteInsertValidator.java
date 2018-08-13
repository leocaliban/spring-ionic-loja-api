package com.leocaliban.loja.api.services.validators;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.domain.enums.TipoCliente;
import com.leocaliban.loja.api.dto.ClienteNovoDTO;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.resources.exceptions.MensagemDoCampo;
import com.leocaliban.loja.api.services.validators.utils.CPF_CNPJ;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert anotacao) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objetoDTO, ConstraintValidatorContext context) {
		List<MensagemDoCampo> lista = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		System.out.println(objetoDTO);
		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !CPF_CNPJ.isValidCPF(objetoDTO.getCpfOuCnpj())) {
			System.out.println("entrou");
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CPF inválido."));
		}
		
		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !CPF_CNPJ.isValidCNPJ(objetoDTO.getCpfOuCnpj())) {
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CNPJ inválido."));
		}
		
		Cliente clienteComEmail = repository.findByEmail(objetoDTO.getEmail());
		//Se achar o cliente com email buscado no repositório então o email já existe.
		if(clienteComEmail != null) {
			lista.add(new MensagemDoCampo("email", "O E-mail informado já existe."));
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

