package br.com.mesttra.project.exception;

import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mesttra.project.response.MessageResponse;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> BusinessException(BusinessException ex) {
		MessageResponse response = new MessageResponse("422", ex.getMessage());
		log.error("UNPROCESSABLE_ENTITY: {}", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> getMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		MessageResponse response = new MessageResponse("404", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		log.error("Bad request: {}", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<?> getUnexpectedTypeException(UnexpectedTypeException ex) {
		MessageResponse response = new MessageResponse("404", ex.getMessage());
		log.error("Bad request: {}", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex) {
		MessageResponse response = new MessageResponse("500",ex.getMessage());
		log.error("Internal server error: {}", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
