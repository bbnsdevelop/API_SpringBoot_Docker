package br.com.restWithSpringBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileStorageNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public FileStorageNotFoundException(String message) {
		super(message);
	}
	
	public FileStorageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
