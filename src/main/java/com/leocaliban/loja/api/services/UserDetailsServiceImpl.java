package com.leocaliban.loja.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.security.UserSpringSecurity;

/**
 * Classe que permite a busca pelo nome do usuário (email no nosso problema)
 * @author Leocaliban
 *
 * 31 de jul de 2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository reposiory;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = reposiory.findByEmail(email);
		if(cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
