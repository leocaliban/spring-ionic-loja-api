package com.leocaliban.loja.api.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leocaliban.loja.api.services.exceptions.AutorizacaoException;
import com.leocaliban.loja.api.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

/**
 * Classe auxiliar que intercepta e controla excessões
 * @author Leocaliban
 *
 * 27 de jul de 2018
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjetoNaoEncontratoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontratoException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(IntegridadeDeDadosException.class)
	public ResponseEntity<ErroPadrao> integridadeDeDadosViolada(IntegridadeDeDadosException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validador(MethodArgumentNotValidException exception, HttpServletRequest request){
		
		ErroDoValidador erro = new ErroDoValidador(HttpStatus.BAD_REQUEST.value(), "Erro de validação.", System.currentTimeMillis());
		
		/**
		 * Adicionando os erros encontrados na lista do erro do validador
		 */
		for(FieldError x : exception.getBindingResult().getFieldErrors()) {
			erro.adicionarErro(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AutorizacaoException.class)
	public ResponseEntity<ErroPadrao> autorizacaoNegada(AutorizacaoException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.FORBIDDEN.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}

}
