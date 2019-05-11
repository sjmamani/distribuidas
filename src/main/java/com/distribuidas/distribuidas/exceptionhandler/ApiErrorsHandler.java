package com.distribuidas.distribuidas.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//import exceptions.ClienteException;

@ControllerAdvice
public class ApiErrorsHandler extends ResponseEntityExceptionHandler{
	
	 @ExceptionHandler(Exception.class)
	 @ResponseBody
	  protected ResponseEntity<ErrorMessage> handleException(Exception ex) {
		 ErrorMessage er = new ErrorMessage(ex.getMessage(),"esto es una prueba");
		 return new ResponseEntity<ErrorMessage>(er, new HttpHeaders(), HttpStatus.NOT_FOUND);
	  }
	 
}

class ErrorMessage{
	private String error;
	private String detalle;
	
	public ErrorMessage(String error, String detalle) {
		super();
		this.error=error;
		this.detalle=detalle;
	}
	
	public String getMessage() {
		return this.error;
	}
	
	public String getDetails() {
		return this.detalle;
	}
}
