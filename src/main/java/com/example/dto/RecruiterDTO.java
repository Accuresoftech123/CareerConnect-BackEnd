package com.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.enums.Status;

public class RecruiterDTO {
	private Integer id;

	private String companyName;

	private String email;

	private long mobileNumber;

	private String password;

	private String confirmPassword;

	// Verification fields
	private Boolean isVerified = false;
	private String otp;
	private LocalDateTime otpGeneratedTime;
	private String mobileOtp;
	private LocalDateTime mobileOtpGeneratedTime;
	private Boolean isMobileVerified = false;
	private Status status;

	// Profile sections

	private CompanyProfileDTO companyProfile;

	private List<CompanyLocationDTO> companyLocations;

	private List<String> industries;

	private RecruiterPersonalInfoDTO personalInfo;

	private RecruiterSocialProfileDTO socialProfile;

	// Additional fields from the PDF form
	private String website; // Company website

	public RecruiterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecruiterDTO(Integer id, String companyName, String email, long mobileNumber, String password,
			String confirmPassword, Boolean isVerified, String otp, LocalDateTime otpGeneratedTime, String mobileOtp,
			LocalDateTime mobileOtpGeneratedTime, Boolean isMobileVerified, Status status,
			CompanyProfileDTO companyProfile, List<CompanyLocationDTO> companyLocations, List<String> industries,
			RecruiterPersonalInfoDTO personalInfo, RecruiterSocialProfileDTO socialProfile, String website) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.isVerified = isVerified;
		this.otp = otp;
		this.otpGeneratedTime = otpGeneratedTime;
		this.mobileOtp = mobileOtp;
		this.mobileOtpGeneratedTime = mobileOtpGeneratedTime;
		this.isMobileVerified = isMobileVerified;
		this.status = status;
		this.companyProfile = companyProfile;
		this.companyLocations = companyLocations;
		this.industries = industries;
		this.personalInfo = personalInfo;
		this.socialProfile = socialProfile;
		this.website = website;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpGeneratedTime() {
		return otpGeneratedTime;
	}

	public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}

	public String getMobileOtp() {
		return mobileOtp;
	}

	public void setMobileOtp(String mobileOtp) {
		this.mobileOtp = mobileOtp;
	}

	public LocalDateTime getMobileOtpGeneratedTime() {
		return mobileOtpGeneratedTime;
	}

	public void setMobileOtpGeneratedTime(LocalDateTime mobileOtpGeneratedTime) {
		this.mobileOtpGeneratedTime = mobileOtpGeneratedTime;
	}

	public Boolean getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(Boolean isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status2) {
		this.status = status2;
	}

	public CompanyProfileDTO getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfileDTO companyProfile) {
		this.companyProfile = companyProfile;
	}

	public List<CompanyLocationDTO> getCompanyLocations() {
		return companyLocations;
	}

	public void setCompanyLocations(List<CompanyLocationDTO> companyLocations) {
		this.companyLocations = companyLocations;
	}

	public List<String> getIndustries() {
		return industries;
	}

	public void setIndustries(List<String> industries) {
		this.industries = industries;
	}

	public RecruiterPersonalInfoDTO getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(RecruiterPersonalInfoDTO personalInfo) {
		this.personalInfo = personalInfo;
	}

	public RecruiterSocialProfileDTO getSocialProfile() {
		return socialProfile;
	}

	public void setSocialProfile(RecruiterSocialProfileDTO socialProfile) {
		this.socialProfile = socialProfile;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}