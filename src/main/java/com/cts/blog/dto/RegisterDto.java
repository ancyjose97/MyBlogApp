package com.cts.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

	
	//using the fields which is in user entity class
	
	private String name;
	private String username;
	private String email;
	private String password;
}
