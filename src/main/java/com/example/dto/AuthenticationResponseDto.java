package com.example.dto;

public class AuthenticationResponseDto {
	
	 private String token;
	 private String role;
	public AuthenticationResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthenticationResponseDto(String token, String role) {
		super();
		this.token = token;
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	 
	 

}
