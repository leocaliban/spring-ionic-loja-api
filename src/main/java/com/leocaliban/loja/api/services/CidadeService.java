package com.leocaliban.loja.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.repositories.CidadeRepository;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> buscarPorEstado(Integer estadoId){
		List<Cidade> cidades = repository.findCidades(estadoId);
		if(cidades == null) {
			throw new ObjetoNaoEncontratoException("Não foi encontrado nenhuma cidade.");
		}
		return cidades;
		
	}
}
