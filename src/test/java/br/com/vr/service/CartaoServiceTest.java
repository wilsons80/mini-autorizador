package br.com.vr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.vr.entity.Cartao;
import br.com.vr.entity.repository.CartaoRepository;
import br.com.vr.exception.CartaoJaExisteException;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.mapper.CartaoMapper;
import br.com.vr.vo.CartaoVO;

@ExtendWith(SpringExtension.class)
public class CartaoServiceTest {

	@InjectMocks
	private CartaoService cartaoService;
	
	@Mock
	private CartaoRepository cartaoRepository;
	@Mock
	private CartaoMapper cartaoMapper;
	
	@Test
	public void test_ConsultarSaldoCartao() {
		String numeroCartao = "6549873025634502";		
		
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao(numeroCartao);
		cartao.setSaldo(BigDecimal.valueOf(560.10));
		
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(cartao);
		BigDecimal saldoCartao = cartaoService.getSaldoCartao(numeroCartao);
		
		assertEquals(saldoCartao, cartao.getSaldo());		
	}
	
	@Test
	public void test_ConsultarSaldoCartaoComCartaoInexistente() {
		String numeroCartao = "1119873025634511";		
		
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(null);
		assertThrows(CartaoNaoExisteException.class, () -> {
			cartaoService.getSaldoCartao(numeroCartao);
		});
	}
	
	
	
	@Test
	public void test_CriarCartaoJaCadastradoNoSistema() {
		String numeroCartao = "6549873025634502";		
		Cartao cartao = mock(Cartao.class);
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(cartao);
		assertThrows(CartaoJaExisteException.class, () -> {
			CartaoVO cartaoVO = new CartaoVO();
			cartaoVO.setNumeroCartao(numeroCartao);			
			cartaoService.criar(cartaoVO);
		});
	}
	
	@Test
	public void test_CriarCartaoComSucesso() {
		String numeroCartao = "6549873025634503";		
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(null);
		
		CartaoVO cartaoVO = new CartaoVO();
		cartaoVO.setNumeroCartao(numeroCartao);
		cartaoVO.setSaldo(BigDecimal.valueOf(500));
		
		Cartao entity = new Cartao();
		entity.setNumeroCartao(numeroCartao);
		
		when(cartaoMapper.toEntity(cartaoVO)).thenReturn(entity);
		when(cartaoRepository.save(entity)).thenReturn(entity);
		when(cartaoMapper.toModel(entity)).thenReturn(cartaoVO);
		
		CartaoVO cartaoCriado = cartaoService.criar(cartaoVO);
		
		assertEquals(cartaoCriado, cartaoVO);
		assertEquals(cartaoCriado.getSaldo(), BigDecimal.valueOf(500));
	}
}
