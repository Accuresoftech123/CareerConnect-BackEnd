package com.example.dto;

import java.util.List;

public class JobSeekerJonPreferencesDto {

	private List<String> desiredJobTitle;
	private List<String> jobTypes; 
	private Double expectedSalary;
	private String preferredLocation;
	public JobSeekerJonPreferencesDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerJonPreferencesDto(List<String> desiredJobTitle, List<String> jobTypes, Double expectedSalary,
			String preferredLocation) {
		super();
		this.desiredJobTitle = desiredJobTitle;
		this.jobTypes = jobTypes;
		this.expectedSalary = expectedSalary;
		this.preferredLocation = preferredLocation;
	}
	
	
	
	public List<String> getDesiredJobTitle() {
		return desiredJobTitle;
	}
	public void setDesiredJobTitle(List<String> desiredJobTitle) {
		this.desiredJobTitle = desiredJobTitle;
	}
	
	public List<String> getJobTypes() {
		return jobTypes;
	}
	public void setJobTypes(List<String> jobTypes) {
		this.jobTypes = jobTypes;
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
