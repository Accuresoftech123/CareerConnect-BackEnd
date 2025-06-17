package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecruiterNotFoundException extends RuntimeException {
    public RecruiterNotFoundException(String message) {
        super(message);
    }
    
    public RecruiterNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %s", 
              resourceName, fieldName, fieldValue));
    }
}