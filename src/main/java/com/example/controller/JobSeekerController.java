package com.example.controller;

import com.example.dto.JobSeekerLoginDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerRegistrationDto;
import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;
import com.example.service.EmailService;
import com.example.service.JobSeekerService;
import com.example.service.mobileOtpService;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST Controller for Job Seeker operations like registration, login, and
 * profile update.
 */
@RestController

@RequestMapping("/jobseekers")  // Use plural naming for RESTful endpoints
@CrossOrigin(origins = "http://localhost:3000")



public class JobSeekerController {

	@Autowired
	private JobSeekerService jobSeekerService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private mobileOtpService MobileOtpService;
	@Autowired
	private JobSeekerRepository repo;

	/**
	 * Registers a new job seeker.
	 *
	 * @param registerDto Job seeker registration data
	 * @return Success message
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerJobSeeker(@RequestBody JobSeekerRegistrationDto registerDto) {
	    return jobSeekerService.register(registerDto);
	}

	// Email verification controller
	
	

	    

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    String otp = request.get("otp");

	    if (email == null || otp == null) {
	        return ResponseEntity.badRequest().body(Map.of(
	            "success", false,
	            "message", "Email and OTP are required."
	        ));
	    }

	    String result = emailService.verifyOtp(email, otp);

	    if (EmailService.OTP_SUCCESS.equals(result)) { // this will now match properly
	        return ResponseEntity.ok(Map.of(
	            "success", true,
	            "message", "OTP verified successfully!"
	        ));
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
	            "success", false,
	            "message", result
	        ));
	    }

	}

	

	// Resend otp controller
	@PostMapping("/resendOTP")
	public ResponseEntity<String> resendotp(@RequestParam String email) {
		String result = emailService.resendOtp(email);
		if (result.equals("New OTP sent successfully!")) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}

	}

	/**
	 * Authenticates a job seeker with email and password.
	 *
	 * @param loginDto Login credentials
	 * @return JobSeeker entity if login is successful, else error
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginJobSeeker(@RequestBody JobSeekerLoginDto loginDto) {
		JobSeeker jobSeeker = jobSeekerService.login(loginDto.getEmail(), loginDto.getPassword());

		if (jobSeeker != null) {
			return ResponseEntity.ok(jobSeeker);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}

	/**
	 * Updates the profile of a job seeker.
	 *
	 * @param id         Job seeker ID
	 * @param profileDto Profile data
	 * @return Success message
	 */
	@PutMapping("/{id}/profile")
	public ResponseEntity<?> updateJobSeekerProfile(@PathVariable Integer id,
	                                                @RequestBody JobSeekerProfileDto profileDto) {
	    return jobSeekerService.updateJobSeekerProfile(id, profileDto);
	}
	
	//Send mobile phone verification code
	
	    @PostMapping("/sendOtp")
	    public ResponseEntity<String> sendMobileOtp(@RequestParam String mobileNumber) {
	        Optional<JobSeeker> seekerOpt = repo.findByMobileNumber(mobileNumber);

	        if (seekerOpt.isPresent()) {
	            JobSeeker jobSeeker = seekerOpt.get();
	           
	            MobileOtpService.generateAndSendMobileOtp(jobSeeker);
	           
	            return ResponseEntity.ok("OTP sent successfully to " + mobileNumber);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile number not found!");
	        }
	    }
	    
	  // track the profile
	    @GetMapping("/{id}/profile-completion")
	    public ResponseEntity<Map<String, Object>> getProfileCompletion(@PathVariable int id) {
	        JobSeeker jobSeeker = repo.findById(id)
	                .orElseThrow(() -> new RuntimeException("JobSeeker not found with ID: " + id));

	        Map<String, Object> status = jobSeekerService.getProfileCompletionStatus(jobSeeker);
	        return ResponseEntity.ok(status);
	    }
}
