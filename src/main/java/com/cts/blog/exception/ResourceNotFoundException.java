package com.cts.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus isn't necessary to be annotated here as we are sending the status code via Global Exception handler class.

@ResponseStatus(value = HttpStatus.NOT_FOUND) // Sends this HTTP Status code when this exception is thrown by the controller
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Long fieldValue;
    

    public ResourceNotFoundException(String resourceName, String fieldName, Long id) {

       super(String.format("%s not found with %s : %s", resourceName, fieldName, id));
    	
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = id;
    }

 



	public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }

}
