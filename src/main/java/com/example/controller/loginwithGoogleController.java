package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginWithGoogleToken;
import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;
import com.example.service.GoogleVerificationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class loginwithGoogleController {

	
	 @Autowired
	    private GoogleVerificationService googleVerificationService;

	 @Autowired
	 private JobSeekerRepository jobSeekerRepo;

	 @PostMapping("/google-login")
	 public ResponseEntity<?> loginUser(@RequestBody LoginWithGoogleToken tokenRequest) {
	     String token = tokenRequest.getToken();
	     if (token == null || token.isEmpty()) {
	         return ResponseEntity.badRequest().body("Token is missing");
	     }

	     GoogleIdToken.Payload payload = googleVerificationService.verifyToken(token);

	     if (payload == null) {
	         return ResponseEntity.badRequest().body("Invalid token");
	     }

	     String email = payload.getEmail();
	     String name = (String) payload.get("name");
	     String picture = (String) payload.get("picture");
	     String googleId = payload.getSubject();

	     Optional<JobSeeker> existingUser = jobSeekerRepo.findByEmail(email);
	     JobSeeker user;

	     if (existingUser.isPresent()) {
	         user = existingUser.get();
	         user.setFullName(name);
	         user.setPicture(picture);
	         user.setGoogleId(googleId);
	     } else {
	         user = new JobSeeker();
	         user.setEmail(email);
	         user.setFullName(name);
	         user.setPicture(picture);
	         user.setGoogleId(googleId);
	         user.setVerified(true);  // mark email verified if logging in via Google
	     }

	     jobSeekerRepo.save(user);

	     return ResponseEntity.ok(user);
	 }

}
