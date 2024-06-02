package com.example.demo.entity;

import jakarta.validation.constraints.NotEmpty;

public class LoginForm {
	
	@NotEmpty
	private String mail;
	
	@NotEmpty
	private String password;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public LoginForm() {
		
	}
	public LoginForm(String mail,String password) {
		this.mail = mail;
		this.password = password;
	}
}
