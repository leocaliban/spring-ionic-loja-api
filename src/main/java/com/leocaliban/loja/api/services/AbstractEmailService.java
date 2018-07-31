package com.leocaliban.loja.api.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leocaliban.loja.api.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	/**
	 * método responsável por retornar o HTML preenchido com os dados de um pedido, a partir do template Thymeleaf
	 * @param objeto Pedido
	 * @return String contendo o resultado do modelo especificado com o contexto fornecido
	 */
	protected String htmlFromTemplatePedido(Pedido objeto) {
		Context context = new Context();
		context.setVariable("pedido", objeto);// *pedido apelido usado no tm "${pedido.id}" *obj pedido que o tm acessa
		
		return templateEngine.process("email/confirmacaoPedido", context);// caminho do html e o contexto montado
	}
	
	public void enviarConfirmacaoDoPedidoHtml(Pedido objeto) {
		MimeMessage mimeMessage;
		try {
			mimeMessage = prepararMimeMessageParaPedido(objeto);
			enviarEmailHtml(mimeMessage);
		} 
		catch (MessagingException e) {
			enviarConfirmacaoDoPedido(objeto);
		}
	}

	protected MimeMessage prepararMimeMessageParaPedido(Pedido objeto) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);
		mimeHelper.setTo(objeto.getCliente().getEmail());
		mimeHelper.setFrom(sender);
		mimeHelper.setSubject("Pedido confirmado! Código: " + objeto.getId());
		mimeHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeHelper.setText(htmlFromTemplatePedido(objeto), true);
		
		return mimeMessage;
	}

}
