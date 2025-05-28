package com.example.dto;

/**
 * DTO (Data Transfer Object) used for generating reports related to Recruiters.
 * Contains summary details about a recruiter and their company.
 */
public class RecruiterReportDto {

    private int id;                         // Unique identifier for the recruiter
    private String companyName;            // Name of the company
    private String city;                   // City where the company is located
    private String companyWebsiteUrl;      // Company's official website URL
    private int numberOfEmployees;         // Total number of employees in the company

    // Recruiter's personal details
    private String firstName;              // First name of the recruiter
    private String lastName;               // Last name of the recruiter

    /**
     * Default constructor.
     */
    public RecruiterReportDto() {
        super();
        // Used by frameworks or for manual object creation
    }

    /**
     * Parameterized constructor to initialize all fields of RecruiterReportDto.
     *
     * @param id                  Unique recruiter ID
     * @param companyName         Name of the recruiter’s company
     * @param city                Location of the company
     * @param companyWebsiteUrl   Website of the company
     * @param numberOfEmployees   Number of employees in the company
     * @param firstName           Recruiter’s first name
     * @param lastName            Recruiter’s last name
     */
    public RecruiterReportDto(int id, String companyName, String city, String companyWebsiteUrl, 
                              int numberOfEmployees, String firstName, String lastName) {
        super();
        this.id = id;
        this.companyName = companyName;
        this.city = city;
        this.companyWebsiteUrl = companyWebsiteUrl;
        this.numberOfEmployees = numberOfEmployees;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyWebsiteUrl() {
        return companyWebsiteUrl;
    }

    public void setCompanyWebsiteUrl(String companyWebsiteUrl) {
        this.companyWebsiteUrl = companyWebsiteUrl;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
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
}
