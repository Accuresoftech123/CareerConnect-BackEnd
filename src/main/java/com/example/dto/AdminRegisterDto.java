package com.example.dto;

import com.example.enums.Role;

public class AdminRegisterDto {

	    private String email;
	    private String password;
	    private Role role;
		public AdminRegisterDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public AdminRegisterDto(String email, String password,Role role) {
			super();
			this.email = email;
			this.password = password;
			this.role=role;
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
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		
		
	    
	    
}
