package com.leocaliban.loja.api.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expirationTime;
	
	/**
	 * Gera o token para o usuário
	 * @param email de login 
	 * @return token
	 */
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)//usuario
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			String username = claims.getSubject();
			Date dataExpiracao = claims.getExpiration();
			Date dataAtual = new Date(System.currentTimeMillis());
			if( username != null && dataExpiracao != null && dataAtual.before(dataExpiracao)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();	
		}
		catch(Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
