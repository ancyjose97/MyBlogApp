package com.cts.blog.dto;



//Only entity classes will have tables in DB. DTO classes will not have tables in DB. DTO classes 
//are just used for transferring the data or to input the data from client 
// to save in entity class tables in DB.

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

	
	private String accessToken;
	private String tokenType = "Bearer";
}
