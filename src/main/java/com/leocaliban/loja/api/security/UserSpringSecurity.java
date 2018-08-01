package com.leocaliban.loja.api.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.leocaliban.loja.api.domain.enums.PerfilUsuario;

public class UserSpringSecurity implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority>authorities;
	
	public UserSpringSecurity() {
		
	}
	
	//Parâmetro [Set<PerfilUsuario> perfis] passado para facilitar o recebimento dos dados, 
	//posteriormente convertidos para o tipo exigido pelo spring
	public UserSpringSecurity(Integer id, String email, String senha,
			Set<PerfilUsuario> perfis ) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}



	public Integer getId() {
		return this.id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Verifica se o perfil de usuário é igual ao perfil passado como parametro
	 * @param perfil
	 * @return boolean
	 */
	public boolean hasRole(PerfilUsuario perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
