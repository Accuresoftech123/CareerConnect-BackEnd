package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
    
    public DuplicateEmailException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: %s", 
              resourceName, fieldName, fieldValue));
    }
}