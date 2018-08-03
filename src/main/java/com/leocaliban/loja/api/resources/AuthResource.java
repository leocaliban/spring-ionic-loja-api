package com.leocaliban.loja.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.loja.api.dto.EmailDTO;
import com.leocaliban.loja.api.security.JWTUtil;
import com.leocaliban.loja.api.security.UserSpringSecurity;
import com.leocaliban.loja.api.services.AuthService;
import com.leocaliban.loja.api.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity usuarioLogado = UserService.usuarioAutenticado();
		String token = jwtUtil.generateToken(usuarioLogado.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objetoDTO) {
		service.enviarNovaSenha(objetoDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
