package com.example.dto;

/**
 * DTO (Data Transfer Object) for handling recruiter login requests.
 * This class contains the necessary fields for recruiter authentication,
 * specifically the email and password.
 */
public class RecruiterLoginDto {

    // Email address of the recruiter used for login
    private String email;

    // Password of the recruiter for authentication
    private String password;

    /**
     * Default no-argument constructor.
     */
    public RecruiterLoginDto() {
        super();
        // Default constructor
    }

    /**
     * Parameterized constructor to initialize the email and password fields.
     *
     * @param email    Recruiter's email address
     * @param password Recruiter's password
     */
    public RecruiterLoginDto(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the email address.
     *
     * @return email of the recruiter
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email to set for the recruiter
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return password of the recruiter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set for the recruiter
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
