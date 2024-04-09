package com.rocketseat.project.exception;

public class AttendeeAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AttendeeAlreadyExistException(String message) {
		super(message);
	}
}