package com.leocaliban.loja.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leocaliban.loja.api.dto.CredenciaisDTO;

/**
 * Classe de filtragem da autenticação (boilerplate)
 * Intercepta as requisições de login 
 * @author Leocaliban
 *
 * 31 de jul de 2018
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	//Inject via construtor
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());//classe interna
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Método que intercepta a requisição de login com dados passados no POST 
	 * e verifica se as credenciais de acesso são válidas
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			// Pega os dados da requisição e converte para a classe CredenciaisDTO
			CredenciaisDTO credenciais = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			
			//token do spring
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken(
							credenciais.getEmail(), 
							credenciais.getSenha(), 
							new ArrayList<>());
			
			//aqui o corre a verificação para saber se os dados são válidos
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Quando a autenticação ocorre com sucesso, este método gera um token de acesso
	 * e adiciona ele na resposta da requisição [Header]
	 * aqui o [Authentication authResult] é o objeto vindo do método anterior attemptAuthentication()
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		
		
		String email = ((UserSpringSecurity) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(email);
		response.addHeader("Authorization", "Bearer " + token);
	}
	
	/**
	 * ATUALIZAÇÃO: Spring versão 2 - correção no retorno de resposta de 403 para 401
	 * Classe interna que define o erro e o corpo de resposta quando o correr erro na autenticação.
	 * @author Leocaliban
	 *
	 * 31 de jul de 2018
	 */
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Acesso não autorizado.\", "
                + "\"message\": \"Email ou senha inválidos.\", "
                + "\"path\": \"/login\"}";
        }
	}
	
}
