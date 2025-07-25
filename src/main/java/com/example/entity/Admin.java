package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String email;
	    private String password;
	    private LocalDateTime otpGeneratedTime;
	    private String otp;
		public Admin() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Admin(int id, String email, String password,LocalDateTime otpGeneratedTime,String otp) {
			super();
			this.id = id;
			this.email = email;
			this.password = password;
			this.otpGeneratedTime=otpGeneratedTime;
			this.otp=otp;
		}
		
		 public Admin(String email, String password) {
		        this.email = email;
		        this.password = password;
		    }
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		public LocalDateTime getOtpGeneratedTime() {
			return otpGeneratedTime;
		}
		public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
			this.otpGeneratedTime = otpGeneratedTime;
		}
		public String getOtp() {
			return otp;
		}
		public void setOtp(String otp) {
			this.otp = otp;
		}
		
	    
	    
}
