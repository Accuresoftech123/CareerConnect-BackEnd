package com.example.controller;

import com.example.dto.RecruiterLoginDto;
import com.example.dto.RecruiterProfileDto;
import com.example.dto.RecruiterRegistrationDto;
import com.example.entity.Recruiter;
import com.example.service.RecruiterService;

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

	/**
	 * Registers a new recruiter.
	 *
	 * @param recruiter Recruiter data from request body
	 * @return Success or failure message
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerRecruiter(@RequestBody RecruiterRegistrationDto recruiterDto) {
		try {
			Recruiter savedRecruiter = recruiterService.register(recruiterDto);
			return ResponseEntity.ok(savedRecruiter);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
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
}
