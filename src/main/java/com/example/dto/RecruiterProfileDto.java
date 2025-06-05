package com.example.dto;

import com.example.enums.Status;

/**
 * DTO (Data Transfer Object) for Recruiter Profile Information.
 * This class contains both company details and recruiter's personal details
 * that are used to display or update the recruiter's profile.
 */
public class RecruiterProfileDto {

    // Company-related fields
    private String companyName;              // Name of the company
    private String companyAddress;           // Physical address of the company
    private String companyDescription;       // Short description about the company
    private String companyWebsiteUrl;        // Official website URL of the company
    private int numberOfEmployees;           // Total number of employees in the company
    private String industryType;             // Industry type (e.g., IT, Healthcare, etc.)

    // Recruiter’s Personal Details
    private String firstName;                // First name of the recruiter
    private String lastName;                 // Last name of the recruiter
    private String email;                    // Email address of the recruiter
    private long phoneNumber;                // Contact number of the recruiter
    private String city;                     // City where the recruiter is located
    private Status status;
    /**
     * Default constructor.
     */
    public RecruiterProfileDto() {
        super();
    }

    /**
     * Parameterized constructor to initialize all fields of the RecruiterProfileDto.
     *
     * @param companyName         Name of the company
     * @param companyAddress      Address of the company
     * @param companyDescription  Description of the company
     * @param companyWebsiteUrl   Website URL of the company
     * @param numberOfEmployees   Number of employees in the company
     * @param industryType        Industry type of the company
     * @param firstName           First name of the recruiter
     * @param lastName            Last name of the recruiter
     * @param email               Email of the recruiter
     * @param phoneNumber         Phone number of the recruiter
     * @param city                City of the recruiter
     */
    public RecruiterProfileDto(String companyName, String companyAddress, String companyDescription,
                               String companyWebsiteUrl, int numberOfEmployees, String industryType,
                               String firstName, String lastName, String email, long phoneNumber, String city, Status status) {
        super();
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyDescription = companyDescription;
        this.companyWebsiteUrl = companyWebsiteUrl;
        this.numberOfEmployees = numberOfEmployees;
        this.industryType = industryType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.status=status;
    }

    // Getters and Setters

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
    
}
