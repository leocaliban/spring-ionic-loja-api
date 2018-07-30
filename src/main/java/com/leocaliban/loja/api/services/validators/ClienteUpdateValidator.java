package com.leocaliban.loja.api.services.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.dto.ClienteDTO;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.resources.exceptions.MensagemDoCampo;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteUpdate anotacao) {
	}

	@Override
	public boolean isValid(ClienteDTO objetoDTO, ConstraintValidatorContext context) {
		
		//para obter o parametro da uri e recuperar o id do item que será atualizado
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<MensagemDoCampo> lista = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista		
		Cliente clienteComEmail = repository.findByEmail(objetoDTO.getEmail());
	
		if(clienteComEmail != null && !clienteComEmail.getId().equals(uriId)) {
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

