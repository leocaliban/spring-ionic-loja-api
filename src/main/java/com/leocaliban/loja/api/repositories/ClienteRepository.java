package com.leocaliban.loja.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leocaliban.loja.api.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	//Indica que a busca não está envolvida com uma transação com o banco
	@Transactional(readOnly = true)
	public Cliente findByEmail(String email);
}
