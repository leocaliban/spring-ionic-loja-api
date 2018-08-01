package com.leocaliban.loja.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leocaliban.loja.api.services.S3Service;

@SpringBootApplication
public class ApiLojaApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiLojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.enviarArquivo("F:\\Imagens\\Facebook\\Sem título.png");
			
	}
}
