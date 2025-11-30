package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.model.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)	
	public ResponseError handleBookNotFoundException(BookNotFoundException e, WebRequest request) {
		return new ResponseError(e.getMessage(), request.getDescription(false));
	}
	
	@ExceptionHandler(AuthorNotFoundException.class)	
	public ResponseError handleAuthorNotFoundException(AuthorNotFoundException e, WebRequest request) {
		return new ResponseError(e.getMessage(), request.getDescription(false));
	}
	
	@ExceptionHandler(UserNotFoundException.class)	
	public ResponseError handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
		return new ResponseError(e.getMessage(), request.getDescription(false));
	}
}
