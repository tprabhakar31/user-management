package com.user.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiError> handleApiException(ApiException ex) {
		ApiError error = new ApiError(
				Instant.now(),
				ex.getStatus().value(),
				ex.getStatus().name(),
				ex.getMessage()
				);		
		return new ResponseEntity<>(error, ex.getStatus());
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiError> handleJwtException(JwtException ex){
			ApiError error = new ApiError(
					Instant.now(),
					HttpStatus.UNAUTHORIZED.value(),
					"UNAUTHORIZED",
					"Invalid or expired Jwt token"
					);
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGenralException(Exception ex){
		ApiError error = new ApiError(
				Instant.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"INTERNAL_SERVER_ERROR",
				ex.getMessage()
				);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
