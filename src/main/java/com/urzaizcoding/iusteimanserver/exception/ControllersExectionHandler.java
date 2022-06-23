package com.urzaizcoding.iusteimanserver.exception;

import java.util.Date;

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
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}
	
	
}
