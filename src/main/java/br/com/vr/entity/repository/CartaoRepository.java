package br.com.vr.entity.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vr.entity.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, BigDecimal>{

	@Query("from Cartao c where c.numeroCartao = :numeroCartao")
	public Cartao findByNumeroCartao(String numeroCartao);
	
}
