package com.cg.bugtracking.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseErrorResponse {
	
	private int statusCode;
	private String message;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	
    private LocalDateTime timestamp;   
    
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
