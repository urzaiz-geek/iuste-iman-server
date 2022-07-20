package com.urzaizcoding.iusteimanserver.exception;

public class TokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public TokenException() {
		super("An error occured while manipulating tokens");
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
