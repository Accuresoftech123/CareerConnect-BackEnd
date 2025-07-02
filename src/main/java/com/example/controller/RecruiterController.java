package com.example.controller;

import com.example.dto.RecruiterLoginDto;
import com.example.dto.RecruiterProfileDto;
import com.example.dto.RecruiterRegistrationDto;
import com.example.entity.Recruiter;
import com.example.repository.RecruiterRepository;
import com.example.service.EmailService;
import com.example.service.RecruiterService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing recruiter-related operations such as
 * registration, login, and profile update.
 */
@RestController
@RequestMapping("/recruiters") // Plural for RESTful consistency
@CrossOrigin(origins = "http://localhost:3000")
public class RecruiterController {

	@Autowired
	private RecruiterService recruiterService;

	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RecruiterRepository recruiterRepository;

	/**
	 * Registers a new recruiter.
	 *
	 * @param recruiter Recruiter data from request body
	 * @return Success or failure message
	 */
    @PostMapping("/register")
    public ResponseEntity<String> registerRecruiter(@RequestBody RecruiterRegistrationDto recruiterDto) {
        String result = recruiterService.register(recruiterDto);
        return ResponseEntity.ok(result);
    }


	/**
	 * Authenticates a recruiter login.
	 *
	 * @param loginDto Contains email and password
	 * @return Recruiter info if successful; error message otherwise
	 */
    @PostMapping("/login")
    public ResponseEntity<String> loginRecruiter(@RequestParam String email, @RequestParam String password) {
        String result = recruiterService.login(email, password);
        return ResponseEntity.ok(result);
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
	
//Otp verification Methods
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        Optional<Recruiter> recruiterOpt = recruiterRepository.findByEmail(email);
        if (recruiterOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recruiter not found!");
        }
        emailService.generateAndSendOtp(recruiterOpt.get());
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        String result = emailService.verifyRecruiterOtp(email, otp);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        String result = emailService.resendRecruiterOtp(email);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("Forget-Password")
    public ResponseEntity<String>forgotpassword(@RequestParam String email){
    	String result = recruiterService.forgotPassword(email);
    	return ResponseEntity.ok(result);
    }
    
    @PostMapping("Verify-reset")
    public ResponseEntity<String>VerifyAndResetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword){
    	String result = recruiterService.validateOtpAndResetPassword(email, otp, newPassword);
    	return ResponseEntity.ok(result);
    }
}
