package br.com.mesttra.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

}
