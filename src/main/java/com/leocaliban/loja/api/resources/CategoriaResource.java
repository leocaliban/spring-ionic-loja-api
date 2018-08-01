package com.leocaliban.loja.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.dto.CategoriaDTO;
import com.leocaliban.loja.api.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
		Categoria objeto = service.buscarPorId(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodas() {
		List<Categoria> lista = service.buscarTodas();
		
		//Construir uma listaDTO a partir da lista de Categorias
		//Stream percorre a lista, Map efetua uma operação a cada elemento percorrido na lista
		//Collect transforma o stream em lista
		List<CategoriaDTO> listaDTO = lista.stream().map(elemento -> new CategoriaDTO(elemento)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> buscarComPaginacao(
			@RequestParam(value = "page", defaultValue = "0") 
			Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")
			String direction) {
		Page<Categoria> lista = service.buscarComPaginacao(page, linesPerPage, orderBy, direction);
		
		//Page não precisa do stream e collect [JAVA8]
		Page<CategoriaDTO> listaDTO = lista.map(elemento -> new CategoriaDTO(elemento));
		return ResponseEntity.ok().body(listaDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")//define quem tem autorização nessa requisição
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody CategoriaDTO objetoDTO){
		Categoria objeto = service.converterDTO(objetoDTO);
		objeto = service.salvar(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> editar(@Valid @RequestBody CategoriaDTO objetoDTO, @PathVariable Integer id){
		Categoria objeto = service.converterDTO(objetoDTO);
		objeto.setId(id);
		objeto = service.editar(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
