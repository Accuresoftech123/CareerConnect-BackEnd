package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;



/**
 * Data Transfer Object (DTO) for Job Seeker Profile. Used to transfer job
 * seeker profile data between layers.
 */
public class JobSeekerProfileDto {
	private int id;
	private String fullName; // Job seeker's Full name
	private String email; // Job seeker's email address
	private String mobileNumber;
	private JobSeekerPersonalInfoDto personalInfo;
	private List<JobSeekerEducationDto> educationList;
	private List<JobSeekerExperienceDto> experienceList;
	private List<String> skills;
	private JobSeekerJonPreferencesDto jobPreferences;
	private JobSeekerSocialProfileDto scoicalProfile;
	
	    private LocalDateTime createdAt;

	    @PrePersist
	    public void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }
	public JobSeekerProfileDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public JobSeekerProfileDto(int id, String fullName, String email, String mobileNumber,
			JobSeekerPersonalInfoDto personalInfo, List<JobSeekerEducationDto> educationList,
			List<JobSeekerExperienceDto> experienceList, List<String> skills, JobSeekerJonPreferencesDto jobPreferences,
			JobSeekerSocialProfileDto scoicalProfile,LocalDateTime createdAt) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.personalInfo = personalInfo;
		this.educationList = educationList;
		this.experienceList = experienceList;
		this.skills = skills;
		this.jobPreferences = jobPreferences;
		this.scoicalProfile = scoicalProfile;
	}



	public JobSeekerPersonalInfoDto getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(JobSeekerPersonalInfoDto personalInfo) {
		this.personalInfo = personalInfo;
	}
	public List<JobSeekerEducationDto> getEducationList() {
		return educationList;
	}
	public void setEducationList(List<JobSeekerEducationDto> educationList) {
		this.educationList = educationList;
	}
	public List<JobSeekerExperienceDto> getExperienceList() {
		return experienceList;
	}
	public void setExperienceList(List<JobSeekerExperienceDto> experienceList) {
		this.experienceList = experienceList;
	}
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public JobSeekerJonPreferencesDto getJobPreferences() {
		return jobPreferences;
	}
	public void setJobPreferences(JobSeekerJonPreferencesDto jobPreferences) {
		this.jobPreferences = jobPreferences;
	}
	public JobSeekerSocialProfileDto getScoicalProfile() {
		return scoicalProfile;
	}
	public void setScoicalProfile(JobSeekerSocialProfileDto scoicalProfile) {
		this.scoicalProfile = scoicalProfile;
	}



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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	

	

}
