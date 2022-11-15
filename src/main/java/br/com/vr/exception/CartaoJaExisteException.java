package br.com.vr.exception;

public class CartaoJaExisteException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public CartaoJaExisteException(String mensagem) {
		super(mensagem);
	}
}
