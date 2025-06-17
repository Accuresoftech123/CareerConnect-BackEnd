package com.example.entity.profile;

import com.example.entity.Recruiter;
import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_social_profiles")
public class RecruiterSocialProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    
    @Column(name = "github_url")
    private String githubUrl;
    
    @Column(name = "portfolio_website")
    private String portfolioWebsite;
    
    @Column(name = "twitter_url")
    private String twitterUrl;
    
    @Column(name = "facebook_url")
    private String facebookUrl;
    
    @Column(name = "other_url")
    private String otherUrl;
    
    @OneToOne
    @JoinColumn(name = "recruiter_id", unique = true)
    private Recruiter recruiter;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
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

	// Constructors
    public RecruiterSocialProfile() {
    }

    public RecruiterSocialProfile(String linkedinUrl, String githubUrl, String portfolioWebsite, 
                                String twitterUrl, String facebookUrl, String otherUrl) {
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.portfolioWebsite = portfolioWebsite;
        this.twitterUrl = twitterUrl;
        this.facebookUrl = facebookUrl;
        this.otherUrl = otherUrl;
    }

   
}