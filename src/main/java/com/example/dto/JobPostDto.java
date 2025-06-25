package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

public class JobPostDto {

    private Integer id;

    
    private String title;

   
    private String description;

    
    private String location;

    
    private double minSalary;
    private double maxSalary;

   
    private String employmentType;

   
    private String minExperience;
    private String maxExperience;

    
    

	private LocalDate lastDateToApply;

    private LocalDate postedDate;

    
    private String jobCategory;

   
    private int numberOfOpenings;

    
    private String companyName;

   
    private String jobType;

    
    private String workLocation;

    
    private String gender;

    
    
    private List<String> skills;

    
    private String jobShift;

    
    private String education;

    private List<Integer> applicants;
    
    private boolean closed; 
    
    private List<String> benefits;

    public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public JobPostDto() {
        super();
    }

    public JobPostDto(Integer id, String title, String description, String location, double minSalary,double maxSalary, String employmentType,
                      String minExperience,String maxExperience, LocalDate lastDateToApply, LocalDate postedDate, String jobCategory,
                      int numberOfOpenings, String companyName, String jobType, String workLocation, String gender,
                      String requiredExperience, List<String> skills, String jobShift, String education, List<Integer> applicants, boolean closed, List<String> benefits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary=maxSalary;
        this.employmentType = employmentType;
        this.minExperience = minExperience;
        this.maxExperience=maxExperience;
        this.lastDateToApply = lastDateToApply;
        this.postedDate = postedDate;
        this.jobCategory = jobCategory;
        this.numberOfOpenings = numberOfOpenings;
        this.companyName = companyName;
        this.jobType = jobType;
        this.workLocation = workLocation;
        this.gender = gender;
        this.skills = skills;
        this.jobShift = jobShift;
        this.benefits=benefits;
        this.education = education;
        this.applicants=applicants;
        this.closed=closed;
        
    }

    // Getters and Setters

    // ID
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    // Title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    // Description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Location
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    
    // Employment Type
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

   

    // Last Date To Apply
    public LocalDate getLastDateToApply() { return lastDateToApply; }
    public void setLastDateToApply(LocalDate lastDateToApply) { this.lastDateToApply = lastDateToApply; }

    // Posted Date
    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }

    // Job Category
    public String getJobCategory() { return jobCategory; }
    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    // Number Of Openings
    public int getNumberOfOpenings() { return numberOfOpenings; }
    public void setNumberOfOpenings(int numberOfOpenings) { this.numberOfOpenings = numberOfOpenings; }

    // Company Name
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    // Job Type
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    // Work Location
    public String getWorkLocation() { return workLocation; }
    public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }

    // Gender
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    
    // Skills
    public void setSkills(List<String> skills) {
		this.skills = skills;
	}
    public List<String> getSkills() {
		return skills;
	}

    
    // Job Shift
    public String getJobShift() { return jobShift; }
    
	public void setJobShift(String jobShift) { this.jobShift = jobShift; }

    // Education
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    // Applicants
    public List<Integer> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Integer> applicants) {
        this.applicants = applicants;
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

	public List<String> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<String> benefits) {
		this.benefits = benefits;
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
    

}
