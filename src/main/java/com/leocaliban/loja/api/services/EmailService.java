package com.leocaliban.loja.api.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.leocaliban.loja.api.domain.Pedido;

/**
 * Interface {@link EmailService} que define quais operações o serviço de email deve oferecer.
 * @author Leocaliban
 *
 * 31 de jul de 2018
 */
public interface EmailService {

	/**
	 * Envia a confirmação de pedido.
	 * @param objeto Pedido
	 */
	public void enviarConfirmacaoDoPedido(Pedido objeto);
	
	/**
	 * Envia o email.
	 * @param mensagem mensagem
	 */
	public void enviarEmail(SimpleMailMessage mensagem);
	
	public void enviarConfirmacaoDoPedidoHtml(Pedido objeto);
	
	public void enviarEmailHtml(MimeMessage mensagem);
	
	
}
