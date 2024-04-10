package com.cts.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.blog.entity.User;
import com.cts.blog.repository.UserRepository;

// This class is to load user object / user details from database


//Spring security provides UserDetailsService interface

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	//Overriding the method "loadUserByUsername" provided by UserDetailsService service
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		
		/* 

		Overall, the below code retrieves user information from the database, converts the user's roles into GrantedAuthority objects, and
		 creates a User object that encapsulates the user's details for authentication and authorization purposes within the Spring Security framework. 
		 
		 The User object is then returned, and it will be used by Spring Security for further authentication and authorization processes.
		 
		 *
		 */

		
		//Fetching user object / user details from DB
		
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with the provided username or email " + usernameOrEmail));
		
		//COnverting Roles of the fetched user to Granted Authority object
		//i.e converting role object to Granted Authority object
		
		Set<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		//return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		
		return new org.springframework.security.core.userdetails.User(usernameOrEmail, user.getPassword(), authorities);
		
		
		
	}
	
	
}
