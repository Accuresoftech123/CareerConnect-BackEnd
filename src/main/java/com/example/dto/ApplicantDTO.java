package com.example.dto;

import java.time.LocalDate;
import java.util.List;

public class ApplicantDTO {
    private Long applicationId;
    private int jobPostId;
    
    private String resumeFileName; // For file upload handling
    
    // Fields for Recruiter View
   
    private String jobSeekerName;
    private String email;
    private String mobileNumber;
    private List<JobSeekerEducationDto> educationList;
    private List<JobSeekerExperienceDto> experienceList;
   
    private List<String> skills;
    
    private LocalDate appliedDate;
    private String status; // From Enum ApplicationStatus (e.g., SUBMITTED, SHORTLISTED)
 long    DaysSinceApplication;
 private String jobPostTitle;
 private String jobPostLocation;

    public String getJobPostTitle() {
	return jobPostTitle;
}

public void setJobPostTitle(String jobPostTitle) {
	this.jobPostTitle = jobPostTitle;
}

public String getJobPostLocation() {
	return jobPostLocation;
}

public void JobPostLocation(String jobPostLocation) {
	this.jobPostLocation = jobPostLocation;
}

	public ApplicantDTO(Long applicationId, int jobPostId, List<JobSeekerExperienceDto> experienceList, List<JobSeekerEducationDto> educationList,
			String mobileNumber,String jobSeekerName,
		String resumeFileName,  String email,  List<String> skills,
		  LocalDate appliedDate, String status,
		long DaysSinceApplication, String jobPostTitle, String jobPostLocation) {
	super();
	this.applicationId = applicationId;
	this.jobPostId = jobPostId;
	
	this.resumeFileName = resumeFileName;
	
	this.email = email;
	
	this.skills = skills;
	
	
	this.appliedDate = appliedDate;
	this.status = status;
	this.DaysSinceApplication = DaysSinceApplication;
	this.jobPostTitle = jobPostTitle;
	this.jobPostLocation = jobPostLocation;
}

	public long getDaysSinceApplication() {
	return DaysSinceApplication;
}

public void setDaysSinceApplication(long DaysSinceApplication) {
	this.DaysSinceApplication = DaysSinceApplication;
}

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

	

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
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

	public String getJobSeekerName() {
		return jobSeekerName;
	}

	public void setJobSeekerName(String jobSeekerName) {
		this.jobSeekerName = jobSeekerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public void setJobPostLocation(String jobPostLocation) {
		this.jobPostLocation = jobPostLocation;
	}

	public ApplicantDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
  

    

	

    
}
