package com.urzaizcoding.iusteimanserver.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ErrorMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Integer statusCode;
	private final Date date;
	private final String message;
	private final String description;
	
	@Builder
	private ErrorMessage(Integer statusCode, Date date, String message, String description) {
		super();
		this.statusCode = statusCode;
		this.date = date;
		this.message = message;
		this.description = description;
	}
}
