package com.leocaliban.loja.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Estado;
import com.leocaliban.loja.api.repositories.EstadoRepository;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public Estado buscarPorId(Integer id) {
		Optional<Estado> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
	}
	
	public List<Estado> buscarTodos(){
		List<Estado> estados = repository.findAllByOrderByNome();
		if(estados == null) {
			throw new ObjetoNaoEncontratoException("Não foi encontrado nenhum estado.");
		}
		return estados;
		
	}
}
