package com.leocaliban.loja.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.domain.Estado;
import com.leocaliban.loja.api.dto.CidadeDTO;
import com.leocaliban.loja.api.dto.EstadoDTO;
import com.leocaliban.loja.api.services.CidadeService;
import com.leocaliban.loja.api.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> buscarEstados() {
		List<Estado> lista = service.buscarTodos();
		List<EstadoDTO> listaDTO = lista.stream().map(objeto -> new EstadoDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
	@RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> buscarCidades(@PathVariable Integer estadoId){
		List<Cidade> lista = cidadeService.buscarPorEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(objeto -> new CidadeDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
}
