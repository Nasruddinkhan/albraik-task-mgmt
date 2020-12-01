package com.albraik.task.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailExistsException.class)
	public ResponseEntity<?> emailExists(EmailExistsException ex, WebRequest request) {
		System.out.println(ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PhoneNumberExistException.class)
	public ResponseEntity<ErrorDetails> phoneNumberExists(PhoneNumberExistException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ErrorDetails> unauthorizedAccess(UnauthorizedAccessException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<?> usernameNotFoundException(UserNotFound ex, WebRequest request) {
		System.out.println(ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetails> badRequest(BadRequestException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceGoneException.class)
	public ResponseEntity<ErrorDetails> resourceGone(ResourceGoneException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.GONE);
	}

}
