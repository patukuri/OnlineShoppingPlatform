package com.securecoding.onelineshoppingplatform.exception;

public class AuthenticationFailException extends IllegalArgumentException{

	public AuthenticationFailException(String message) {
		super(message);
	}
}
