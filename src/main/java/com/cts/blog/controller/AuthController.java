package com.cts.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.blog.dto.JwtAuthResponse;
import com.cts.blog.dto.LoginDto;
import com.cts.blog.dto.RegisterDto;
import com.cts.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	private AuthService authService;
	
	//Dependency injection -> constructor based

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	// Build login REST API
	
	@PostMapping(value = {"/login", "/signin"})
	//public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
	
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		
		//String response = authService.login(loginDto);
		
		String token = authService.login(loginDto);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		
		jwtAuthResponse.setAccessToken(token);
		
		
		// As a response to submitting the login details in Postman, we get the fields and their values of JwtAuthResponse class in output
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
		
		//return ResponseEntity.ok(response);
		
		
	}
	
	
	@PostMapping(value = {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		
		String response = authService.register(registerDto);
		
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
		
		
	}
}
