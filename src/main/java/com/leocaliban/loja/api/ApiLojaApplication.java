package com.leocaliban.loja.api;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.domain.Endereco;
import com.leocaliban.loja.api.domain.Estado;
import com.leocaliban.loja.api.domain.Pagamento;
import com.leocaliban.loja.api.domain.PagamentoBoleto;
import com.leocaliban.loja.api.domain.PagamentoCartao;
import com.leocaliban.loja.api.domain.Pedido;
import com.leocaliban.loja.api.domain.Produto;
import com.leocaliban.loja.api.domain.enums.StatusPagamento;
import com.leocaliban.loja.api.domain.enums.TipoCliente;
import com.leocaliban.loja.api.repositories.CategoriaRepository;
import com.leocaliban.loja.api.repositories.CidadeRepository;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.repositories.EnderecoRepository;
import com.leocaliban.loja.api.repositories.EstadoRepository;
import com.leocaliban.loja.api.repositories.PagamentoRepository;
import com.leocaliban.loja.api.repositories.PedidoRepository;
import com.leocaliban.loja.api.repositories.ProdutoRepository;

@SpringBootApplication
public class ApiLojaApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiLojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Cozinha");
		
		Produto produto1 = new Produto(null, "Roteador", 100.00);
		Produto produto2 = new Produto(null, "Monitor", 900.00);
		Produto produto3 = new Produto(null, "Liquidificador", 70.00);
		Produto produto4 = new Produto(null, "Panela Elétrica", 300.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2));
		categoria2.getProdutos().addAll(Arrays.asList(produto3, produto4));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1));
		produto3.getCategorias().addAll(Arrays.asList(categoria2));
		produto4.getCategorias().addAll(Arrays.asList(categoria2));

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4));
		
		Estado estado1 = new Estado(null, "Paraíba");
		Estado estado2 = new Estado(null, "São Paulo");
		Estado estado3 = new Estado(null, "Pernambuco");
		
		Cidade cidade1 = new Cidade(null, "Patos", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Recife", estado3);
		Cidade cidade4 = new Cidade(null, "Monteiro", estado1);
		Cidade cidade5 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1, cidade4));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade5));
		estado3.getCidades().addAll(Arrays.asList(cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2, estado3));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4, cidade5));
		
		Cliente cliente1 = new Cliente(null, "Jack Bauer", "uct@gmail.com", "09565698785", TipoCliente.PESSOA_FISICA);
		
		cliente1.getTelefones().addAll(Arrays.asList("99165-7855", "98878-8577"));
		
		Endereco endereco1 = new Endereco(null, "New Link", "071", "Apartamento", "Centro", "54585400", cliente1, cidade2);
		Endereco endereco2 = new Endereco(null, "Rua Nova", "200", "Casa", "Centro", "54585400", cliente1, cidade1);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, formataData.parse("28/07/2017 08:30"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, formataData.parse("30/07/2017 10:30"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoCartao(null, StatusPagamento.QUITADO, pedido1, 10);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoBoleto(null, StatusPagamento.PENDENTE, pedido2, formataData.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
	}
}
