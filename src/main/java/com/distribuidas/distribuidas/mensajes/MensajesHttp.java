package com.distribuidas.distribuidas.mensajes;

import org.springframework.http.HttpStatus;

public class MensajesHttp {
	
	public static Message get200Code(){
		return new Message (HttpStatus.OK.toString(),200);
	}
	
	
}
class Message{
	private String message;
	private int code;
	
	public Message(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getCode() {
		return this.code;
	}
}