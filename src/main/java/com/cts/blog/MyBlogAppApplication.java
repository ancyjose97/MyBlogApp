package com.cts.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBlogAppApplication {

	
	
	
	/*	ModelMapper is used to map one object to another.
	 * 
	 * Eg: Mapping CommentDTO class to CommentEntity
	 * 
	 * Eg: comment.setName(commentDto.getName());
	 * 		comment.setBody(commentDto.getBody());
	 * 
	 * STEPS:
	 * 1. Add ModelMapper dependency in pom.xml
	 * 2. Add modelMapper() method in main class for Bean configuration.
	 * 3. Inject ModelMapper as a dependency in service class
	 * 	  
	 *  */
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(MyBlogAppApplication.class, args);
	}

}
