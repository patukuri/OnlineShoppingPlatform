package com.securecoding.onelineshoppingplatform.exception;

import java.net.http.HttpHeaders;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = CustomException.class)
	public final ResponseEntity<String> handleCustomException(CustomException exception){
		
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=AuthenticationFailException.class)
	public final ResponseEntity<String>handleAuthenticationFailException(AuthenticationFailException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	  @ExceptionHandler(value = ProductNotExistsException.class)
	    public final ResponseEntity<String> handleProductNotExistsException(ProductNotExistsException exception) {
	        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	  
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex,
	            HttpHeaders headers, HttpStatus status, WebRequest request) {
	             
	        Map<String, Object> responseBody = new LinkedHashMap<>();
	        responseBody.put("timestamp", new Date());
	        responseBody.put("status", status.value());
	         
	        List<String> errors = ex.getBindingResult().getFieldErrors()
	            .stream()
	            .map(x -> x.getDefaultMessage())
	            .collect(Collectors.toList());
	         
	        responseBody.put("errors", errors);
	         
	        return new ResponseEntity<Object>(responseBody, status);
	    }
}
