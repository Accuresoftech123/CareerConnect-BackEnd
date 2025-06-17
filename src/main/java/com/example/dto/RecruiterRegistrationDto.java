package com.example.dto;

import com.example.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * DTO (Data Transfer Object) for handling Recruiter Registration requests. It
 * captures the necessary information required to register a recruiter account.
 */
public class RecruiterRegistrationDto {

	// Recruiter's personal details
	private String fullName; // First name of the recruiter
	private String lastName; // Last name of the recruiter
	private String email; // Email address of the recruiter
	private long mobileNumber; // Contact number of the recruiter

	// Password fields
	private String password; // Password chosen by the recruiter
	private String confirmPassword; // Confirmation of the password
	
	@Enumerated(EnumType.STRING)
    private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Default constructor.
	 */
	public RecruiterRegistrationDto() {
		super();
		// Default constructor for framework usage or object initialization
	}

	/**
	 * Parameterized constructor to initialize all fields of
	 * RecruiterRegistrationDto.
	 *
	 * @param firstName       First name of the recruiter
	 * @param lastName        Last name of the recruiter
	 * @param email           Email address of the recruiter
	 * @param phoneNo         Phone number of the recruiter
	 * @param password        Password entered by the recruiter
	 * @param confirmPassword Confirmation of the password
	 */
	public RecruiterRegistrationDto(String fullName, String lastName, String email, long phoneNumber, String password,
			String confirmPassword, Status status) {
		super();
		this.fullName = fullName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = phoneNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.status=status;
	}

	// Getters and Setters

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
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
