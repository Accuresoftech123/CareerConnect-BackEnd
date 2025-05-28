package com.example.dto;

/**
 * Data Transfer Object (DTO) to represent a Job Seeker's report data.
 * This class is typically used to transfer data between layers
 * or to display detailed information about a job seeker.
 */
public class JobSeekerReportDto {

    // Unique identifier for the job seeker
    private int id;

    // Personal details
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String gender;
    private String dateOfBirth;

    // Professional and educational details
    private String skills;
    private String address;
    private String collegeName;
    private String highestEducationQualification;
    private String yearOfPassing;
    private String yearsOfExperience;

    // URLs for external profiles and resources
    private String githubProfileUrl;
    private String profileImageUrl;
    private String resumeUrl;

    // Job preferences and additional info
    private String noticePeriod;
    private String preferredJobLocation;
    private String profileSummary;

    /**
     * Default no-argument constructor.
     */
    public JobSeekerReportDto() {
        super();
        // Default constructor stub
    }

    /**
     * Parameterized constructor to initialize all fields.
     *
     * @param id                          Job seeker ID
     * @param firstName                   First name
     * @param lastName                    Last name
     * @param email                       Email address
     * @param skills                      Skills possessed by the job seeker
     * @param address                     Residential address
     * @param collegeName                 College name attended
     * @param gender                      Gender of the job seeker
     * @param dateOfBirth                 Date of birth as String
     * @param githubProfileUrl            URL of GitHub profile
     * @param highestEducationQualification Highest education qualification
     * @param yearOfPassing               Year of passing out
     * @param yearsOfExperience           Total years of experience
     * @param mobileNumber                Mobile contact number
     * @param noticePeriod                Notice period required before joining
     * @param preferredJobLocation        Preferred job location
     * @param profileImageUrl             URL of profile image
     * @param profileSummary              Summary/profile description
     * @param resumeUrl                   URL to the resume document
     */
    public JobSeekerReportDto(int id, String firstName, String lastName, String email, String skills,
                               String address, String collegeName, String gender, String dateOfBirth,
                               String githubProfileUrl, String highestEducationQualification, String yearOfPassing,
                               String yearsOfExperience, String mobileNumber, String noticePeriod,
                               String preferredJobLocation, String profileImageUrl, String profileSummary,
                               String resumeUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skills = skills;
        this.address = address;
        this.collegeName = collegeName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.githubProfileUrl = githubProfileUrl;
        this.highestEducationQualification = highestEducationQualification;
        this.yearOfPassing = yearOfPassing;
        this.yearsOfExperience = yearsOfExperience;
        this.mobileNumber = mobileNumber;
        this.noticePeriod = noticePeriod;
        this.preferredJobLocation = preferredJobLocation;
        this.profileImageUrl = profileImageUrl;
        this.profileSummary = profileSummary;
        this.resumeUrl = resumeUrl;
    }

    // Getters and Setters with brief comments

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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGithubProfileUrl() {
        return githubProfileUrl;
    }

    public void setGithubProfileUrl(String githubProfileUrl) {
        this.githubProfileUrl = githubProfileUrl;
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

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getPreferredJobLocation() {
        return preferredJobLocation;
    }

    public void setPreferredJobLocation(String preferredJobLocation) {
        this.preferredJobLocation = preferredJobLocation;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}
