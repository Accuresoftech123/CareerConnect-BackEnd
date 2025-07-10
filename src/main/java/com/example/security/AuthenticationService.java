package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.dto.AuthenticationRequestDto;
import com.example.dto.AuthenticationResponseDto;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        // Authenticate user (throws exception if invalid)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Load user details and cast to CustomUserDetails to get role
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        CustomUserDetails customUser = (CustomUserDetails) userDetails;

        // Generate JWT token with role
        String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());

        // Return token and role
        return new AuthenticationResponseDto(token, customUser.getRole().name());
    }
}
