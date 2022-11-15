package br.com.vr.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.build.CartaoMapper;
import br.com.vr.entitys.Cartao;
import br.com.vr.exception.CartaoJaExisteException;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.repository.CartaoRepository;
import br.com.vr.vo.CartaoVO;

@Service
public class CartaoService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private CartaoMapper cartaoMapper;
	
	public Optional<Cartao> getCartao(String numeroCartao) {
		return Optional.ofNullable(cartaoRepository.findByNumeroCartao(numeroCartao));		
	}

	public CartaoVO criar(CartaoVO cartaoVo) {
		Optional<Cartao> cartaoOpt = Optional.ofNullable(cartaoRepository.findByNumeroCartao(cartaoVo.getNumeroCartao()));
		cartaoOpt.ifPresent((cartao) -> {throw new CartaoJaExisteException(String.format("O cartão %s já está cadastrado.", cartaoVo.getNumeroCartao()));});
				
		Cartao entity = cartaoMapper.toEntity(cartaoVo);
		entity.setSaldo(new BigDecimal(500.00));
		entity = cartaoRepository.save(entity);
		return cartaoMapper.toModel(entity);
	}
	
	
	public BigDecimal getSaldoCartao(String numeroCartao) {
		Optional<Cartao> cartaoOpt = getCartao(numeroCartao);
		Cartao cartao = cartaoOpt.orElseThrow(() -> new CartaoNaoExisteException(String.format("O cartão %s não existe na base de dados.", numeroCartao)));
		return cartao.getSaldo();
	}
}
