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
	 
	 @ExceptionHandler(InvalidOtpException.class)
	    public ResponseEntity<?> handleInvalidOtpException(InvalidOtpException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", ex.getMessage()));
	    }

	    @ExceptionHandler(OtpExpiredException.class)
	    public ResponseEntity<?> handleOtpExpiredException(OtpExpiredException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", ex.getMessage()));
	    }

	    @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(Map.of("error", ex.getMessage()));
	    }

	    // Optional: fallback for unhandled exceptions
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleGeneralException(Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "Something went wrong", "details", ex.getMessage()));
	    }
}
