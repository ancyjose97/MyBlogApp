package com.cts.blog.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.blog.dto.LoginDto;
import com.cts.blog.dto.RegisterDto;
import com.cts.blog.entity.Role;
import com.cts.blog.entity.User;
import com.cts.blog.exception.BlogAPIException;
import com.cts.blog.repository.RoleRepository;
import com.cts.blog.repository.UserRepository;
import com.cts.blog.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginDto loginDto) {

		// This method typically uses the provided credentials to verify the user's
		// identity against a user store (e.g., database, LDAP server)

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		// Authentication object is stored in the security context using
		// SecurityContextHolder.getContext().setAuthentication(authentication).

		// This step is crucial as it allows the application to access the authenticated
		// user's information throughout the user's session.

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;

		//return "User logged in successfully !";
	}

	@Override
	public String register(RegisterDto registerDto) {

		// check if username exists in database

		if (userRepository.existsByUsername(registerDto.getUsername())) {

			throw new BlogAPIException("Username already exists !");
		}

		// check if email exists in database

		if (userRepository.existsByEmail(registerDto.getEmail())) {

			throw new BlogAPIException("Email already exists !");
		}

		User user = new User();

		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role role = roleRepository.findByName("ROLE_USER").get();

		roles.add(role);

		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully !";
	}

}
