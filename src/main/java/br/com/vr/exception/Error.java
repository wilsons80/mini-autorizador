package br.com.vr.exception;

import java.time.OffsetDateTime;

public class Error {

	private Integer status;
	private OffsetDateTime timestamp;
	private String mensagem;
	
	public Error(Integer status, OffsetDateTime timestamp, String mensagem) {
		this.status = status;
		this.timestamp = timestamp;
		this.mensagem = mensagem;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	
	
}
