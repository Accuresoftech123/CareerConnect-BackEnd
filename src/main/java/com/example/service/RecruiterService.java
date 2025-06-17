package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.ApplicantDTO;
import com.example.dto.RecruiterProfileDto;
import com.example.dto.RecruiterRegistrationDto;
import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.entity.profile.CompanyProfile;
import com.example.enums.ApplicationStatus;
import com.example.enums.Status;
import com.example.exception.RecruiterNotFoundException;
import com.example.repository.ApplicantRepository;
import com.example.repository.RecruiterRepository;

import jakarta.transaction.Transactional;

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
    public String register(RecruiterRegistrationDto newRecruiter) {
		Optional<Recruiter> existing = recruiterRepository.findByEmail(newRecruiter.getEmail());

        
        if (existing == null) {
            return "Email already registered";
        }

        // Set default status as PENDING before saving
        newRecruiter.setStatus(Status.APPROVED);
        Recruiter recruiter = new Recruiter();
        recruiter.setFullName(newRecruiter.getFullName());
        recruiter.setEmail(newRecruiter.getEmail());
        recruiter.setMobileNumber(newRecruiter.getMobileNumber());
        recruiter.setPassword(newRecruiter.getPassword());
        recruiter.setConfirmPassword(newRecruiter.getConfirmPassword());
        recruiterRepository.save(recruiter);
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
    public Recruiter updateProfile(int id, RecruiterProfileDto profileDto) {
        Recruiter recruiter = recruiterRepository.findById(id)
            .orElseThrow(() -> new RecruiterNotFoundException("Recruiter not found"));
        
        // Update basic info
        if (profileDto.getFullName() != null) {
            recruiter.setFullName(profileDto.getFullName());
        }
        
        if (profileDto.getMobileNumber() != 0) {
            recruiter.setMobileNumber(profileDto.getMobileNumber());
        }
        
        // Update company profile if exists or create new
        if (recruiter.getCompanyProfile() == null) {
            recruiter.setCompanyProfile(new CompanyProfile());
        }CompanyProfile companyProfile = recruiter.getCompanyProfile();
        if (profileDto.getCompanyName() != null) {
            companyProfile.setCompanyName(profileDto.getCompanyName());
        }
        
        
        return recruiterRepository.save(recruiter);
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
               dto.getFullName() == null &&
               
               
            		   dto.getIndustryType() == null &&
            				   dto.getMobileNumber() == 0;
        
    }
    


}
