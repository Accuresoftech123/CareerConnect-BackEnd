package com.example.dto;

public class JobSeekerJonPreferencesDto {

	private String desiredJobTitle;
	private String jobType;
	private Double expectedSalary;
	private String preferredLocation;
	public JobSeekerJonPreferencesDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerJonPreferencesDto(String desiredJobTitle, String jobType, Double expectedSalary,
			String preferredLocation) {
		super();
		this.desiredJobTitle = desiredJobTitle;
		this.jobType = jobType;
		this.expectedSalary = expectedSalary;
		this.preferredLocation = preferredLocation;
	}
	public String getDesiredJobTitle() {
		return desiredJobTitle;
	}
	public void setDesiredJobTitle(String desiredJobTitle) {
		this.desiredJobTitle = desiredJobTitle;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public Double getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(Double expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public String getPreferredLocation() {
		return preferredLocation;
	}
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	
}
