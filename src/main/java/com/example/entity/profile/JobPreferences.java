package com.example.entity.profile;



import com.example.entity.JobSeeker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class JobPreferences {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String desiredJobTitle;
	private String jobType;
	private Double expectedSalary;
	private String preferredLocation;
	
	
	@OneToOne
	@JoinColumn(name = "job_seeker_id")
	private JobSeeker jobSeeker;


	public JobPreferences() {
		super();
		// TODO Auto-generated constructor stub
	}


	public JobPreferences(long id, String desiredJobTitle, String jobType, Double expectedSalary,
			String preferredLocation, JobSeeker jobSeeker) {
		super();
		this.id = id;
		this.desiredJobTitle = desiredJobTitle;
		this.jobType = jobType;
		this.expectedSalary = expectedSalary;
		this.preferredLocation = preferredLocation;
		this.jobSeeker = jobSeeker;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDesiredJobTitle() {
		return desiredJobTitle;
	}


	public void setDesiredJobTitle(String desiredJobTitle) {
		this.desiredJobTitle = desiredJobTitle;
	}


	public String getJobType() {
		return jobType;
	}


	public void setJobType(String jobType) {
		this.jobType = jobType;
	}


	public Double getExpectedSalary() {
		return expectedSalary;
	}


	public void setExpectedSalary(Double expectedSalary) {
		this.expectedSalary = expectedSalary;
	}


	public String getPreferredLocation() {
		return preferredLocation;
	}


	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}


	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}


	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}
	
	
	

}
