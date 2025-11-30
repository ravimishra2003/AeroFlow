package com.flightbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PaymentNotFound extends RuntimeException{
	public PaymentNotFound(String message) {
		super(message);
	}
}
