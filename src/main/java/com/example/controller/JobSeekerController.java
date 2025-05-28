package com.example.controller;


import com.example.dto.JobSeekerLoginDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerRegistrationDto;
import com.example.entity.JobSeeker;
import com.example.service.JobSeekerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Job Seeker operations like registration, login, and profile update.
 */
@RestController
@RequestMapping("/jobseekers")  // Use plural naming for RESTful endpoints
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    /**
     * Registers a new job seeker.
     *
     * @param registerDto Job seeker registration data
     * @return Success message
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerJobSeeker(@RequestBody JobSeekerRegistrationDto registerDto) {
        String result = jobSeekerService.register(registerDto);
        return ResponseEntity.ok(result);
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
     * @param id        Job seeker ID
     * @param profileDto Profile data
     * @return Success message
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<String> updateJobSeekerProfile(
            @PathVariable Integer id,
            @RequestBody JobSeekerProfileDto profileDto) {

        String result = jobSeekerService.updateJobSeekerProfile(id, profileDto);
        return ResponseEntity.ok(result);
    }
}
