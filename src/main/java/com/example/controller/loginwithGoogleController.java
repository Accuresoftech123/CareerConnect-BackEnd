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
import com.example.entity.LoginWithGoogle;
import com.example.repository.LoginWithGoogleRepo;
import com.example.service.GoogleVerificationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class loginwithGoogleController {

	
	 @Autowired
	    private GoogleVerificationService googleVerificationService;

	    @Autowired
	    private LoginWithGoogleRepo loginRepo;
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

	        Optional<LoginWithGoogle> existingUser = loginRepo.findByEmail(email);
	        LoginWithGoogle user;

	        if (existingUser.isPresent()) {
	            user = existingUser.get();
	            user.setName(name);
	            user.setPicture(picture);
	        } else {
	            user = new LoginWithGoogle();
	            user.setEmail(email);
	            user.setName(name);
	            user.setPicture(picture);
	            user.setGoogleId(googleId);
	        }

	        loginRepo.save(user);

	        return ResponseEntity.ok(user);
	    }

}
