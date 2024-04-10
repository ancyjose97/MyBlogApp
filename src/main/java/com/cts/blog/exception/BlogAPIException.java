package com.cts.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



public class BlogAPIException extends RuntimeException {

	
	
	
	private String message;

	
	//Constuctor sets values
	
	public BlogAPIException( String message) {
		
		super(message); // Prints message
		
		
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

}
