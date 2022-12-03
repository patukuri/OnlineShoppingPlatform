package com.securecoding.onlineshoppingplatform.dto;

public class SignInDto {

	
	private String email;
	
	private String password;

	
	public String getEmail() {
		return email;
	}

	public SignInDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
