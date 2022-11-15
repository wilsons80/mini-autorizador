package br.com.vr.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransacaoVO {
	
	@NotNull(message = "O número do cartão deve ser informado.")
	private String numeroCartao;
	
	@NotNull(message = "A senha deve ser informada.")
	private String senhaCartao;
	
	@NotNull(message = "O valor deve ser informado.")
	private BigDecimal valor;

	public TransacaoVO() {
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenhaCartao() {
		return senhaCartao;
	}

	public void setSenhaCartao(String senhaCartao) {
		this.senhaCartao = senhaCartao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
}
