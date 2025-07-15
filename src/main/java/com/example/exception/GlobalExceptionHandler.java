package com.example.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(AlreadyAppliedException.class)
	    public ResponseEntity<Map<String, String>> handleAlreadyApplied(AlreadyAppliedException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(Map.of("message", ex.getMessage()));
	    }
	 
	 @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("message", ex.getMessage()));
	    }

}
