package com.urzaizcoding.iusteimanserver.exception;

public class InvalidTokenException extends TokenException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super("Client provided an invalid Token");
		// TODO Auto-generated constructor stub
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
