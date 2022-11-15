package br.com.vr.vo;

import javax.validation.constraints.NotNull;

public class CartaoVO {
	
	@NotNull(message = "Informe o número do cartão.")
	private String numeroCartao;
	
	@NotNull(message = "Informe a senha do cartão.")
	private String senha;

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
}
