package com.leocaliban.loja.api.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leocaliban.loja.api.domain.Categoria;
import com.leocaliban.loja.api.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//padrao de busca por nome do método substitui a query acima.
	//5.3.2. Query Creation
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
	public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, 
			@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
