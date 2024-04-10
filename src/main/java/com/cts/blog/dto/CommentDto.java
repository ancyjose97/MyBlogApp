package com.cts.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

	private Long id;

	@NotEmpty(message = "name should not be null or empty")
	private String name;
	
	@NotEmpty(message = "email should not be null or empty")
	@Email(message = "provide valid email")
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Comment body must have minimum of 10 characters")
	private String body;
	
	
}
