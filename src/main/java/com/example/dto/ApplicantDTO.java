package com.example.dto;

import java.time.LocalDate;
import java.util.List;

public class ApplicantDTO {
    private Long applicationId;
    private int jobPostId;
    private String coverLetter;    
    private String expectedSalary;
    private String availability;
    private String resumeFileName; // For file upload handling
    
    // Fields for Recruiter View
    private String jobSeekerName;
    private String email;
    private int totalExperience;
    private List<String> skills;
    private String highestQualification;
    private String resumeUrl;
    private LocalDate appliedDate;
    private String status; // From Enum ApplicationStatus (e.g., SUBMITTED, SHORTLISTED)

    public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public int getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(int jobPostId) {
		this.jobPostId = jobPostId;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	public String getJobSeekerName() {
		return jobSeekerName;
	}

	public void setJobSeekerName(String jobSeekerName) {
		this.jobSeekerName = jobSeekerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(int totalExperience) {
		this.totalExperience = totalExperience;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
    public ApplicantDTO() {}

    // Full constructor
    public ApplicantDTO(Long applicationId, int jobPostId, String coverLetter, String expectedSalary,
                        String availability, String resumeFileName, String jobSeekerName, String email,
                        int totalExperience, List<String> skills, String highestQualification,
                        String resumeUrl, LocalDate appliedDate, String status) {
        this.applicationId = applicationId;
        this.jobPostId = jobPostId;
        this.coverLetter = coverLetter;
        this.expectedSalary = expectedSalary;
        this.availability = availability;
        this.resumeFileName = resumeFileName;
        this.jobSeekerName = jobSeekerName;
        this.email = email;
        this.totalExperience = totalExperience;
        this.skills = skills;
        this.highestQualification = highestQualification;
        this.resumeUrl = resumeUrl;
        this.appliedDate = appliedDate;
        this.status = status;
    }

    
}
