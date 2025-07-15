package com.example.entity.profile;

import com.example.entity.JobSeeker;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class JobSeekerPersonalInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String profileImageUrl;
	private String city;
	private String state;
	private String country;
	
	@Lob
	@Column(name = "resume_url", columnDefinition = "LONGTEXT")
	private String resumeUrl;
	
	@Lob
	@Column(name = "intro_video_url", columnDefinition = "LONGTEXT")
	private String introVideoUrl;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "job_seeker_id")
	private JobSeeker jobSeeker;

	public JobSeekerPersonalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobSeekerPersonalInfo(Long id, String img, String city, String state, String country, String resumeUrl,
			String introVideoUrl, JobSeeker jobSeeker) {
		super();
		this.id = id;
		this.profileImageUrl = img;
		this.city = city;
		this.state = state;
		this.country = country;
		this.resumeUrl = resumeUrl;
		this.introVideoUrl = introVideoUrl;
		this.jobSeeker = jobSeeker;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public String getIntroVideoUrl() {
		return introVideoUrl;
	}

	public void setIntroVideoUrl(String introVideoUrl) {
		this.introVideoUrl = introVideoUrl;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}
	
	

}
