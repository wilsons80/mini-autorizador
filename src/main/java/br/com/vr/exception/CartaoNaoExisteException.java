package br.com.vr.exception;

public class CartaoNaoExisteException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public CartaoNaoExisteException(String mensagem) {
		super(mensagem);
	}
}
