package com.leocaliban.loja.api.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leocaliban.loja.api.services.DBService;
import com.leocaliban.loja.api.services.EmailService;
import com.leocaliban.loja.api.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Autowired
	private DBService dbService;
	
	// recupera o valor de application-dev.properties
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	/**
	 * Só deve inicializar o banco se a estratégia de criação não for create no dev.properties
	 */
	@Bean
	public boolean inicializarBancoDeDados() throws ParseException {
		if(!"create".equals(strategy)) {
			return false;
		}
		
		dbService.inicializarBancoDeDadosParaTeste();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
