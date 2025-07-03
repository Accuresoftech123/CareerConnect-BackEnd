package com.example.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.dto.CompanyProfileDTO;
import com.example.entity.profile.*;
import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiters")

public class Recruiter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fullName;

	@Column(unique = true)
	private String email;

	private long mobileNumber;

	
	private String password;

	@Transient
	private transient String confirmPassword;

	// Verification fields
	private boolean isVerified = false;
	private String otp;
	private LocalDateTime otpGeneratedTime;
	private String mobileOtp;
	private LocalDateTime mobileOtpGeneratedTime;
	private boolean isMobileVerified = false;

	// Status field with enum
	@Enumerated(EnumType.STRING)

	private Status status;

	// Relationships
	@OneToOne(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private CompanyProfile companyProfile;

	@OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyLocation> companyLocations;

	@ElementCollection
	@CollectionTable(name = "recruiter_industries", joinColumns = @JoinColumn(name = "recruiter_id"))
	@Column(name = "industry")
	private List<String> industries;

	@OneToOne(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
	private RecruiterSocialProfile socialProfile;

	@OneToOne(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
	private RecruiterPersonalInfo personalInfo;

	public Recruiter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recruiter(Integer id, String fullName, String email, long mobileNumber, String password,
			String confirmPassword, boolean isVerified, String otp, LocalDateTime otpGeneratedTime, String mobileOtp,
			LocalDateTime mobileOtpGeneratedTime, boolean isMobileVerified, Status status,
			CompanyProfile companyProfile, List<CompanyLocation> companyLocations, List<String> industries,
			RecruiterSocialProfile socialProfile, RecruiterPersonalInfo personalInfo) {
		super();
		this.id = id;
		this.fullName = fullName;
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
		this.socialProfile = socialProfile;
		this.personalInfo = personalInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long string) {
		this.mobileNumber = string;
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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
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

	public boolean isMobileVerified() {
		return isMobileVerified;
	}

	public void setMobileVerified(boolean isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

	public List<CompanyLocation> getCompanyLocations() {
		return companyLocations;
	}

	public void setCompanyLocations(List<CompanyLocation> companyLocations) {
		this.companyLocations = companyLocations;
	}

	public List<String> getIndustries() {
		return industries;
	}

	public void setIndustries(List<String> industries) {
		this.industries = industries;
	}

	public RecruiterSocialProfile getSocialProfile() {
		return socialProfile;
	}

	public void setSocialProfile(RecruiterSocialProfile socialProfile) {
		this.socialProfile = socialProfile;
	}

	public RecruiterPersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(RecruiterPersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

	public void addCompanyLocation(CompanyLocation location) {
		location.setRecruiter(this);
		this.companyLocations.add(location);
	}

	public void removeCompanyLocation(CompanyLocation location) {
		location.setRecruiter(null);
		this.companyLocations.remove(location);
	}

}