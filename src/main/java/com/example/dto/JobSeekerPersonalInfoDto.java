package com.example.dto;

public class JobSeekerPersonalInfoDto {

	private String profileImageUrl;
	private String fullName;
	private String city;
	private String state;
	private String country;
	
	private String resumeUrl;
	private String introVideoUrl;
	
	public JobSeekerPersonalInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerPersonalInfoDto(String profileImageUrl, String city, String state, String country, String resumeUrl,
			String introVideoUrl) {
		super();
		this.profileImageUrl = profileImageUrl;
		this.city = city;
		this.state = state;
		this.country = country;
		this.resumeUrl = resumeUrl;
		this.introVideoUrl = introVideoUrl;
		this.fullName=fullName;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
}
