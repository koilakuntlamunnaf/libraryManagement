package com.example.demo.model;

import java.time.LocalDateTime;

public class ResponseError {
	
	private String message;
	private String description;
	private LocalDateTime time;
	
	public ResponseError(String message, String description) {
		this.message = message;
		this.description = description;
		this.time = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "ResponseError [message=" + message + ", description=" + description + ", time=" + time + "]";
	}
	

}
