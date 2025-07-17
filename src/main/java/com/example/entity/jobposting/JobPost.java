package com.example.entity.jobposting;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.Recruiter;
import com.example.enums.JobPostStatus;
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

	@Column(length = 5000)
	private String description;

	private String location;

	private String employmentType;

	private String minExperience;
	private String maxExperience;

	private LocalDate lastDateToApply;

	private LocalDate postedDate;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "recruiter_id", nullable = false)
	private Recruiter recruiter;

	
	private int numberOfOpenings;
	

	@ElementCollection
	private List<String> skills;
	

	private double minSalary;

	private double maxSalary;
	@ElementCollection
	private List<String> benefits;

	@Transient
	private boolean prefillRequest;

	@Transient
	private Integer prefillFromJobId;

	@PrePersist
	protected void onCreate() {
		this.postedDate = LocalDate.now();
		// this.applicants = 0; // Default value
	}

	@Enumerated(EnumType.STRING)
	private JobPostStatus status = JobPostStatus.OPEN;

	public JobPostStatus getStatus() {
		return status;
	}

	public void setStatus(JobPostStatus status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
	private List<SavedJob> savedJobs;

	public JobPost() {
		super();
	}

	// All Args Constructor
	public JobPost(int id, String title, String description, String location, String employmentType,
			String minExperience, String maxExperience, LocalDate lastDateToApply, LocalDate postedDate,
			Recruiter recruiter, int numberOfOpenings,

			JobPostStatus status, double minSalary, double maxSalary, List<String> skills, List<String> benefits,
			List<SavedJob> savedJobs, boolean prefillRequest, Integer prefillFromJobId) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.employmentType = employmentType;
		this.minExperience = minExperience;
		this.maxExperience = maxExperience;
		this.lastDateToApply = lastDateToApply;
		this.postedDate = postedDate;
		this.recruiter = recruiter;

		this.numberOfOpenings = numberOfOpenings;

		// this.jobType = jobType;

		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.skills = skills;
		this.benefits = benefits;

		this.status = status;

		this.savedJobs = savedJobs;
		this.prefillRequest = prefillRequest;
		this.prefillFromJobId = prefillFromJobId;

	}

	// Getters and Setters for all fields

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public LocalDate getLastDateToApply() {
		return lastDateToApply;
	}

	public void setLastDateToApply(LocalDate lastDateToApply) {
		this.lastDateToApply = lastDateToApply;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	public int getNumberOfOpenings() {
		return numberOfOpenings;
	}

	public void setNumberOfOpenings(int numberOfOpenings) {
		this.numberOfOpenings = numberOfOpenings;
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

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<String> getSkills() {
		return skills;
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

	public List<SavedJob> getSavedJobs() {
		return savedJobs;
	}

	public void setSavedJobs(List<SavedJob> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public boolean isPrefillRequest() {
		return prefillRequest;
	}

	public void setPrefillRequest(boolean prefillRequest) {
		this.prefillRequest = prefillRequest;
	}

	public Integer getPrefillFromJobId() {
		return prefillFromJobId;
	}

	public void setPrefillFromJobId(Integer prefillFromJobId) {
		this.prefillFromJobId = prefillFromJobId;
	}

}
