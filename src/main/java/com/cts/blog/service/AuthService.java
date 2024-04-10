package com.cts.blog.service;

import com.cts.blog.dto.LoginDto;
import com.cts.blog.dto.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
