package br.com.vr.exception;

public class SenhaInvalidaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException(String mensagem) {
		super(mensagem);
	}
}
