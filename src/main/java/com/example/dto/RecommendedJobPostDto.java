package com.example.dto;

import java.util.List;

public class RecommendedJobPostDto {
	
	 private int id;
	 private String title;
	 private String companyName;
	 private String location;
	 private String jobType;
	 private double minSalary; 
	 private double maxSalary;
	 private List<String> skills;
	 
	 
	public RecommendedJobPostDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RecommendedJobPostDto(int id, String title, String companyName, String location, String jobType,
			double minSalary, double maxSalary, List<String> skills) {
		super();
		this.id = id;
		this.title = title;
		this.companyName = companyName;
		this.location = location;
		this.jobType = jobType;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.skills = skills;
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


	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	

	 
}
