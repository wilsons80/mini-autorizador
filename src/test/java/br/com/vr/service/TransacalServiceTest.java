package br.com.vr.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.vr.entity.Cartao;
import br.com.vr.entity.repository.CartaoRepository;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.exception.SaldoInsuficienteException;
import br.com.vr.exception.SenhaInvalidaException;
import br.com.vr.vo.TransacaoVO;

@ExtendWith(SpringExtension.class)
public class TransacalServiceTest {

	@InjectMocks
	private TransacaoService transacaoService;
	
	@Mock
	private CartaoRepository cartaoRepository;

	
	
	@Test
	public void test_RealizarTransacaoComSucesso() {
		String numeroCartao = "6549873025634502";
		
		TransacaoVO transacaoVO = new TransacaoVO();
		transacaoVO.setNumeroCartao(numeroCartao);
		transacaoVO.setSenhaCartao("1234");
		transacaoVO.setValor(BigDecimal.valueOf(100));
		
		Cartao cartao = getCartao();
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(cartao);
		
		transacaoService.realizarTransacao(transacaoVO);
		verify(cartaoRepository).save(Mockito.any());
	}
	
	
	@Test
	public void test_RealizarTransacaoComCartaoInexistente() {
		String numeroCartao = "1119873025634111";
		
		TransacaoVO transacaoVO = new TransacaoVO();
		transacaoVO.setNumeroCartao(numeroCartao);
		transacaoVO.setSenhaCartao("1234");
		transacaoVO.setValor(BigDecimal.valueOf(100));
		
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(null);
		
		assertThrows(CartaoNaoExisteException.class, () -> {
			transacaoService.realizarTransacao(transacaoVO);
		});

	}
	
	@Test
	public void test_RealizarTransacaoComSaldoInsuficiente() {
		String numeroCartao = "1119873025634111";
		
		TransacaoVO transacaoVO = new TransacaoVO();
		transacaoVO.setNumeroCartao(numeroCartao);
		transacaoVO.setSenhaCartao("1234");
		transacaoVO.setValor(BigDecimal.valueOf(890));
		
		Cartao cartao = getCartao();
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(cartao);
		
		assertThrows(SaldoInsuficienteException.class, () -> {
			transacaoService.realizarTransacao(transacaoVO);
		});

	}
	
	@Test
	public void test_RealizarTransacaoComSenhaInvalida() {
		String numeroCartao = "1119873025634111";
		
		TransacaoVO transacaoVO = new TransacaoVO();
		transacaoVO.setNumeroCartao(numeroCartao);
		transacaoVO.setSenhaCartao("8989");
		transacaoVO.setValor(BigDecimal.valueOf(190));
		
		Cartao cartao = getCartao();
		when(cartaoRepository.findByNumeroCartao(numeroCartao)).thenReturn(cartao);
		
		assertThrows(SenhaInvalidaException.class, () -> {
			transacaoService.realizarTransacao(transacaoVO);
		});

	}
	
	
	
	private Cartao getCartao() {
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634502");
		cartao.setSenha("1234");
		cartao.setSaldo(BigDecimal.valueOf(500));
		return cartao;
	}
	
}
