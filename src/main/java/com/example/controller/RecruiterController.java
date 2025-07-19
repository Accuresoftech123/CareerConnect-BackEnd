package com.example.controller;

import com.example.dto.RecruiterDTO;
import com.example.dto.RecruiterLoginDto;
import com.example.dto.RecruiterProfileDto;
import com.example.dto.RecruiterRegistrationDto;
import com.example.dto.recruiterProfileImgDto;
import com.example.entity.Recruiter;
import com.example.service.EmailService;
import com.example.service.RecruiterService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing recruiter-related operations such as
 * registration, login, and profile update.
 */
@RestController
@RequestMapping("/api/recruiters") // Plural for RESTful consistency
@CrossOrigin(origins = "http://localhost:3000")
public class RecruiterController {

	@Autowired
	private RecruiterService recruiterService;
@Autowired
private EmailService emailService;


	/**
	 * Registers a new recruiter.
	 *
	 * @param recruiter Recruiter data from request body
	 * @return Success or failure message
	 */
	 @PostMapping("/register")
	    public ResponseEntity<?> registerRecruiter(@RequestBody RecruiterRegistrationDto recruiterDto) {
	        return recruiterService.register(recruiterDto);
	    }

	/**
	 * Authenticates a recruiter login.
	 *
	 * @param loginDto Contains email and password
	 * @return Recruiter info if successful; error message otherwise
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginRecruiter(@RequestBody RecruiterLoginDto loginDto) {
	    return recruiterService.login(loginDto.getEmail(), loginDto.getPassword());
	}

	/**
	 * Updates the profile details of a recruiter.
	 *
	 * @param id         Recruiter ID
	 * @param profileDto Profile update information
	 * @return Success message
	 */
	@PutMapping("/profile/{id}")
	public ResponseEntity<Recruiter> updateRecruiterProfile(@PathVariable int id,
			@RequestBody RecruiterProfileDto profileDto) {

		Recruiter result = recruiterService.updateProfile(id, profileDto);
		return ResponseEntity.ok(result);
	}
	

	
	
	//verify otp 
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
	    System.out.println("Incoming OTP verification request: " + request);

	    String email = request.get("email");
	    String otp = request.get("otp");

	    if (email == null || otp == null) {
	        return ResponseEntity.badRequest().body(Map.of(
	            "success", false,
	            "message", "Email and OTP are required."
	        ));
	    }

	    Map<String, Object> result = emailService.verifyRecruiterOtp(email, otp);
	    
	    if (Boolean.TRUE.equals(result.get("success"))) {
	        return ResponseEntity.ok(result);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	    }
	} 

	 @GetMapping("/recent/count")
	    public ResponseEntity<Long> countRecentRecruiters() {
	        return ResponseEntity.ok(recruiterService.countRecruitersFromLast30Days());
	    }
	 
	 @GetMapping("/recent")
	    public ResponseEntity<List<RecruiterDTO>> getRecentRecruiterSummaries() {
	        List<RecruiterDTO> recentRecruiters = recruiterService.getRecentRecruiterSummaries();
	        return new ResponseEntity<>(recentRecruiters, HttpStatus.OK);
	    }
	 
	 //get image and company name
	 @GetMapping("/profile-image/{id}")
	 public ResponseEntity<recruiterProfileImgDto> getRecruiterProfileImage(@PathVariable int id) {
	     recruiterProfileImgDto dto = recruiterService.getRecruiterProfileImage(id);
	     return new ResponseEntity<>(dto, HttpStatus.OK);
	 }


}
