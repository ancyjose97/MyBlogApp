package com.cts.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cts.blog.entity.JwtProperty;
import com.cts.blog.exception.BlogAPIException;
import com.cts.blog.repository.JwtPropertyRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



//This class provides some utility methods to perform JWT Operations

@Component
public class JwtTokenProvider {

	
//	@Value("${app.jwt-secret}")
//	private String jwtSecret;
	
	
//	@Value("${app-jwt-expiration-milliseconds}")
//	private long jwtExpirationDate;
	
	
	@Autowired
	private JwtPropertyRepository jwtPropertyRepository;
	
	//Generate JWT Token
	
	public String generateToken(Authentication authentication) {
		
		JwtProperty value =	jwtPropertyRepository.findById(2L).get();
		
		Long jwtExpirationDate = Long.parseLong(value.getPropertyValue());
 		
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		//getTime() returns the milliseconds format of current date.
		
		
		
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		//Generating / building token
	String token = Jwts.builder()
						.setSubject(username) 
						.setIssuedAt(new Date())
						.setExpiration(expireDate)
						.signWith(key())
						.compact(); //Structuring the token to return a string
	
	return token;
		
		
	}
	
	//returns the generated HMAC-SHA key as a Key object for signing the token to verify and ensure integrity and authenticity of the token.
	//The key is derived from the decoded secret key.
	
	private Key key() {
		
	JwtProperty value = jwtPropertyRepository.findById(1L).get();
	
	String jwtSecret = value.getPropertyValue();
	
		
		return Keys.hmacShaKeyFor(
						Decoders.BASE64.decode(jwtSecret));
	}
	
	
	
	//Get username from JWT Token
	public String getUsername(String token) {
		
		//Parser / parsing means breaking down the token into seperate components
		//Each of the seperate components are called as claims.
		//eg : subject, expiration date, Issued at time, etc are individual claims.
		
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(key())
							.build()  //JWT parser is built or configured to break the token
							.parseClaimsJws(token) // returns Claims object => that is, mutilple claims inside the Claims object 
							.getBody(); // retrieves the body of the parsed token, which contains the claims. 
		
		String username = claims.getSubject();
		
		return username;
 	}
	
	
	//validate JWT token
	
	public boolean validateToken(String token) {
		
		try  {
				Jwts.parserBuilder()
		
				.setSigningKey(key())
		
				.build() 
		
				.parse(token); //Token is parsed / broken and the signature is verified and then the token's content is validated 
						//according to the application's requirements. This validation typically includes checking the expiration time, etc.
		
		return true;
	}
	catch (MalformedJwtException ex) {

		throw new BlogAPIException("Invalid JWT Token");

	} catch (ExpiredJwtException ex) {

		throw new BlogAPIException("Expired JWT Token");

	} catch (UnsupportedJwtException ex) {

		throw new BlogAPIException("Unsupported JWT Token");

	} catch (IllegalArgumentException ex) {

		throw new BlogAPIException("JWT Claims string is empty");

	}

}
	
}
