package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

public class JobPostDto {

    private Integer id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @PositiveOrZero(message = "Salary must be zero or positive")
    private double salary;

    @NotBlank(message = "Employment Type is required")
    private String employmentType;

    @NotBlank(message = "Experience is required")
    private String experience;

    @NotNull(message = "Last date to apply is required")
    private LocalDate lastDateToApply;

    private LocalDate postedDate;

    @NotBlank(message = "Job Category is required")
    private String jobCategory;

    @PositiveOrZero(message = "Number of openings must be zero or positive")
    private int numberOfOpenings;

    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Job Type is required")
    private String jobType;

    @NotBlank(message = "Work Location is required")
    private String workLocation;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Required Experience is required")
    private String requiredExperience;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Job Shift is required")
    private String jobShift;

    @NotBlank(message = "Education is required")
    private String education;

    private List<Integer> applicants;
    
    private boolean closed; 

    public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public JobPostDto() {
        super();
    }

    public JobPostDto(Integer id, String title, String description, String location, double salary, String employmentType,
                      String experience, LocalDate lastDateToApply, LocalDate postedDate, String jobCategory,
                      int numberOfOpenings, String companyName, String jobType, String workLocation, String gender,
                      String requiredExperience, String skills, String jobShift, String education, List<Integer> applicants, boolean closed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.employmentType = employmentType;
        this.experience = experience;
        this.lastDateToApply = lastDateToApply;
        this.postedDate = postedDate;
        this.jobCategory = jobCategory;
        this.numberOfOpenings = numberOfOpenings;
        this.companyName = companyName;
        this.jobType = jobType;
        this.workLocation = workLocation;
        this.gender = gender;
        this.requiredExperience = requiredExperience;
        this.skills = skills;
        this.jobShift = jobShift;
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

    // Salary
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    // Employment Type
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    // Experience
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

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

    // Required Experience
    public String getRequiredExperience() { return requiredExperience; }
    public void setRequiredExperience(String requiredExperience) { this.requiredExperience = requiredExperience; }

    // Skills
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

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

}
