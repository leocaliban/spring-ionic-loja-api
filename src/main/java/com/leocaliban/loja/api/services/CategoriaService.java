package com.leocaliban.loja.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscarPorId(Integer id) {
		//OPTIONAL - Container que encapsula o objeto buscado, evitando excessao nullpointer [JAVA 8]
		Optional<Categoria> objeto = repository.findById(id);
		//Se não achar o objeto retorna null.
		return objeto.orElse(null);
	}
}
