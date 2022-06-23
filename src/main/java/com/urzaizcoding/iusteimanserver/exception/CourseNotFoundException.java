package com.urzaizcoding.iusteimanserver.exception;

public class CourseNotFoundException extends ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7477046705257486233L;

	public CourseNotFoundException() {
		super("The course associated with the given id was not found");
	}

	public CourseNotFoundException(String message) {
		super(message);
	}
}
