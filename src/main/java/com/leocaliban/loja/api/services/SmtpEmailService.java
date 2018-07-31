package com.leocaliban.loja.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	

	@Override
	public void enviarEmail(SimpleMailMessage mensagem) {
		LOG.info("Enviando e-mail...");
		mailSender.send(mensagem);
		LOG.info("E-mail enviado.");
		
	}

}
