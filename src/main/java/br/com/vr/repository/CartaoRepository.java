package br.com.vr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.entitys.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>{

}
