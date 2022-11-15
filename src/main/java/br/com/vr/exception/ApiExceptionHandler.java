package br.com.vr.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CartaoJaExisteException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(CartaoJaExisteException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;		
		String mensagem = ex.getMessage();		
		Error error = new Error(status.value(), OffsetDateTime.now(), mensagem);
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {	    
	    String mensagem = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";  
	    Error error = new Error(status.value(), OffsetDateTime.now(), mensagem);
	    return handleExceptionInternal(ex, error, headers, status, request);
	}
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagem = ex.getMessage();		
		Error error = new Error(status.value(), OffsetDateTime.now(), mensagem);		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	

}