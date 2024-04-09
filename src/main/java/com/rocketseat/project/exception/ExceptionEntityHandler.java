package com.rocketseat.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rocketseat.project.dto.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<?> handleEventNotFound(EventNotFoundException eventNotFoundException) {
		return ResponseEntity.notFound().build();
	}
	
	 @ExceptionHandler(EventFullException.class)
	 public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception){
		 return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
	 }

	 @ExceptionHandler(AttendeeNotFoundException.class)
	 public ResponseEntity<?> handleAttendeeFound(AttendeeNotFoundException exception){
	     return ResponseEntity.notFound().build();
	 }

	 @ExceptionHandler(AttendeeAlreadyExistException.class)
	 public ResponseEntity<?> handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception){
	     return ResponseEntity.status(HttpStatus.CONFLICT).build();
	 }

	 @ExceptionHandler(CheckInAlreadyExistException.class)
	 public ResponseEntity<?> handleCheckInAlreadyExist(CheckInAlreadyExistException exception){
	     return ResponseEntity.status(HttpStatus.CONFLICT).build();
	 }
}