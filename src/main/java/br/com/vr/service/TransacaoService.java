package br.com.vr.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.core.StatusTransacaoEnum;
import br.com.vr.entity.Cartao;
import br.com.vr.entity.repository.CartaoRepository;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.exception.SaldoInsuficienteException;
import br.com.vr.exception.SenhaInvalidaException;
import br.com.vr.vo.TransacaoVO;

@Service
public class TransacaoService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	public void realizarTransacao(TransacaoVO transacaoVO) {
		Optional<Cartao> cartaoOpt = Optional.ofNullable(cartaoRepository.findByNumeroCartao(transacaoVO.getNumeroCartao()));
		Cartao cartao = cartaoOpt.orElseThrow(() -> new CartaoNaoExisteException(StatusTransacaoEnum.CARTAO_INEXISTENTE.toString()));
		
		Stream.of(cartao).filter(c -> c.getSaldo().doubleValue() < transacaoVO.getValor().doubleValue())
		                 .findAny().ifPresent((c) -> { throw new SaldoInsuficienteException(StatusTransacaoEnum.SALDO_INSUFICIENTE.toString());});

		Stream.of(cartao).filter(c -> !c.getSenha().equals(transacaoVO.getSenhaCartao()))
                         .findAny().ifPresent((c) -> { throw new SenhaInvalidaException(StatusTransacaoEnum.SENHA_INVALIDA.toString());});
		
		cartao.setSaldo(cartao.getSaldo().subtract(transacaoVO.getValor()));
		cartaoRepository.save(cartao);
	}
	
	
}
