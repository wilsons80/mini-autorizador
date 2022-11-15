package br.com.vr.service;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.vr.build.CartaoMapper;
import br.com.vr.entitys.Cartao;
import br.com.vr.repository.CartaoRepository;
import br.com.vr.vo.CartaoVO;

@Service
public class CartaoService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private CartaoMapper cartaoMapper;

	public CartaoVO criar(@RequestBody @Valid CartaoVO cartaoVo) {
		Cartao entity = cartaoMapper.toEntity(cartaoVo);
		entity.setSaldo(new BigDecimal(500.00));
		
		entity = cartaoRepository.save(entity);
		return cartaoMapper.toModel(entity);
	}
}
