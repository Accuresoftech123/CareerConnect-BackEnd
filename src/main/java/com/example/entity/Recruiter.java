package com.example.entity;

import com.example.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entity class representing a Recruiter in the system.
 * Maps to the "recruiters" table in the database.
 */
@Entity
@Table(name = "recruiters")
public class Recruiter {
	
	// Primary key for the Recruiter entity, auto-generated
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// --- Company Information ---

	// Name of the recruiter's company
	private String companyName;
	
	// Physical address of the company
	private String companyAddress;
	
	// Description or overview about the company
	private String companyDescription;
	
	// Website URL of the company
	private String companyWebsiteUrl;
	
	// Total number of employees working in the company
	private int numberOfEmployees;
	
	// Type of industry the company operates in
	private String industryType;

	// --- Recruiter's Personal Details ---

	// Recruiter's first name
	private String FirstName;
	
	// Recruiter's last name
	private String LastName;
	
	// Recruiter's email address, used for communication and login
	private String email;
	
	// Recruiter's contact phone number
	private long phoneNumber;
	
	// City where the recruiter is based
	private String city;

	// --- Security ---

	// Password for recruiter login (should be stored securely, ideally hashed)
	private String password;

	// Confirm password field, not persisted to the database (used for validation)
	@Transient
	private String confirmPassword;
	
	
	// Use EnumType.STRING so the enum value is stored as a readable string in the database
	 @Enumerated(EnumType.STRING)
	    private Status status;

	// Getters and Setters for all fields follow
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getCompanyWebsiteUrl() {
		return companyWebsiteUrl;
	}

	public void setCompanyWebsiteUrl(String companyWebsiteUrl) {
		this.companyWebsiteUrl = companyWebsiteUrl;
	}

	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Constructor with all fields (except transient confirmPassword)
	 */
	public Recruiter(int id, String companyName, String companyAddress, String companyDescription,
			String companyWebsiteUrl, int numberOfEmployees, String firstName, String lastName,
			String email, long phoneNumber, String city, String password, String confirmPassword, String industryType,Status status) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyDescription = companyDescription;
		this.companyWebsiteUrl = companyWebsiteUrl;
		this.numberOfEmployees = numberOfEmployees;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.industryType = industryType;
		this.status=status;
	}

	/**
	 * Default no-argument constructor
	 */
	public Recruiter() {
		super();
	}

}
