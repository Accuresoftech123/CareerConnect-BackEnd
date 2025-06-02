package com.example.dto;

import java.time.LocalDate;

public class JobSeekerExperienceDto {

	private String jobTitle;
	private String companyName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String keyResponsibilities;
	public JobSeekerExperienceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerExperienceDto(String jobTitle, String companyName, LocalDate startDate, LocalDate endDate,
			String keyResponsibilities) {
		super();
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.keyResponsibilities = keyResponsibilities;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getKeyResponsibilities() {
		return keyResponsibilities;
	}
	public void setKeyResponsibilities(String keyResponsibilities) {
		this.keyResponsibilities = keyResponsibilities;
	}
	
	
}
