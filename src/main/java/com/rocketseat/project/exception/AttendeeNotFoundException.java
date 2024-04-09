package com.rocketseat.project.exception;

public class AttendeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AttendeeNotFoundException(String message) {
		super(message);
	}
}