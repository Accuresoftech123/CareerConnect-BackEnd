package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.ApplicantDTO;
import com.example.dto.RecruiterDTO;
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
 * Service class for handling Recruiter-related business logic. Acts as an
 * intermediary between the controller and repository layers.
 */
@Service
public class RecruiterService {


	@Autowired
	private RecruiterRepository recruiterRepository;

	/**
	 * Registers a new recruiter. Checks for duplicate email before saving.
	 *
	 * @param newRecruiter the recruiter to be registered
	 * @return registration status message
	 */
	public Recruiter register(RecruiterRegistrationDto newRecruiter) {
		Optional<Recruiter> existing = recruiterRepository.findByEmail(newRecruiter.getEmail());

		if (existing.isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		Recruiter recruiter = new Recruiter();
		recruiter.setFullName(newRecruiter.getFullName());
		recruiter.setEmail(newRecruiter.getEmail());
		recruiter.setMobileNumber(newRecruiter.getMobileNumber());
		recruiter.setPassword(newRecruiter.getPassword());
		recruiter.setConfirmPassword(newRecruiter.getConfirmPassword());
		recruiter.setStatus(Status.APPROVED);

		return recruiterRepository.save(recruiter); // return saved object
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

		if (existing == null || !existing.getPassword().equals(password)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}

		if (existing.getStatus() != Status.APPROVED) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Access Denied. Your application status is " + existing.getStatus());
		}

		// Map entity to DTO
		RecruiterDTO dto = new RecruiterDTO();
		dto.setId(existing.getId());
		dto.setFullName(existing.getFullName());
		dto.setEmail(existing.getEmail());

		return ResponseEntity.ok(dto);
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
		}
		CompanyProfile companyProfile = recruiter.getCompanyProfile();

		return recruiterRepository.save(recruiter);
	}

	/**
	 * Checks whether all profile fields in the DTO are empty.
	 *
	 * @param dto the profile DTO
	 * @return true if all fields are empty; false otherwise
	 */

   
    @Autowired
    private EmailService emailService;

   


    /**
     * Validates recruiter credentials for login.
     *
     * @param email    email of the recruiter
     * @param password password of the recruiter
     * @return recruiter object if valid, otherwise null
     */
   
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

    //Forgot Password 
    
    public String forgotPassword(String email) {
    	Recruiter recruiter = recruiterRepository.findByEmail(email)
    			.orElseThrow(()->new RuntimeException("Recruiter not found with email: " + email));
    	emailService.generateAndSendOtp(recruiter);
    	return  "OTP sent to registered email.";
    }
   

    public String validateOtpAndResetPassword(String email, String otp, String newPassword) {
    	Recruiter recruiter=recruiterRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Recruiter Not Found"));
    	
    	if(recruiter.getOtp()==null|| recruiter.getOtpGeneratedTime()==null) {
    		return "OTP not found";
    	}
    	
    	if(!recruiter.getOtp().equals(otp)) {
    		return "Invalid OTP";
    	}
    	
    	if(recruiter.getOtpGeneratedTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
    		return "OTP expired";
    	}
    	recruiter.setPassword(newPassword);
    	recruiter.setOtp(null);
    	recruiter.setOtpGeneratedTime(null);
    	recruiterRepository.save(recruiter);
    	
    	return "Password reset successful.";
    	
    }


}
