package com.example.entity;

import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.enums.ApplicationStatus;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    private String coverLetter;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    private LocalDateTime applicationDate;
    
    private String resumeFilePath;
    
    // Additional fields recruiter might need
    private String expectedSalary;
    private String availability;
    private String notes; // For recruiter's internal use
    
    

    public Applicant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Applicant(int id, JobPost jobPost, JobSeeker jobSeeker, String coverLetter, ApplicationStatus status,
			LocalDateTime applicationDate, String resumeFilePath, String expectedSalary, String availability,
			String notes) {
		super();
		this.id = id;
		this.jobPost = jobPost;
		this.jobSeeker = jobSeeker;
		this.coverLetter = coverLetter;
		this.status = status;
		this.applicationDate = applicationDate;
		this.resumeFilePath = resumeFilePath;
		this.expectedSalary = expectedSalary;
		this.availability = availability;
		this.notes = notes;
	}

	public JobPost getJobPost() {
		return jobPost;
	}

	public void setJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getResumeFilePath() {
		return resumeFilePath;
	}

	public void setResumeFilePath(String resumeFilePath) {
		this.resumeFilePath = resumeFilePath;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
        this.status = ApplicationStatus.SUBMITTED;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
}


