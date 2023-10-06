package com.abdelhakim.cnc.login.payload.request;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String cin;

	@NotBlank
	private String password;

	public String getCin() {
		return cin;
	}

	public void setUsername(String cin) {
		this.cin = cin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
