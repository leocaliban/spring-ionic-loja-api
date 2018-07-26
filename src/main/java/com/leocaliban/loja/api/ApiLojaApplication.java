package com.leocaliban.loja.api;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.repositories.CategoriaRepository;

@SpringBootApplication
public class ApiLojaApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiLojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Cozinha");
		
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		
	}
}
