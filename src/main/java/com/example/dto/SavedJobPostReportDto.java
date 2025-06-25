package com.example.dto;

import java.util.List;

public class SavedJobPostReportDto {
	
	 private int id;
	 private String title;
	 private String companyName;
	 private String location;
	 private double salary;
	 private String jobType;
	 private List<String> skills;
	 
	 
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	public SavedJobPostReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SavedJobPostReportDto(int id, String title, String companyName, String location, double salary, String jobType,
			List<String> skills) {
		super();
		this.id=id;
		this.title = title;
		this.companyName = companyName;
		this.location = location;
		this.salary = salary;
		this.jobType = jobType;
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


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getJobType() {
		return jobType;
	}


	public void setJobType(String jobType) {
		this.jobType = jobType;
	}


	
	 
	 

}
