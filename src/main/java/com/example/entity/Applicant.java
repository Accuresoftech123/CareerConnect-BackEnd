package com.example.entity;

import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.entity.profile.Education;
import com.example.entity.profile.Experience;
import com.example.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    private String coverLetter;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    private LocalDateTime applicationDate;
    
    private String resumeFilePath;
    
    
  
  
   
    private String jobPostTitle;
    private String jobPostLocation;
    
    // JobSeeker info
    private String fullName;
    private String email;
    private String mobileNumber;
    private List<String> skills;
    
   
    
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ApplicantEducation> educationList = new ArrayList<>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ApplicantExperience> experienceList = new ArrayList<>();

    
    
    
    
    

    

	public Applicant(int id,String fullName, String email, String mobileNumber, List<String> skills, List<Education> educationList, JobPost jobPost,List<Experience> experienceList, JobSeeker jobSeeker, String coverLetter, ApplicationStatus status,
			LocalDateTime applicationDate, String resumeFilePath,
			String jobPostTitle, String jobPostLocation) {
		super();
		this.id = id;
		this.jobPost = jobPost;
		this.jobSeeker = jobSeeker;
		this.coverLetter = coverLetter;
		this.status = status;
		this.applicationDate = applicationDate;
		this.resumeFilePath = resumeFilePath;
		
		this.jobPostTitle = jobPostTitle;
		this.jobPostLocation = jobPostLocation;
	}

	public Applicant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getJobPostTitle() {
		return jobPostTitle;
	}

	public void setJobPostTitle(String jobPostTitle) {
		this.jobPostTitle = jobPostTitle;
	}

	public String getJobPostLocation() {
		return jobPostLocation;
	}

	public void setJobPostLocation(String jobPostLocation) {
		this.jobPostLocation = jobPostLocation;
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

	

	

	@PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
        this.status = ApplicationStatus.SUBMITTED;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<ApplicantEducation> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<ApplicantEducation> educationList) {
		this.educationList = educationList;
	}

	public List<ApplicantExperience> getExperienceList() {
		return experienceList;
	}

	public void setExperienceList(List<ApplicantExperience> experienceList) {
		this.experienceList = experienceList;
	}

	
    
}


