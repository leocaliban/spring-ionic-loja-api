package com.leocaliban.loja.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leocaliban.loja.api.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT objeto FROM Cidade objeto WHERE objeto.estado.id = :estadoId ORDER BY objeto.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);

}
