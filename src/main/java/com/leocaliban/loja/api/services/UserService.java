package com.leocaliban.loja.api.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.leocaliban.loja.api.security.UserSpringSecurity;

public class UserService {

	/**
	 * Método que obtém o usuário que está logado
	 * @return UserSpringSecurity
	 */
	public static UserSpringSecurity usuarioAutenticado() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
