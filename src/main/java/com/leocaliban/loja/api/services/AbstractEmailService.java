package com.leocaliban.loja.api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.leocaliban.loja.api.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void enviarConfirmacaoDoPedido(Pedido objeto) {
		SimpleMailMessage simpleMailMessage = prepararSimpleMailMessageParaPedido(objeto);
		enviarEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepararSimpleMailMessageParaPedido(Pedido objeto) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// email do destinatario
		simpleMailMessage.setTo(objeto.getCliente().getEmail());
		//email remetente recuperado de application
		simpleMailMessage.setFrom(sender);
		//assunto do email
		simpleMailMessage.setSubject("Pedido confirmado! Código: " + objeto.getId());
		// data do email
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		simpleMailMessage.setText(objeto.toString());
		return simpleMailMessage;
	}

}
