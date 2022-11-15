package br.com.vr.exception;

public class SaldoInsuficienteException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(String mensagem) {
		super(mensagem);
	}
}
