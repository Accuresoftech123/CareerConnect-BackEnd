package com.example.entity.profile;

import java.time.LocalDate;

import com.example.entity.JobSeeker;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Experience {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String jobTitle;
	private String companyName;
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	@Column(length = 1000)
	private String keyResponsibilities;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "job_seeker_id")
	private JobSeeker jobSeeker;

	public Experience() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Experience(long id, String jobTitle, String companyName, LocalDate startDate, LocalDate endDate,
			String keyResponsibilities, JobSeeker jobSeeker) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.keyResponsibilities = keyResponsibilities;
		this.jobSeeker = jobSeeker;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getKeyResponsibilities() {
		return keyResponsibilities;
	}

	public void setKeyResponsibilities(String keyResponsibilities) {
		this.keyResponsibilities = keyResponsibilities;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}
	
	
	

}
