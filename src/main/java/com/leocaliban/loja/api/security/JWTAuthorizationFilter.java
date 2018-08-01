package com.leocaliban.loja.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JWTUtil jwtUtil;
	
	//usado para analizar se o token é válido através da busca de usuário
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain chain) throws IOException, ServletException {
		
		// recupera o bearer no header da requisição
		String header = request.getHeader("Authorization");
		// o header recuperado não deve ser nulo e deve começar com 'Bearer '
		if(header != null && header.startsWith("Bearer ")) {//substring retira as letras Bearer_ para sobrar o token
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if(auth != null) {//liberar a autorização
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.tokenValido(token)) {
			String email = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(email);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
