package com.example.dto;

public class AdminRegisterDto {

	    private String email;
	    private String password;
		public AdminRegisterDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public AdminRegisterDto(String email, String password) {
			super();
			this.email = email;
			this.password = password;
		}
		public String getEmail() {
			return email;
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
