package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Job Seeker Profile.
 * Used to transfer job seeker profile data between layers.
 */
public class JobSeekerProfileDto {
	
	// Unique identifier for the job seeker profile
	private int id;
	
	// Job seeker's first name
	private String firstName;
	
	// Job seeker's last name
	private String lastName;
	
	// Job seeker's email address
	private String email;
	
	// Job seeker's mobile phone number as String (to accommodate formatting)
	private String mobileNumber;
	
	// Residential address of the job seeker
	private String address;
	
	// Gender of the job seeker (e.g., Male, Female, Other)
	private String gender;
	
	// Date of birth of the job seeker
	private LocalDate dateOfBirth;
	
	// Brief summary or introduction of the job seeker's profile
	private String profileSummary;
	
	// Highest education qualification (e.g., B.Tech, MBA, etc.)
	private String highestEducationQualification;
	
	// Year of passing from the highest education institution
	private String yearOfPassing;
	
	// Name of the college/university attended
	private String collegeName;
	
	// Skills possessed by the job seeker (comma-separated or formatted string)
	private String skills;
	
	// Total years of professional experience
	private int yearsOfExperience;
	
	// URL or path to the job seeker's uploaded resume
	private String resumeUrl;
	
	// URL to the job seeker's GitHub profile (if any)
	private String githubProfileUrl;
	
	// URL or path to the job seeker's profile image
	private String profileImageUrl;
	
	// Preferred location(s) where the job seeker wants to work
	private String preferredJobLocation;
	
	// Notice period the job seeker has to serve before joining a new job
	private String noticePeriod;
	
	// Current salary (Cost to Company) of the job seeker
	private Double currentCtc;
	
	// Expected salary (Cost to Company) from the new job
	private Double expectedCtc;
	
	// Flag to indicate if the profile is marked as complete
	private boolean profileComplete;
	
	// Timestamp when the profile was created
	private LocalDateTime createdAt;
	
	// Timestamp when the profile was last updated
	private LocalDateTime updatedAt;

	/**
	 * Default no-argument constructor.
	 */
	public JobSeekerProfileDto() {
		super();
		// Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public JobSeekerProfileDto(int id, String firstName, String lastName, String email, String mobileNumber,
			String address, String gender, LocalDate dateOfBirth, String profileSummary,
			String highestEducationQualification, String yearOfPassing, String collegeName, String skills,
			int yearsOfExperience, String resumeUrl, String githubProfileUrl, String profileImageUrl,
			String preferredJobLocation, String noticePeriod, Double currentCtc, Double expectedCtc,
			boolean profileComplete, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.profileSummary = profileSummary;
		this.highestEducationQualification = highestEducationQualification;
		this.yearOfPassing = yearOfPassing;
		this.collegeName = collegeName;
		this.skills = skills;
		this.yearsOfExperience = yearsOfExperience;
		this.resumeUrl = resumeUrl;
		this.githubProfileUrl = githubProfileUrl;
		this.profileImageUrl = profileImageUrl;
		this.preferredJobLocation = preferredJobLocation;
		this.noticePeriod = noticePeriod;
		this.currentCtc = currentCtc;
		this.expectedCtc = expectedCtc;
		this.profileComplete = profileComplete;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and setters for all the above fields

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfileSummary() {
		return profileSummary;
	}

	public void setProfileSummary(String profileSummary) {
		this.profileSummary = profileSummary;
	}

	public String getHighestEducationQualification() {
		return highestEducationQualification;
	}

	public void setHighestEducationQualification(String highestEducationQualification) {
		this.highestEducationQualification = highestEducationQualification;
	}

	public String getYearOfPassing() {
		return yearOfPassing;
	}

	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public String getGithubProfileUrl() {
		return githubProfileUrl;
	}

	public void setGithubProfileUrl(String githubProfileUrl) {
		this.githubProfileUrl = githubProfileUrl;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getPreferredJobLocation() {
		return preferredJobLocation;
	}

	public void setPreferredJobLocation(String preferredJobLocation) {
		this.preferredJobLocation = preferredJobLocation;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Double getCurrentCtc() {
		return currentCtc;
	}

	public void setCurrentCtc(Double currentCtc) {
		this.currentCtc = currentCtc;
	}

	public Double getExpectedCtc() {
		return expectedCtc;
	}

	public void setExpectedCtc(Double expectedCtc) {
		this.expectedCtc = expectedCtc;
	}

	public boolean isProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(boolean profileComplete) {
		this.profileComplete = profileComplete;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
