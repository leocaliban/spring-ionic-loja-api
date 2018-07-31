package com.leocaliban.loja.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * Classe {@link MockEmailService} implementa o envio de email para o log via Mock.
 * @author Leocaliban
 *
 * 31 de jul de 2018
 */
public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void enviarEmail(SimpleMailMessage mensagem) {
		LOG.info("Simulando envio de e-mail...");
		LOG.info(mensagem.toString());
		LOG.info("E-mail enviado.");
		
	}

}
