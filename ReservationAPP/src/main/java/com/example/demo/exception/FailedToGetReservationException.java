package com.example.demo.exception;

public class FailedToGetReservationException extends RuntimeException {
	public FailedToGetReservationException(String message) {
		super(message);
	}
}
