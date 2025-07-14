package com.example.exception;

public class JobAlreadySavedException extends RuntimeException {
	 public JobAlreadySavedException(String message) {
	        super(message);
	    }

}
