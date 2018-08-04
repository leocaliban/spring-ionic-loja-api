package com.leocaliban.loja.api.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.leocaliban.loja.api.services.exceptions.ArquivoException;
import com.leocaliban.loja.api.services.exceptions.AutorizacaoException;
import com.leocaliban.loja.api.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

/**
 * Classe auxiliar que intercepta e controla exceções.
 * @author Leocaliban
 *
 * 27 de jul de 2018
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjetoNaoEncontratoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontratoException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), 
							"Recurso não encontrado.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(IntegridadeDeDadosException.class)
	public ResponseEntity<ErroPadrao> integridadeDeDadosViolada(IntegridadeDeDadosException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
							"Integridade de dados violada.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validador(MethodArgumentNotValidException exception, HttpServletRequest request){
		
		ErroDoValidador erro = new ErroDoValidador(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), 
								"Erro de validação.", exception.getMessage(), request.getRequestURI());
		
		/**
		 * Adicionando os erros encontrados na lista do erro do validador
		 */
		for(FieldError x : exception.getBindingResult().getFieldErrors()) {
			erro.adicionarErro(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
	}
	
	@ExceptionHandler(AutorizacaoException.class)
	public ResponseEntity<ErroPadrao> autorizacaoNegada(AutorizacaoException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), 
							"Acesso negado.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	@ExceptionHandler(ArquivoException.class)
	public ResponseEntity<ErroPadrao> arquivo(ArquivoException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
							"Erro de arquivo.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<ErroPadrao> amazonService(AmazonServiceException exception, HttpServletRequest request){
		
		HttpStatus codigo = HttpStatus.valueOf(exception.getErrorCode());
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), codigo.value(), 
							"Erro Amazon Service.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(codigo).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonClient(AmazonClientException exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
							"Erro Amazon Client.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception exception, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
							"Erro Amazon S3.", exception.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
}
