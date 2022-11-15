package br.com.vr.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.core.StatusTransacaoEnum;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.exception.SaldoInsuficienteException;
import br.com.vr.exception.SenhaInvalidaException;
import br.com.vr.service.TransacaoService;
import br.com.vr.vo.TransacaoVO;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;
	
	@Transactional
	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransacaoEnum> criar(@RequestBody @Valid TransacaoVO transacaoVO) {
		try {
			transacaoService.realizarTransacao(transacaoVO);
			return new ResponseEntity<>(StatusTransacaoEnum.OK, HttpStatus.CREATED);
		} catch (SaldoInsuficienteException e) {
			return new ResponseEntity<StatusTransacaoEnum>(StatusTransacaoEnum.SALDO_INSUFICIENTE, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (SenhaInvalidaException e) {
			return new ResponseEntity<StatusTransacaoEnum>(StatusTransacaoEnum.SENHA_INVALIDA, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (CartaoNaoExisteException e) {
			return new ResponseEntity<StatusTransacaoEnum>(StatusTransacaoEnum.CARTAO_INEXISTENTE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
