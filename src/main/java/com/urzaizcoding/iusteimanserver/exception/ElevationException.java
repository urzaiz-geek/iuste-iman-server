package com.urzaizcoding.iusteimanserver.exception;

public class ElevationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElevationException() {
		super("Account elevation failed");
	}

	public ElevationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ElevationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
