package com.example.dto;

import jakarta.validation.constraints.*;



public class RecruiterProfileDto {
    private String fullName;
    private long mobileNumber;
    
    // Company info
    private String companyName;
    private String companyAddress;
    private String companyDescription;
    private String companyWebsiteUrl;
    private Integer numberOfEmployees;
    private String industryType;
	public RecruiterProfileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecruiterProfileDto(String fullName, long mobileNumber, String companyName, String companyAddress,
			String companyDescription, String companyWebsiteUrl, Integer numberOfEmployees, String industryType) {
		super();
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyDescription = companyDescription;
		this.companyWebsiteUrl = companyWebsiteUrl;
		this.numberOfEmployees = numberOfEmployees;
		this.industryType = industryType;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	
}