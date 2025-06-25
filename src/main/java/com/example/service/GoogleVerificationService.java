package com.example.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class GoogleVerificationService {
	
	  @Value("${google.client.id}")
	    private String clientId;

	    public GoogleIdToken.Payload verifyToken(String idTokenString) {
	        try {
	            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
	                    .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
	                    .setAudience(Collections.singletonList(clientId))
	                    .build();

	            GoogleIdToken idToken = verifier.verify(idTokenString);
	            if (idToken != null) {
	                return idToken.getPayload();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


}
