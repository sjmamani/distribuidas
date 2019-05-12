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
	  protected ResponseEntity<Message> handleException(Exception ex) {
		 Message er = new Message(ex.getMessage());
		 return new ResponseEntity<Message>(er, new HttpHeaders(), HttpStatus.NOT_FOUND);
	  }
	 
	 @ExceptionHandler(RuntimeException.class)
	 @ResponseBody
	  protected ResponseEntity<Message> handleException(RuntimeException ex) {
		 Message er = new Message(ex.getMessage());
		 return new ResponseEntity<Message>(er, new HttpHeaders(), HttpStatus.NOT_FOUND);
	  }
}

class Message {
	private String message;
	
	public Message(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}

