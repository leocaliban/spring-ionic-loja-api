package com.leocaliban.loja.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.dto.CategoriaDTO;
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
	
	/**
	 * Busca as categorias com paginação
	 * @param page Integer que representa a página (0-1-2...)
	 * @param linesPerPage Integer que representa quantas linhas a página possui.
	 * @param orderBy String que representa por qual atributo a lista será ordenada.
	 * @param direction String que representa se a lista será Ascendente ou Descendente.
	 * @return Página
	 */
	public Page<Categoria> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Categoria salvar(Categoria objeto) {
		objeto.setId(null);
		return repository.save(objeto);
	}

	public Categoria editar(Categoria objeto) {
		Categoria novoObjeto = buscarPorId(objeto.getId());
		aplicarEdicao(novoObjeto, objeto);
		return repository.save(novoObjeto);
	}

	/**
	 * Método auxiliar para aplicar as edições no objeto.
	 * @param novoObjeto - objeto que será salvo contendo os dados complementares do objeto antigo.
	 * @param objeto - objeto recuperado do banco que irá tranferir seus dados para o novo objeto.
	 */
	private void aplicarEdicao(Categoria novoObjeto, Categoria objeto) {
		novoObjeto.setNome(objeto.getNome());
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
	
	public Categoria converterDTO(CategoriaDTO objetoDTO) {
		return new Categoria(objetoDTO.getId(), objetoDTO.getNome());
	}

}
