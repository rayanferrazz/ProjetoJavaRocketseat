package com.rocketseat.project.exception;

public class CheckInAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CheckInAlreadyExistException(String message) {
		super(message);
	}
}