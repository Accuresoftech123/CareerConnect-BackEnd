package com.example.dto;

/**
 * Data Transfer Object (DTO) for Job Seeker Login.
 * Encapsulates login credentials: email and password.
 */
public class JobSeekerLoginDto {

    private String email;
    private String password;

    // Default constructor
    public JobSeekerLoginDto() {
    }

    // Parameterized constructor
    public JobSeekerLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
