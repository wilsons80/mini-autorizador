package br.com.vr.controller;

import java.math.BigDecimal;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.exception.CartaoJaExisteException;
import br.com.vr.exception.CartaoNaoExisteException;
import br.com.vr.service.CartaoService;
import br.com.vr.vo.CartaoVO;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	
	@Transactional
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartaoVO> criarCartao(@RequestBody @Valid CartaoVO cartaoVO) {
		try {
			CartaoVO novo = cartaoService.criar(cartaoVO);
			return new ResponseEntity<>(novo, HttpStatus.CREATED);
		} catch (CartaoJaExisteException e) {
			return new ResponseEntity<CartaoVO>(cartaoVO, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@Transactional
	@GetMapping("/{numeroCartao}")
	public ResponseEntity<BigDecimal> buscarSaldoCartao(@PathVariable(name = "numeroCartao") @Valid String numeroCartao) {
		try {
			BigDecimal saldoCartao = cartaoService.getSaldoCartao(numeroCartao);
			return new ResponseEntity<>(saldoCartao, HttpStatus.OK);
		} catch (CartaoNaoExisteException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
}
