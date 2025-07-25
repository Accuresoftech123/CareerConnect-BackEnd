package com.example.dto;

import java.time.LocalDate;
import java.util.List;

public class SavedJobPostReportDto {
	
	 private int id;
	 private String title;
	 private String companyName;
	 private String location;
	 private String jobType;
	 private double minSalary; 
	 private double maxSalary;
     private String minExperience;
     private String maxExperience;
	 private List<String> skills;
	 private LocalDate postedDate;
	 
	 
	public SavedJobPostReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SavedJobPostReportDto(int id, String title, String companyName, String location, String jobType,
			double minSalary, double maxSalary, String minExperience, String maxExperience, List<String> skills,
			LocalDate postedDate) {
		super();
		this.id = id;
		this.title = title;
		this.companyName = companyName;
		this.location = location;
		this.jobType = jobType;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.minExperience = minExperience;
		this.maxExperience = maxExperience;
		this.skills = skills;
		this.postedDate = postedDate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getJobType() {
		return jobType;
	}


	public void setJobType(String jobType) {
		this.jobType = jobType;
	}


	public double getMinSalary() {
		return minSalary;
	}


	public void setMinSalary(double minSalary) {
		this.minSalary = minSalary;
	}


	public double getMaxSalary() {
		return maxSalary;
	}


	public void setMaxSalary(double maxSalary) {
		this.maxSalary = maxSalary;
	}


	public String getMinExperience() {
		return minExperience;
	}


	public void setMinExperience(String minExperience) {
		this.minExperience = minExperience;
	}


	public String getMaxExperience() {
		return maxExperience;
	}


	public void setMaxExperience(String maxExperience) {
		this.maxExperience = maxExperience;
	}


	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	public LocalDate getPostedDate() {
		return postedDate;
	}


	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	 
	


	
	 
	 

}
