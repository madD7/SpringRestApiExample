package com.example.practicaljava.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.practicaljava.api.response.ErrorResponse;

@RestControllerAdvice 			// Makes the class Global Exception Handler.
public class GlobalExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = IllegalApiParamException.class)
	private ResponseEntity<ErrorResponse> handleIllegalApiParamException(IllegalApiParamException e) {
		LOG.warn(e.getMessage());
		var errResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		
		// This is ResponseEntity Constructor way
		// return new ResponseEntity<ErrorResponse>(errResponse, null, HttpStatus.BAD_REQUEST);
		
		// Below is ResponseEntity builder-MethodChaining way 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);   		// is also valid.
	}
	
}
