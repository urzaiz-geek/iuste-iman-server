package com.urzaizcoding.iusteimanserver.exception;

public class StudentNotFoundException extends ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3455137812361542581L;
	
	public StudentNotFoundException() {
		super("The student associated with the given id was not found");
	}

	public StudentNotFoundException(String message) {
		super(message);
	}
}
