package com.example.entity.jobposting;

import jakarta.persistence.*;

import java.time.LocalDate;
import com.example.entity.Recruiter;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity representing a job post created by a recruiter.
 */
@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    private String title;

   
    private String description;

    
    private String location;

    
    private double salary;

    
    private String employmentType;

   
    private String experience;

    
    private LocalDate lastDateToApply;

    private LocalDate postedDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiter recruiter;

    // New Fields Added
    private String jobCategory;
    private int numberOfOpenings;
    private String companyName;
    private String jobType;
    private String workLocation;
    private String gender;
    private String requiredExperience;
    private String skills;
    private String jobShift;
    private String education;
    private int applicants;

    @PrePersist
    protected void onCreate() {
        this.postedDate = LocalDate.now();
        this.applicants = 0; // Default value
    }

    public JobPost() {
        super();
    }

    // All Args Constructor
    public JobPost(int id, String title, String description, String location, double salary, String employmentType,
                   String experience, LocalDate lastDateToApply, LocalDate postedDate, Recruiter recruiter,
                   String jobCategory, int numberOfOpenings, String companyName, String jobType, String workLocation,
                   String gender, String requiredExperience, String skills, String jobShift, String education, int applicants) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.employmentType = employmentType;
        this.experience = experience;
        this.lastDateToApply = lastDateToApply;
        this.postedDate = postedDate;
        this.recruiter = recruiter;
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
        this.applicants = applicants;
    }

    // Getters and Setters for all fields

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public LocalDate getLastDateToApply() { return lastDateToApply; }
    public void setLastDateToApply(LocalDate lastDateToApply) { this.lastDateToApply = lastDateToApply; }

    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }

    public Recruiter getRecruiter() { return recruiter; }
    public void setRecruiter(Recruiter recruiter) { this.recruiter = recruiter; }

    public String getJobCategory() { return jobCategory; }
    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    public int getNumberOfOpenings() { return numberOfOpenings; }
    public void setNumberOfOpenings(int numberOfOpenings) { this.numberOfOpenings = numberOfOpenings; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getWorkLocation() { return workLocation; }
    public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getRequiredExperience() { return requiredExperience; }
    public void setRequiredExperience(String requiredExperience) { this.requiredExperience = requiredExperience; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getJobShift() { return jobShift; }
    public void setJobShift(String jobShift) { this.jobShift = jobShift; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public int getApplicants() { return applicants; }
    public void setApplicants(int applicants) { this.applicants = applicants; }
}
