package com.example.entity.profile;

import com.example.entity.JobSeeker;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class SocialProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String linkedinUrl;
	private String githubUrl;
	private String portfolioWebsite;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "job_seeker_id")
	private JobSeeker jobSeeker;

	public SocialProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SocialProfile(long id, String linkedinUrl, String githubUrl, String portfolioWebsite, JobSeeker jobSeeker) {
		super();
		this.id = id;
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.portfolioWebsite = portfolioWebsite;
		this.jobSeeker = jobSeeker;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getPortfolioWebsite() {
		return portfolioWebsite;
	}

	public void setPortfolioWebsite(String portfolioWebsite) {
		this.portfolioWebsite = portfolioWebsite;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}
	
	

}
