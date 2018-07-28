package com.leocaliban.loja.api;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.domain.Estado;
import com.leocaliban.loja.api.domain.Produto;
import com.leocaliban.loja.api.repositories.CategoriaRepository;
import com.leocaliban.loja.api.repositories.CidadeRepository;
import com.leocaliban.loja.api.repositories.EstadoRepository;
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
		
		
	}
}
