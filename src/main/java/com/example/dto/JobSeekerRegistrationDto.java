package com.example.dto;



import jakarta.persistence.Transient;

/**
 * Data Transfer Object (DTO) class used for job seeker registration. This class
 * holds the information submitted by a job seeker during registration.
 */
public class JobSeekerRegistrationDto {

	// Basic personal information fields
	private int id;
	private String fullName; // Job seeker's Full name
	private String email; // Job seeker's email address
	private String mobileNumber; // Job seeker's contact mobile number
	private String password; // Password for the account
	private String otp;
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * Transient field not persisted in the database, used only for password
	 * confirmation during registration.
	 */
	@Transient
	private String confirmPassword; // Confirm password field to validate password input

	public JobSeekerRegistrationDto() {
		super();
		// No special initialization required here
		
	}

	/**
	 * Parameterized constructor to initialize all fields of the DTO.
	 */
	public JobSeekerRegistrationDto(int id, String fullName, String email, String mobileNumber, String password,
			String confirmPassword,String otp) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.otp=otp;
	}

	// Getter and Setter methods for all fields below
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}

	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
