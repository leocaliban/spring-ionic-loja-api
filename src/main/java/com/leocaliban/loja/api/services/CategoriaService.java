package com.leocaliban.loja.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.repositories.CategoriaRepository;
import com.leocaliban.loja.api.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscarPorId(Integer id) {
		//OPTIONAL - Container que encapsula o objeto buscado, evitando excessao nullpointer [JAVA 8]
		Optional<Categoria> objeto = repository.findById(id);
		//Se não achar o objeto retorna null.
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> buscarTodas() {
		return repository.findAll();
	}

	public Categoria salvar(Categoria objeto) {
		objeto.setId(null);
		return repository.save(objeto);
	}

	public Categoria editar(Categoria objeto) {
		//Na edição é preciso verificar se o objeto existe, verificando seu id.
		buscarPorId(objeto.getId());
		return repository.save(objeto);
	}

	public void deletar(Integer id) {
		buscarPorId(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir uma categoria que possui produtos.");
		}
		
	}

}
