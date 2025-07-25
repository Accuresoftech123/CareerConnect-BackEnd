package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Recruiter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RecruiterProfileDto {
	
	private String companyName;
	private long mobileNumber;
	private String recruiterEmail;
	private int recruiterId;

	// Company info
	CompanyProfileDTO CompanyProfile;
	private List<CompanyLocationDTO> companyLocation;
	
	private RecruiterDTO recruiter;
	public RecruiterProfileDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecruiterProfileDto(String companyName, long mobileNumber, CompanyProfileDTO companyProfile,
			List<CompanyLocationDTO> companyLocation, String recruiterEmail) {
		super();
		this.companyName = companyName;
		this.mobileNumber = mobileNumber;
		this.CompanyProfile = companyProfile;
		this.companyLocation = companyLocation;
		this.recruiterEmail = recruiterEmail;
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	

	public CompanyProfileDTO getCompanyProfile() {
		return CompanyProfile;
	}

	public void setCompanyProfile(CompanyProfileDTO companyProfile) {
		CompanyProfile = companyProfile;
	}

	

	public List<CompanyLocationDTO> getCompanyLocation() {
		return companyLocation;
	}

	public void setCompanyLocation(List<CompanyLocationDTO> companyLocation) {
		this.companyLocation = companyLocation;
	}

	public String getRecruiterEmail() {
		return recruiterEmail;
	}

	public void setRecruiterEmail(String recruiterEmail) {
		this.recruiterEmail = recruiterEmail;
	}
	public void setCompanyLocations(List<CompanyLocationDTO> companyLocations) {
        this.companyLocation = companyLocations != null ? companyLocations : new ArrayList<>();
    }

	public RecruiterDTO getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(RecruiterDTO recruiter) {
		this.recruiter = recruiter;
	}

	public int getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	
	
}