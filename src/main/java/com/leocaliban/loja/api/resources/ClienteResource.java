package com.leocaliban.loja.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.dto.ClienteDTO;
import com.leocaliban.loja.api.dto.ClienteNovoDTO;
import com.leocaliban.loja.api.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
		Cliente objeto = service.buscarPorId(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> lista = service.buscarTodos();

		List<ClienteDTO> listaDTO = lista.stream().map(elemento -> new ClienteDTO(elemento)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscarComPaginacao(
			@RequestParam(value = "page", defaultValue = "0") 
			Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")
			String direction) {
		Page<Cliente> lista = service.buscarComPaginacao(page, linesPerPage, orderBy, direction);
		
		Page<ClienteDTO> listaDTO = lista.map(elemento -> new ClienteDTO(elemento));
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody ClienteNovoDTO objetoDTO){
		Cliente objeto = service.converterDTO(objetoDTO);
		objeto = service.salvar(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> editar(@Valid @RequestBody ClienteDTO objetoDTO, @PathVariable Integer id){
		Cliente objeto = service.converterDTO(objetoDTO);
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
