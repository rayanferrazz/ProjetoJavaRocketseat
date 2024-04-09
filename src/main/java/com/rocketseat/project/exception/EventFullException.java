package com.rocketseat.project.exception;

public class EventFullException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EventFullException(String message) {
		super(message);
	}
}
