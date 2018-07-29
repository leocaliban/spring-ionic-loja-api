package com.leocaliban.loja.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.dto.ClienteDTO;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscarPorId(Integer id) {
		//OPTIONAL - Container que encapsula o objeto buscado, evitando excessao nullpointer [JAVA 8]
		Optional<Cliente> objeto = repository.findById(id);
		//Se não achar o objeto retorna null.
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> buscarTodos() {
		return repository.findAll();
	}
	
	/**
	 * Busca os clientes com paginação
	 * @param page Integer que representa a página (0-1-2...)
	 * @param linesPerPage Integer que representa quantas linhas a página possui.
	 * @param orderBy String que representa por qual atributo a lista será ordenada.
	 * @param direction String que representa se a lista será Ascendente ou Descendente.
	 * @return Página
	 */
	public Page<Cliente> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	//TODO: Salvar

	public Cliente editar(Cliente objeto) {
		Cliente novoObjeto = buscarPorId(objeto.getId());
		aplicarEdicao(novoObjeto, objeto);
		return repository.save(novoObjeto);
	}

	/**
	 * Método auxiliar para aplicar as edições no objeto.
	 * @param novoObjeto - objeto que será salvo contendo os dados complementares do objeto antigo.
	 * @param objeto - objeto recuperado do banco que irá tranferir seus dados para o novo objeto.
	 */
	private void aplicarEdicao(Cliente novoObjeto, Cliente objeto) {
		novoObjeto.setNome(objeto.getNome());
		novoObjeto.setEmail(objeto.getEmail());
	}

	public void deletar(Integer id) {
		buscarPorId(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir um cliente que possui pedidos.");
		}
	}
	
	public Cliente converterDTO(ClienteDTO objetoDTO) {
		return new Cliente(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getEmail(), null, null);
	}
}
