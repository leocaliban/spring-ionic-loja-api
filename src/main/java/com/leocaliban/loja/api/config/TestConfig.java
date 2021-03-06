package com.leocaliban.loja.api.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leocaliban.loja.api.services.DBService;
import com.leocaliban.loja.api.services.EmailService;
import com.leocaliban.loja.api.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;

	@Bean
	public boolean inicializarBancoDeDados() throws ParseException {
		dbService.inicializarBancoDeDadosParaTeste();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
