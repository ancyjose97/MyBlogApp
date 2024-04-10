package com.cts.blog.dto;

import java.util.Date;

// We have created this class with some fields  for Global / Custom Exception handling.
//To throw encountered exceptions with these details.

public class ErrorDetails {

	
	private Date timestamp;
	
	private String message;
	
	
	private String details;
	
	

	public ErrorDetails(Date timestamp, String message, String details) {
		
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public String getMessage() {
		return message;
	}


	public String getDetails() {
		return details;
	}
	
	
	
	
}
