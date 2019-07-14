package br.com.restWithSpringBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceOperationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ResourceOperationException(String message) {
		super(message);
	}
	
}
