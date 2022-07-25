package com.urzaizcoding.iusteimanserver.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllersExectionHandler {
	
	static final Logger logger = LoggerFactory.getLogger(ControllersExectionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		ErrorMessage message = ErrorMessage.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.date(new Date())
				.description(request.getDescription(false))
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ErrorMessage> tokenErrorException(TokenException ex, WebRequest request){
		ErrorMessage message = ErrorMessage.builder()
				.statusCode(HttpStatus.FORBIDDEN.value())
				.date(new Date())
				.description(request.getDescription(false))
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> invalidDataInFields(MethodArgumentNotValidException ex, WebRequest request){
		
		String message = "Request Rejected due to Invalid Data : ";
		
		for(ObjectError error: ex.getAllErrors()) {
			message = String.format("%s %s : %s", message,((FieldError) error).getField(),error.getDefaultMessage());
		}
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.date(new Date())
				.message(message)
				.description(request.getDescription(false))
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalErrorException(Exception ex, WebRequest request){
		ErrorMessage message = ErrorMessage.builder()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.date(new Date())
				.description(request.getDescription(false))
				.message("And unexpected error occured on server please join administrators")
				.build();
		//TO-REMOVE
		logger.error("global exception",ex);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}
	
	
}
