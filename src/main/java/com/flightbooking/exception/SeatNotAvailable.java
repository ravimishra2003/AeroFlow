package com.flightbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatNotAvailable extends RuntimeException{
	public SeatNotAvailable(String message) {
		super(message);
	}
}
