package br.com.restWithSpringBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnsuporteMathOperationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public UnsuporteMathOperationException(String message) {
		super(message);
	}
	
}
