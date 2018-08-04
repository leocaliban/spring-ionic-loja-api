package com.leocaliban.loja.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.domain.Endereco;
import com.leocaliban.loja.api.domain.Estado;
import com.leocaliban.loja.api.domain.ItemPedido;
import com.leocaliban.loja.api.domain.Pagamento;
import com.leocaliban.loja.api.domain.PagamentoBoleto;
import com.leocaliban.loja.api.domain.PagamentoCartao;
import com.leocaliban.loja.api.domain.Pedido;
import com.leocaliban.loja.api.domain.Produto;
import com.leocaliban.loja.api.domain.enums.PerfilUsuario;
import com.leocaliban.loja.api.domain.enums.StatusPagamento;
import com.leocaliban.loja.api.domain.enums.TipoCliente;
import com.leocaliban.loja.api.repositories.CategoriaRepository;
import com.leocaliban.loja.api.repositories.CidadeRepository;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.repositories.EnderecoRepository;
import com.leocaliban.loja.api.repositories.EstadoRepository;
import com.leocaliban.loja.api.repositories.ItemPedidoRepository;
import com.leocaliban.loja.api.repositories.PagamentoRepository;
import com.leocaliban.loja.api.repositories.PedidoRepository;
import com.leocaliban.loja.api.repositories.ProdutoRepository;

@Service
public class DBService {
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
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void inicializarBancoDeDadosParaTeste() throws ParseException {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Cozinha");
		Categoria categoria3 = new Categoria(null, "Alimentos");
		
		Produto produto1 = new Produto(null, "Roteador", 100.00);
		Produto produto2 = new Produto(null, "Monitor", 900.00);
		Produto produto3 = new Produto(null, "Liquidificador", 70.00);
		Produto produto4 = new Produto(null, "Panela Elétrica", 300.00);
		
		Produto produto5 = new Produto(null, "Leite Desnatado - Molico", 5.99);
		Produto produto6 = new Produto(null, "Salgadinho - Cheetos", 2.45);
		Produto produto7 = new Produto(null, "Batata Frita - Lays", 6.25);
		Produto produto8 = new Produto(null, "Batata Frita - Ruffles", 5.00);
		Produto produto9 = new Produto(null, "Batata Palha - YOKI", 5.49);
		Produto produto10 = new Produto(null, "Sopão - Vono", 1.69);
		Produto produto11 = new Produto(null, "Óleo de Milho - LIZA", 5.29);
		Produto produto12 = new Produto(null, "Adoçante Líquido - ZERO CAL ", 5.29);
		Produto produto13 = new Produto(null, "Macarrão Instantâneo - NISSIN", 1.29);
		Produto produto14 = new Produto(null, "Risoto - Tio João", 5.15);
		Produto produto15 = new Produto(null, "Sopa de Galinha - MAGGI", 3.75);
		Produto produto16 = new Produto(null, "Macarrão Integral - Taeq", 7.13);
		Produto produto17 = new Produto(null, "Arroz Integral - Tio João", 11.90);
		Produto produto18 = new Produto(null, "Sal - Cisne", 1.99);
		Produto produto19 = new Produto(null, "CUP NOODLES - Nissin", 3.39);
		Produto produto20 = new Produto(null, "Vinagre - Kenko", 4.49);
		Produto produto21 = new Produto(null, "Óleo de Girassol - LIZA", 5.29);
		Produto produto22 = new Produto(null, "Feijão Branco - QUALITÁ", 4.99);
		Produto produto23 = new Produto(null, "Açúcar Demerara - GUARANI", 2.75);
		Produto produto24 = new Produto(null, "Creme de Queijo - VONO", 3.99);
		Produto produto25 = new Produto(null, "Sopa Americana", 16.90);
		Produto produto26 = new Produto(null, "Arroz Integral", 3.73);
		Produto produto27 = new Produto(null, "Sopa de Batata", 1.69);
		Produto produto28 = new Produto(null, "Feijão Carioca", 2.69);
		Produto produto29 = new Produto(null, "Feijão Preto", 5.49);
		Produto produto30 = new Produto(null, "Chocolate Diamante Negro", 6.89);
		Produto produto31 = new Produto(null, "Bombom LACTA", 7.99);
		Produto produto32 = new Produto(null, "Chocolate Branco", 6.89);
		Produto produto33 = new Produto(null, "Chocolate Suíço ", 18.50);
		Produto produto34 = new Produto(null, "Kit Kat", 1.44);
		Produto produto35 = new Produto(null, "Chocolate Branco", 6.79);
		Produto produto36 = new Produto(null, "Baton Duo", 1.25);
		Produto produto37 = new Produto(null, "PRINGLES", 13.50);
		Produto produto38 = new Produto(null, "Butter Toffes", 17.50);
		Produto produto39 = new Produto(null, "Doritos", 3.12);
		Produto produto40 = new Produto(null, "Bis", 3.49);
		Produto produto41 = new Produto(null, "MENTOS Pote ", 11.75);
		Produto produto42 = new Produto(null, "Stiksy", 3.99);
		Produto produto43 = new Produto(null, "Doce de Leite", 23.50);
		Produto produto44 = new Produto(null, "HALLS", 2.15);
		Produto produto45 = new Produto(null, "Tubinhos", 3.99);
		Produto produto46 = new Produto(null, "Amendoim", 4.99);
		Produto produto47 = new Produto(null, "M&Ms", 8.99);
		Produto produto48 = new Produto(null, "Stax", 15.90);
		Produto produto49 = new Produto(null, "TRIDENT", 4.85);
		Produto produto50 = new Produto(null, "MONSTER", 7.99);
		Produto produto51 = new Produto(null, "RED BULL", 7.15);
		Produto produto52 = new Produto(null, "Água Tônica", 2.15);
		Produto produto53 = new Produto(null, "SUKITA", 2.69);
		

		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2));
		categoria2.getProdutos().addAll(Arrays.asList(produto3, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6, produto7, produto8, produto9, produto10, 
				produto11, produto12, produto13, produto14, produto15, produto16, produto17, produto18, produto19, 
				produto20, produto21, produto22, produto23, produto24, produto25, produto26, produto27, produto28, 
				produto29, produto30, produto31, produto32, produto33, produto34, produto35, produto36, produto37, 
				produto38, produto39, produto40, produto41, produto42, produto43, produto44, produto45, produto46, 
				produto47, produto48, produto49, produto50, produto51, produto52, produto53));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1));
		produto3.getCategorias().addAll(Arrays.asList(categoria2));
		produto4.getCategorias().addAll(Arrays.asList(categoria2));
		produto5.getCategorias().addAll(Arrays.asList(categoria3));
		produto6.getCategorias().addAll(Arrays.asList(categoria3));
		produto7.getCategorias().addAll(Arrays.asList(categoria3));
		produto8.getCategorias().addAll(Arrays.asList(categoria3));
		produto9.getCategorias().addAll(Arrays.asList(categoria3));
		produto10.getCategorias().addAll(Arrays.asList(categoria3));
		produto11.getCategorias().addAll(Arrays.asList(categoria3));
		produto12.getCategorias().addAll(Arrays.asList(categoria3));
		produto13.getCategorias().addAll(Arrays.asList(categoria3));
		produto14.getCategorias().addAll(Arrays.asList(categoria3));
		produto15.getCategorias().addAll(Arrays.asList(categoria3));
		produto16.getCategorias().addAll(Arrays.asList(categoria3));
		produto17.getCategorias().addAll(Arrays.asList(categoria3));
		produto18.getCategorias().addAll(Arrays.asList(categoria3));
		produto19.getCategorias().addAll(Arrays.asList(categoria3));
		produto20.getCategorias().addAll(Arrays.asList(categoria3));
		produto21.getCategorias().addAll(Arrays.asList(categoria3));
		produto22.getCategorias().addAll(Arrays.asList(categoria3));
		produto23.getCategorias().addAll(Arrays.asList(categoria3));
		produto24.getCategorias().addAll(Arrays.asList(categoria3));
		produto25.getCategorias().addAll(Arrays.asList(categoria3));
		produto26.getCategorias().addAll(Arrays.asList(categoria3));
		produto27.getCategorias().addAll(Arrays.asList(categoria3));
		produto28.getCategorias().addAll(Arrays.asList(categoria3));
		produto29.getCategorias().addAll(Arrays.asList(categoria3));
		produto30.getCategorias().addAll(Arrays.asList(categoria3));
		produto31.getCategorias().addAll(Arrays.asList(categoria3));
		produto32.getCategorias().addAll(Arrays.asList(categoria3));
		produto33.getCategorias().addAll(Arrays.asList(categoria3));
		produto34.getCategorias().addAll(Arrays.asList(categoria3));
		produto35.getCategorias().addAll(Arrays.asList(categoria3));
		produto36.getCategorias().addAll(Arrays.asList(categoria3));
		produto37.getCategorias().addAll(Arrays.asList(categoria3));
		produto38.getCategorias().addAll(Arrays.asList(categoria3));
		produto39.getCategorias().addAll(Arrays.asList(categoria3));
		produto40.getCategorias().addAll(Arrays.asList(categoria3));
		produto41.getCategorias().addAll(Arrays.asList(categoria3));
		produto42.getCategorias().addAll(Arrays.asList(categoria3));
		produto43.getCategorias().addAll(Arrays.asList(categoria3));
		produto44.getCategorias().addAll(Arrays.asList(categoria3));
		produto45.getCategorias().addAll(Arrays.asList(categoria3));
		produto46.getCategorias().addAll(Arrays.asList(categoria3));
		produto47.getCategorias().addAll(Arrays.asList(categoria3));
		produto48.getCategorias().addAll(Arrays.asList(categoria3));
		produto49.getCategorias().addAll(Arrays.asList(categoria3));
		produto50.getCategorias().addAll(Arrays.asList(categoria3));
		produto51.getCategorias().addAll(Arrays.asList(categoria3));
		produto52.getCategorias().addAll(Arrays.asList(categoria3));
		produto53.getCategorias().addAll(Arrays.asList(categoria3));
		


		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4));
		
		categoriaRepository.saveAll(Arrays.asList(categoria3));
		produtoRepository.saveAll(Arrays.asList(produto5, produto6, produto7, produto8, produto9, produto10, 
				produto11, produto12, produto13, produto14, produto15, produto16, produto17, produto18, produto19, 
				produto20, produto21, produto22, produto23, produto24, produto25, produto26, produto27, produto28, 
				produto29, produto30, produto31, produto32, produto33, produto34, produto35, produto36, produto37, 
				produto38, produto39, produto40, produto41, produto42, produto43, produto44, produto45, produto46, 
				produto47, produto48, produto49, produto50, produto51, produto52, produto53));
		
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
		
		Cliente cliente1 = new Cliente(null, "Jack Bauer", "leocaliban@gmail.com", "09565698785", 
							TipoCliente.PESSOA_FISICA, passwordEncoder.encode("123"));
		cliente1.getTelefones().addAll(Arrays.asList("99165-7855", "98878-8577"));
		
		Cliente cliente2 = new Cliente(null, "Nina Myers", "callmusic0@gmail.com", "00209128038", 
				TipoCliente.PESSOA_FISICA, passwordEncoder.encode("123"));
		cliente2.getTelefones().addAll(Arrays.asList("1874-7855"));
		cliente2.addPerfil(PerfilUsuario.ADMIN);
		
		Endereco endereco1 = new Endereco(null, "New Link", "071", "Apartamento", "Centro", "54585400", cliente1, cidade2);
		Endereco endereco2 = new Endereco(null, "Rua Nova", "200", "Casa", "Centro", "54585400", cliente1, cidade1);
		Endereco endereco3 = new Endereco(null, "Rua do Morro", "266", "Casa", "Centro", "54585400", cliente2, cidade3);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		cliente2.getEnderecos().addAll(Arrays.asList(endereco3));
		
		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
		
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
		
		ItemPedido item1 = new ItemPedido(pedido1, produto1, 0.0, 1, 100.0);
		ItemPedido item2 = new ItemPedido(pedido1, produto3, 0.0, 2, 140.0);
		ItemPedido item3 = new ItemPedido(pedido2, produto2, 100.0, 1, 800.0);
		
		pedido1.getItens().addAll(Arrays.asList(item1, item2));
		pedido2.getItens().addAll(Arrays.asList(item3));
		
		produto1.getItens().addAll(Arrays.asList(item1));
		produto2.getItens().addAll(Arrays.asList(item3));
		produto3.getItens().addAll(Arrays.asList(item2));
		
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
	}

}
