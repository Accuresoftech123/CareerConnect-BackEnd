package com.example.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.enums.Role;
import com.example.enums.Status;
import com.example.exception.RecruiterNotFoundException;
import com.example.repository.ApplicantRepository;
import com.example.repository.RecruiterRepository;
import com.example.security.CustomUserDetails;
import com.example.security.CustomUserDetailsService;
import com.example.security.JwtUtil;

import jakarta.transaction.Transactional;

/**
 * Service class for handling Recruiter-related business logic.
 * Acts as an intermediary between the controller and repository layers.
 */
@Service
public class RecruiterService {
	
	

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    

    @Autowired
    private RecruiterRepository recruiterRepository;

    
    @Autowired
    private EmailService emailService;
    /**
     * Registers a new recruiter.
     * Checks for duplicate email before saving.
     *
     * @param newRecruiter the recruiter to be registered
     * @return registration status message
     */
    public ResponseEntity<?> register(RecruiterRegistrationDto newRecruiter) {
        Optional<Recruiter> existing = recruiterRepository.findByEmail(newRecruiter.getEmail());

        if (existing.isPresent()) {
            Recruiter existingRecruiter = existing.get();
            
            if (!existingRecruiter.isVerified()) {
                // Re-send OTP to the unverified recruiter
                emailService.generateAndSendOtp(existingRecruiter);

                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "success", true,
                    "message", "Email already registered but not verified. OTP re-sent.",
                    "recruiterId", existingRecruiter.getId()
                ));
            }

            // Already registered and verified
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "success", false,
                "message", "Email already registered"
            ));
        }

       Recruiter recruiter = new Recruiter();
		recruiter.setFullName(newRecruiter.getFullName());
		recruiter.setEmail(newRecruiter.getEmail());
		recruiter.setMobileNumber(newRecruiter.getMobileNumber());
		// Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newRecruiter.getPassword());
		recruiter.setPassword(encodedPassword);
		recruiter.setConfirmPassword(encodedPassword);
		recruiter.setStatus(Status.APPROVED);
         recruiter.setVerified(false);
		recruiter.setRole(Role.ROLE_RECRUITER);


	//	recruiterRepository.save(recruiter);

        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        emailService.generateAndSendOtp(savedRecruiter);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP sent. Please verify your account.");
        response.put("recruiterId", recruiter.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

        if (existing == null || !passwordEncoder.matches(password, existing.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
      if(!existing.isVerified()){
    	  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please verify your email before logging in.");
    	  
      }
        if (existing.getStatus() != Status.APPROVED) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access Denied. Your application status is " + existing.getStatus());
        }
           
        // ✅ Load user details for JWT
		
		 UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		 CustomUserDetails customUser = (CustomUserDetails) userDetails;
		 
		  // Generate JWT token with role
	      String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());


	      // Prepare response
	      Map<String, Object> response = new HashMap<>();
	      response.put("token", token);
	      response.put("role", existing.getRole().name());
	      response.put("id", existing.getId());
        // Map entity to DTO
        RecruiterDTO dto = new RecruiterDTO();
        dto.setId(existing.getId());
        dto.setFullName(existing.getFullName());
        dto.setEmail(existing.getEmail());

       // return ResponseEntity.ok(dto);
        return ResponseEntity.ok(response);
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
        
        
        
        return recruiterRepository.save(recruiter);
    }
    
   
    public long countRecruitersFromLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        return recruiterRepository.countRecruitersRegisteredInLast30Days(startDate);
    }
   

}
