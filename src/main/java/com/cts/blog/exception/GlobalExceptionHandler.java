package com.cts.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.blog.dto.ErrorDetails;

/* STEPS FOR EXCEPTION HANDLING
 * 1. Initially, create specific exception classes and throw exceptions from serviceimpl classes. this will throw exception in default way, that is with default fields/ format
 * 
 * But If we want exception response in a customised way, then, do the following step.
 * 
 * 2. Later, for global / custom exception handling or global / custom exception response , create errrodetails class with required fields and create global exception handler class and add the needed @exception handler methods.
 * 
 * 
 * 
 * */

@ControllerAdvice // Handles exceptions globally. i.e. We can add all the needed Exception classes
					// here for customised error response

//ResponseEntityExceptionHandler class is extended when field Validation are errors to be thrown in customised way . Else, no need to extend it.

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// Handles specific Exceptions

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	// handles global exceptions. i.e other exceptions

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	//Approach 1 for  Customised validation response. i.e. Validation errors to be thrown in customised way when field values of DTO classes are not properly given as input
	
	//ResponseEntityExceptionHandler class is extended only when Approach 1 is followed

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																HttpHeaders headers, 
																HttpStatusCode status, 
																WebRequest request) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			
		String fieldName = ((FieldError)error).getField();
		String message = error.getDefaultMessage();
		
		errors.put(fieldName, message);
		
		});

		return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
	}
	
	//Approach 2 for  Customised validation response. i.e. Validation errors to be thrown in customised way when field values of DTO classes are not properly given as input
	
	/*
	  @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {

		
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			
		String fieldName = ((FieldError)error).getField();
		String message = error.getDefaultMessage();
		
		errors.put(fieldName, message);
		
		});
		return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
	}
	*/
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);

	}
	
	
	
	

}
