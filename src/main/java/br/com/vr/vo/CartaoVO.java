package br.com.vr.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CartaoVO {
	
	@NotNull(message = "Informe o número do cartão.")
	private String numeroCartao;
	
	@NotNull(message = "Informe a senha do cartão.")
	private String senha;
	
	@JsonIgnore(value = true)
	private BigDecimal saldo;

	public CartaoVO() {
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	
}
