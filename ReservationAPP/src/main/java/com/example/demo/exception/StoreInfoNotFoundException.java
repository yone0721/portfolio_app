package com.example.demo.exception;

public class StoreInfoNotFoundException extends RuntimeException {
	public StoreInfoNotFoundException(String message) {
		super(message);
	};
}
