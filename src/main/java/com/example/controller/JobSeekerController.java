package com.example.controller;

import com.example.dto.JobSeekerLoginDto;
import org.springframework.http.MediaType;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerRegistrationDto;
import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;
import com.example.service.EmailService;
import com.example.service.JobSeekerService;
import com.example.service.mobileOtpService;

import com.itextpdf.io.exceptions.IOException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller for Job Seeker operations like registration, login, and
 * profile update.
 */
@RestController

@RequestMapping("/api/jobseekers")  // Use plural naming for RESTful endpoints
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
	
	

	    

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    String otp = request.get("otp");

	    if (email == null || otp == null) {
	        return ResponseEntity.badRequest().body(Map.of(
	            "success", false,
	            "message", "Email and OTP are required."
	        ));
	    }

	    Map<String, Object> result = emailService.verifyOtp(email, otp);

	    if (Boolean.TRUE.equals(result.get("success"))) {
	        return ResponseEntity.ok(result);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	    }

	}

	

	// Resend otp controller
	@PostMapping("/resend-otp")
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
	    return jobSeekerService.login(loginDto.getEmail(), loginDto.getPassword());
	}

	/**
	 * Updates the profile of a job seeker.
	 *
	 * @param id         Job seeker ID
	 * @param profileDto Profile data
	 * @return Success message
	 * @throws java.io.IOException 
	 */
	@PutMapping("/{id}/profile")
	public ResponseEntity<?> updateJobSeekerProfile(
	        @PathVariable Integer id,
	        @RequestPart("dto") JobSeekerProfileDto profileDto,
	        @RequestPart(value = "resumeFile", required = false) MultipartFile resumeFile,
	        @RequestPart(value = "videoFile", required = false) MultipartFile videoFile,
	        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
	) throws IOException, java.io.IOException {
	    return jobSeekerService.updateJobSeekerProfile(id, profileDto, resumeFile, videoFile, imageFile);
	}

	
	//Send mobile phone verification code
	
	    @PostMapping("/send-mobile-otp")
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
	    

	    // Forgot Password — Send OTP
	    @PostMapping("/forgot-password")
	    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
	        JobSeeker seeker = repo.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("JobSeeker not found"));

	        emailService.generateAndSendOtp(seeker);

	        return ResponseEntity.ok("OTP sent to your registered email.");
	    }
	    

	    @PostMapping("/reset-password")
	    public ResponseEntity<String> resetPassword(
	        @RequestParam String email,
	        @RequestParam String otp,
	        @RequestParam String newPassword) {

	        boolean isValid = jobSeekerService.validateOtpAndResetPassword(email, otp, newPassword);
	        if (!isValid) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
	        }
	        return ResponseEntity.ok("Password reset successfully.");
	    }
	    
	    //get jobseeker image and name of jobseeker for dashboard
	    
	    @GetMapping("/get-image-name/{id}")
	    public ResponseEntity<?> getJobSeekerImageAndName(@PathVariable int id) {
	        return jobSeekerService.getJobSeekerImageAndName(id);
	    }
	    
//	 //  GET list of job seekers registered in the last 30 days
//	    @GetMapping("/recent")
//	    public ResponseEntity<List<JobSeeker>> getRecentJobSeekers() {
//	        return ResponseEntity.ok(jobSeekerService.getJobSeekersFromLast30Days());
//	    }

	    // GET count of job seekers registered in the last 30 days
	    @GetMapping("/recent/count")
	    public ResponseEntity<Long> getRecentJobSeekerCount() {
	        return ResponseEntity.ok(jobSeekerService.countJobSeekersFromLast30Days());
	    }
}
