package com.cts.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.blog.security.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	private JwtTokenProvider jwtTokenProvider;
	
	
	//we actually need to autowire customuserDetailsService class. but instead of autowiring it, we are autowiring the interface from which that class is implemented
	private UserDetailsService userDetailsService;
	
	//constructor based injection
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}
	
	
	
	/* when the client makes an API request to the application and includes the JWT token, 
	 * this code is executed to validate the token, extract the username, load the user details, 
	 * and set the authentication token in the security context. This ensures that the user is considered 
	 * authenticated for the duration of the request processing.
		
		So, to summarize, this code can be used after the user has logged in and is sending an API request 
		with a valid JWT token for authentication purposes. 
		
		*/
	// This method is basically to authenticate the JWT token which the user sent in the header along with the API request
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)	throws ServletException, IOException {
		
		
		//Get Jwt Token from request / http request
		
		String token = getTokenFromRequest(request);
		
		//validate token
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			//get username from token
			
			String username = jwtTokenProvider.getUsername(token);
			
			//load the user associated with token
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
//The purpose of the UsernamePasswordAuthenticationToken class is to represent an authentication request that includes a username and password. 
//It is typically created when a user submits their credentials during the login process. 
//This token is then passed to the authentication provider, which verifies the provided credentials against a user database or other authentication mechanism.
//Once the authentication provider processes the UsernamePasswordAuthenticationToken and successfully verifies the credentials, 
//it returns an authenticated Authentication object to indicate that the user has been authenticated. 
//This authenticated object is then stored in the security context, allowing the application to track the authenticated user throughout the session.
			
			
			
			/* In summary, the code creates a new UsernamePasswordAuthenticationToken with the user details and authorities,
			 *  attaches additional request details to the token, and then sets the token as the authentication object in the security context. 
			 *  This ensures that the user is considered authenticated throughout the request processing. */
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			
			//attaches additional request details to the authenticationToken. This provides more information about the authentication request.
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	


		}
		
		//The FilterChain interface in Java Servlet technology represents a chain of filters that are applied to 
		//an incoming request before it reaches its intended destination (such as a servlet or a resource). 
		//The purpose of the FilterChain is to provide a way to perform common preprocessing and postprocessing tasks on the request and response objects.

		
		//doFIlter is invoking next filter in the chain to process the request and response objects.
		//If there are no more filters in the chain, the request eventually reaches the target servlet or resource.
		
		filterChain.doFilter(request, response);
		
	}
	
	
	
	



	//Get Jwt Token from request / http request
	private String getTokenFromRequest(HttpServletRequest request) {
		
		//JWT token is sent in header along with each api request. we can seee that in postman
		//in headers tab in postman, the key is authorization for the value jwt token.
		
		//below line of code will return the value of the key "authorization" as a string
		String bearerToken = request.getHeader("Authorization");
		
		//hasText method from stringutils class is used here to check if bearer token string variable has any meaningful content instead of null, empty or whitespace character
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			
			//below line of code will return the token alone by extracting the token from the value of the key "authorization"
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
