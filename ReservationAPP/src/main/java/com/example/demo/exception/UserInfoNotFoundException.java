package com.example.demo.exception;

public class UserInfoNotFoundException extends RuntimeException {
	public UserInfoNotFoundException(String message) {
		super(message);
	}
	
}
