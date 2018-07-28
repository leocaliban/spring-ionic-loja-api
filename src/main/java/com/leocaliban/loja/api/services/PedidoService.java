package com.leocaliban.loja.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Pedido;
import com.leocaliban.loja.api.repositories.PedidoRepository;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido buscarPorId(Integer id) {
		Optional<Pedido> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
