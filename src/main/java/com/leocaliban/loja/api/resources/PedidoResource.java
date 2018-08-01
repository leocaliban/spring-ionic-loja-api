package com.leocaliban.loja.api.resources;

import java.net.URI;

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

import com.leocaliban.loja.api.domain.Pedido;
import com.leocaliban.loja.api.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
		Pedido objeto = service.buscarPorId(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> buscarComPaginacao(
			@RequestParam(value = "page", defaultValue = "0") 
			Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "dataPedido")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC")
			String direction) {
		
		Page<Pedido> lista = service.buscarComPaginacao(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(lista);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Pedido objeto){
		objeto = service.salvar(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
