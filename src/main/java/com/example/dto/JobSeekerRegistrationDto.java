package com.example.dto;

import java.time.LocalDate;

import jakarta.persistence.Transient;

/**
 * Data Transfer Object (DTO) class used for job seeker registration.
 * This class holds the information submitted by a job seeker during registration.
 */
public class JobSeekerRegistrationDto {

    // Basic personal information fields
    private String firstName;            // Job seeker's first name
    private String lastName;             // Job seeker's last name
    private String email;                // Job seeker's email address
    private String mobileNumber;         // Job seeker's contact mobile number
    private String password;             // Password for the account

    /**
     * Transient field not persisted in the database,
     * used only for password confirmation during registration.
     */
    @Transient
    private String confirmPassword;     // Confirm password field to validate password input

    // Additional personal details
    private String address;              // Job seeker's residential address
    private String gender;               // Gender of the job seeker
    private LocalDate dateOfBirth;      // Date of birth of the job seeker

    // Professional and educational details
    private String profileSummary;                  // Brief summary/profile description
    private String highestEducationQualification;  // Highest education qualification (e.g., B.Tech, MCA)
    private String yearOfPassing;                   // Year of passing the highest qualification
    private String collegeName;                     // Name of the college/university
    private String skills;                          // Skills of the job seeker, e.g., Java, Spring Boot
    private int yearsOfExperience;                  // Total years of professional experience

    // URLs for resumes and profiles
    private String resumeUrl;               // Link to uploaded resume document
    private String githubProfileUrl;       // GitHub profile URL for technical portfolio

    private String profileImageUrl;        // URL to the profile picture/image
    private String preferredJobLocation;   // Preferred job location by the job seeker
    private String noticePeriod;            // Notice period in current job (e.g., 30 days)

    // Salary details
    private Double currentCtc;              // Current Cost to Company (salary)
    private Double expectedCtc;             // Expected salary by the job seeker

    // Flag indicating if profile is complete or not
    private boolean profileComplete;

    /**
     * Default no-argument constructor.
     */
    public JobSeekerRegistrationDto() {
        super();
        // No special initialization required here
    }

    /**
     * Parameterized constructor to initialize all fields of the DTO.
     */
    public JobSeekerRegistrationDto(String firstName, String lastName, String email, String mobileNumber,
            String password, String confirmPassword, String address, String gender, LocalDate dateOfBirth,
            String profileSummary, String highestEducationQualification, String yearOfPassing, String collegeName,
            String skills, int yearsOfExperience, String resumeUrl, String githubProfileUrl, String profileImageUrl,
            String preferredJobLocation, String noticePeriod, Double currentCtc, Double expectedCtc,
            boolean profileComplete){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
    }

    // Getter and Setter methods for all fields below
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

}
