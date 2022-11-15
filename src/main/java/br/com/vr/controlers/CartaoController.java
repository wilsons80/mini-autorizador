package br.com.vr.controlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.service.CartaoService;
import br.com.vr.vo.CartaoVO;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	
	@Transactional
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CartaoVO criar(@RequestBody @Valid CartaoVO cartaoVo) {
		CartaoVO novo = cartaoService.criar(cartaoVo);
		return novo;
	}
}
