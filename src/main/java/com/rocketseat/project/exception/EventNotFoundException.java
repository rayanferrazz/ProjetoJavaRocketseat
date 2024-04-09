package com.rocketseat.project.exception;

public class EventNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EventNotFoundException (String message) {
		super(message);
	}
}