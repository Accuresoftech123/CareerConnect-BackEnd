package com.example.dto;

public class JobSeekerSocialProfileDto {

	private String linkedinUrl;
	private String githubUrl;
	private String portfolioWebsite;
	public JobSeekerSocialProfileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerSocialProfileDto(String linkedinUrl, String githubUrl, String portfolioWebsite) {
		super();
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.portfolioWebsite = portfolioWebsite;
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
	
	
}
