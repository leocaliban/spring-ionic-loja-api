package com.leocaliban.loja.api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.domain.ItemPedido;
import com.leocaliban.loja.api.domain.PagamentoBoleto;
import com.leocaliban.loja.api.domain.Pedido;
import com.leocaliban.loja.api.domain.enums.StatusPagamento;
import com.leocaliban.loja.api.repositories.ItemPedidoRepository;
import com.leocaliban.loja.api.repositories.PagamentoRepository;
import com.leocaliban.loja.api.repositories.PedidoRepository;
import com.leocaliban.loja.api.security.UserSpringSecurity;
import com.leocaliban.loja.api.services.exceptions.AutorizacaoException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido buscarPorId(Integer id) {
		Optional<Pedido> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Page<Pedido> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSpringSecurity usuarioLogado = UserService.usuarioAutenticado();
		if(usuarioLogado == null) {
			throw new AutorizacaoException("Acesso negado.");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.buscarPorId(usuarioLogado.getId());
		return repository.findByCliente(cliente, pageRequest);
	}
	
	@Transactional
	public Pedido salvar(Pedido objeto) {
		objeto.setId(null);
		objeto.setDataPedido(new Date());
		objeto.setCliente(clienteService.buscarPorId(objeto.getCliente().getId()));
		objeto.getPagamento().setStatus(StatusPagamento.PENDENTE);
		objeto.getPagamento().setPedido(objeto);
		if(objeto.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagamento = (PagamentoBoleto) objeto.getPagamento();
			boletoService.realizarPagamentoComBoleto(pagamento, objeto.getDataPedido());
		}
		objeto = repository.save(objeto);
		pagamentoRepository.save(objeto.getPagamento());
		
		for (ItemPedido ip : objeto.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscarPorId(ip.getProduto().getId()));
			ip.setValor(ip.getProduto().getValor());
			ip.setPedido(objeto);
		}
		itemPedidoRepository.saveAll(objeto.getItens());
		emailService.enviarConfirmacaoDoPedidoHtml(objeto);
		return objeto;
	}
}
