package com.Internity.extraservice;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler{

	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                 HttpHeaders headers, HttpStatus status, WebRequest request) {
	        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
	        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError->fieldError.getDefaultMessage()).collect(Collectors.toList());
	        //LOGGER.info("Validation error list : "+validationList);
	        CustomApiError customApiError = new CustomApiError(errorMessage);
	        customApiError.setErrors(validationList);
	        return new ResponseEntity<>(customApiError, status);
	 }
	
	 
	 @ExceptionHandler(ConstraintViolationException.class)
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		 CustomApiError customApiError = new CustomApiError(new Date(),HttpStatus.BAD_REQUEST, ex.getMessage(), "error occurred");
		 return new ResponseEntity<>(customApiError, HttpStatus.BAD_REQUEST);
	 }
	
	 @ExceptionHandler(NoSuchElementException.class)
	 @ResponseStatus(HttpStatus.NOT_FOUND)
	 ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
		 CustomApiError customApiError = new CustomApiError(new Date(),HttpStatus.NOT_FOUND, ex.getMessage(), "error occurred");
		 return new ResponseEntity<>(customApiError, HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler({ Exception.class })
	 public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	    CustomApiError customApiError = new CustomApiError(new Date(),HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "error occurred");
	    return new ResponseEntity<Object>(
	    		customApiError, new HttpHeaders(), customApiError.getStatus());
	 }
}
