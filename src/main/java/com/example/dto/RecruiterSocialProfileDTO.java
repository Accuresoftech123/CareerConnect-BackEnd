package com.example.dto;





public class RecruiterSocialProfileDTO {
    private Long id;
    
    
    private String linkedinUrl;
    
    
    private String githubUrl;
    
    
    private String portfolioWebsite;
    
    
    private String twitterUrl;
    
    
    private String facebookUrl;
    
    
    private String otherUrl;


	public RecruiterSocialProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RecruiterSocialProfileDTO(Long id, String linkedinUrl, String githubUrl, String portfolioWebsite,
			String twitterUrl, String facebookUrl, String otherUrl) {
		super();
		this.id = id;
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.portfolioWebsite = portfolioWebsite;
		this.twitterUrl = twitterUrl;
		this.facebookUrl = facebookUrl;
		this.otherUrl = otherUrl;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public String getTwitterUrl() {
		return twitterUrl;
	}


	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}


	public String getFacebookUrl() {
		return facebookUrl;
	}


	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}


	public String getOtherUrl() {
		return otherUrl;
	}


	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}
}