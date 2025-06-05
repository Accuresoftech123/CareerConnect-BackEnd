package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.RecruiterProfileDto;
import com.example.entity.Recruiter;
import com.example.enums.Status;
import com.example.repository.RecruiterRepository;

/**
 * Service class for handling Recruiter-related business logic.
 * Acts as an intermediary between the controller and repository layers.
 */
@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    /**
     * Registers a new recruiter.
     * Checks for duplicate email before saving.
     *
     * @param newRecruiter the recruiter to be registered
     * @return registration status message
     */
    public String register(Recruiter newRecruiter) {
        Recruiter existing = recruiterRepository.findByEmail(newRecruiter.getEmail()).orElse(null);
        if (existing != null) {
            return "Email already registered";
        }

        // Set default status as PENDING before saving
        newRecruiter.setStatus(Status.PENDING);
        recruiterRepository.save(newRecruiter);
        return "Registration successful";
    }

    /**
     * Validates recruiter credentials for login.
     *
     * @param email    email of the recruiter
     * @param password password of the recruiter
     * @return recruiter object if valid, otherwise null
     */
    public ResponseEntity<?> login(String email, String password) {
        Recruiter existing = recruiterRepository.findByEmail(email).orElse(null);
        if (existing != null && existing.getPassword().equals(password)) {
        	
        	 if (existing.getStatus() == Status.APPROVED) {
                 return ResponseEntity.ok(existing);
             }else {
            	 return ResponseEntity.ok("Access Denied.Your application status is "+existing.getStatus());
             }
          
        }
       
        return null;
    }

    /**
     * Updates the profile details of a recruiter.
     *
     * @param id  the recruiter ID
     * @param dto the profile data
     * @return update status message
     */
    public String updateProfile(int id, RecruiterProfileDto dto) {
        Recruiter recruiter = recruiterRepository.findById(id).orElse(null);
        if (recruiter == null) {
            return "Recruiter not found";
        }

        if (isProfileDataEmpty(dto)) {
            return "Invalid profile data";
        }

        // Set non-null fields
        if (dto.getCompanyName() != null) recruiter.setCompanyName(dto.getCompanyName());
        if (dto.getCompanyAddress() != null) recruiter.setCompanyAddress(dto.getCompanyAddress());
        if (dto.getCompanyDescription() != null) recruiter.setCompanyDescription(dto.getCompanyDescription());
        if (dto.getCompanyWebsiteUrl() != null) recruiter.setCompanyWebsiteUrl(dto.getCompanyWebsiteUrl());
        if (dto.getNumberOfEmployees() > 0) recruiter.setNumberOfEmployees(dto.getNumberOfEmployees());
        if (dto.getFirstName() != null) recruiter.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) recruiter.setLastName(dto.getLastName());
        if (dto.getCity() != null) recruiter.setCity(dto.getCity());
        if (dto.getIndustryType() != null) recruiter.setIndustryType(dto.getIndustryType());
        if (dto.getPhoneNumber() > 0) recruiter.setPhoneNumber(dto.getPhoneNumber());

        recruiterRepository.save(recruiter);
        return "Profile updated successfully";
    }

    /**
     * Checks whether all profile fields in the DTO are empty.
     *
     * @param dto the profile DTO
     * @return true if all fields are empty; false otherwise
     */
    private boolean isProfileDataEmpty(RecruiterProfileDto dto) {
        return dto.getCompanyName() == null &&
               dto.getCompanyAddress() == null &&
               dto.getCompanyDescription() == null &&
               dto.getCompanyWebsiteUrl() == null &&
               dto.getNumberOfEmployees() <= 0 &&
               dto.getFirstName() == null &&
               dto.getLastName() == null &&
               dto.getCity() == null&&
            		   dto.getIndustryType() == null &&
            				   dto.getPhoneNumber() == 0;
        
    }
}
