package com.example.dto;

import java.util.List;

public class RecommendedJobPostDto {
	
	 private int id;
	 private String title;
	 private String companyName;
	 private String location;
	 private String employmentType;;
	 private double minSalary; 
	 private double maxSalary;
	 private List<String> skills;
	 private boolean bookmarked;
	 
	 
	public RecommendedJobPostDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RecommendedJobPostDto(int id, String title, String companyName, String location, String employmentType,
			double minSalary, double maxSalary, List<String> skills ,boolean bookmarked) {
		super();
		this.id = id;
		this.title = title;
		this.companyName = companyName;
		this.location = location;
		this.employmentType = employmentType;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.skills = skills;
		this.bookmarked = bookmarked;
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


	


	public String getEmploymentType() {
		return employmentType;
	}


	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
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


	public boolean isBookmarked() {
		return bookmarked;
	}


	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}
	
	
	

	 
}
