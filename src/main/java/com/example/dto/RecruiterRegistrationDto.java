package com.example.dto;

/**
 * DTO (Data Transfer Object) for handling Recruiter Registration requests. It
 * captures the necessary information required to register a recruiter account.
 */
public class RecruiterRegistrationDto {

	// Recruiter's personal details
	private String firstName; // First name of the recruiter
	private String lastName; // Last name of the recruiter
	private String email; // Email address of the recruiter
	private long phoneNumber; // Contact number of the recruiter

	// Password fields
	private String password; // Password chosen by the recruiter
	private String confirmPassword; // Confirmation of the password

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
	public RecruiterRegistrationDto(String firstName, String lastName, String email, long phoneNumber, String password,
			String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	// Getters and Setters

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public long getPhoneNo() {
		return phoneNumber;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNumber = phoneNo;
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
