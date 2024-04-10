package com.cts.blog.dto;

import java.util.Set;

import com.cts.blog.entity.Category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Entity class is the output retrieved in JSON format in postman.
//DTO class is the input taken in JSON in postman.
//DTO class is created only with the required fields that are taken from Entity class.
//i.e. DTO class takes the inputs from client to controller, service and stores them in Entity class in repository as output

@Data //Getters, Setters, toString() etc.

public class PostDto {

	/* STEPS FOR ADDING BEAN VALIDATION
	 * 
	 * 1. Since controller class has PostDto class near to @requestbody, we are adding validations for each / needed field in this postDto class. Because postDto takes input
	 * 2. Add spring boot starter validation dependency
	 * 3. Add validation annotations to this postDto class.
	 * 4. Add @Valid annotation near to @requestbody annotation of createPost API and updatePost API in postController
	 * 
	 * For throwing field Validation errors in a customised way, i.e. when proper values are not given as input in fields, handle it in globalexceptionhandler class.
	 * */
	
    private Long id;
    
    //title should not be null or empty
    //title should have atleast 2 characters
    
    @NotEmpty
    @Size(min = 2, message = "Post title should have atleast 2 characters")
    private String title;
    
    //description should not be null or empty
    //description should have atleast 10 characters
    
    @NotEmpty
    @Size(min = 10, message = "Post description should have atleast 10 characters")
    private String description;
    
    //content should not be null or empty
    
    @NotEmpty
    private String content;
    
    //Added this finally (i.e. after creating comment class logics) because, would like to  retrieve also the comments along with other details in the post.
    private Set<CommentDto> comments;
    
    //Adding this finally (i.e. after creating category class logics), because while creating a post, i want to tag post to some category.
    
  private Long categoryId;
  
    
    
}
