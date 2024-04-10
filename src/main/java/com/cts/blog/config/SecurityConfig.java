package com.cts.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.blog.security.JwtAuthenticationEntryPoint;
import com.cts.blog.security.JwtAuthenticationFilter;

import jakarta.websocket.Session;

@Configuration // This class has bean definitions. Spring container processes this class and
				// generates beans to be used in this application
@EnableMethodSecurity

public class SecurityConfig {

	//Injecting Interface instead of the class CustomUserDetailsService for loose coupling.
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

//	// Constructor based injection
//
//	public SecurityConfig(UserDetailsService userDetailsService) {
//
//		this.userDetailsService = userDetailsService;
//
//	}

	
	// This method configures HTTP basic authentication
	// This indicates that the customized / configured security filters should be
	// applied to the application's requests.

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable()

				/* Authorizes all requests for all users */

				// .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())

				/*
				 * SECURING REST API's using ROLE BASED AUTHORIZATION (USING THE USERS
				 * CONFIGURED BY IN-MEMORY AUTHENTICATION CONCEPT))
				 * 
				 * Authorizes only GET requests for all users (both admin and user)
				 */

				.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
						
						//for login/signup API
						.requestMatchers("/api/auth/**").permitAll()
						
						.anyRequest().authenticated())
				
					//-----------Following 3 lines added after JWt implementation ----------------------------
				
				//Throws exception for unauthorized users
						.exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
						
						//jwt is a stateless authentication mechanism
						.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
						//JwtAuthenticationFilter is executed before spring security filters.
		//here we are configuring that authenticationFilter is executed before UsernamePasswordAuthenticationFilter
						httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
						
				//------------------------------------------------------------------------------------------------------

				/*
				.httpBasic(Customizer.withDefaults()); // enables http basic authentication with default settings for
														// http basic authentication
			 */

				
		return httpSecurity.build(); // httpSecurity object is built and returned as the SecurityFilterChain bean

	}
	
	
	/*----------------------------------------------------------------------------------------------------------------------------------------*/

	/* Configuring Multiple User Details for In-Memory Authentication */

	/*
	 * Storing user details in server memory is suitable for development and testing
	 * purposes. In a production environment, you would typically use a persistent
	 * data store such as a database to store and manage user details securely.
	 
	
	 -----COMMENTED THE BELOW IN-MEMORY AUTHENTICATION CODE AS WE IMPLEMENTED DATABASE AUTHENTICATION----- 

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails ancy = User.builder().username("ancy").password(passwordEncoder().encode("ancy")) // We shouldn't
																										// store
																										// password as a
																										// plain text
				.roles("USER").build();

		
		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")) // We shouldn't
																											// store
																											// password
																											// as a
																											// plain
																											// text
				.roles("ADMIN").build();
		return new InMemoryUserDetailsManager(ancy, admin);
	}
	
	*/
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------- */

	
	/*
	 * We shouldn't store password as a plain text. So we create a bean for
	 * PasswordEncoder Interface
	 */

	@Bean
	public static PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
	
	
	/*	DATABASE BASED AUTHENTICATION */
	
	//this method returns an object/instance for AuthenticationManager  
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
}
