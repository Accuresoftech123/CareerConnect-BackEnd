package com.example.entity.profile;



import java.util.List;

import com.example.entity.JobSeeker;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
	
	@ElementCollection
	@CollectionTable(name = "job_preference_desired_titles", joinColumns = @JoinColumn(name = "job_preference_id"))
	@Column(name = "desired_job_title")
	private List<String> desiredJobTitle;

	
	
	@ElementCollection
	@CollectionTable(name = "job_preference_job_types", joinColumns = @JoinColumn(name = "job_preference_id"))
	@Column(name = "job_type")
	private List<String> jobTypes;

	private Double expectedSalary;
	private String preferredLocation;
	
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "job_seeker_id")
	private JobSeeker jobSeeker;


	public JobPreferences() {
		super();
		// TODO Auto-generated constructor stub
	}


	public JobPreferences(long id, List<String> desiredJobTitle, List<String> jobType, Double expectedSalary,
			String preferredLocation, JobSeeker jobSeeker) {
		super();
		this.id = id;
		this.desiredJobTitle = desiredJobTitle;
		this.jobTypes = jobType;
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

	public List<String> getDesiredJobTitle() {
		return desiredJobTitle;
	}


	public void setDesiredJobTitle(List<String> desiredJobTitle) {
		this.desiredJobTitle = desiredJobTitle;
	}


	public List<String> getJobTypes() {
		return jobTypes;
	}


	public void setJobTypes(List<String> jobTypes) {
		this.jobTypes = jobTypes;
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
