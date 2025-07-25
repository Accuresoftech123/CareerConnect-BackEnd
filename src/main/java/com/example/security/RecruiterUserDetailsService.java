package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Recruiter;
import com.example.repository.RecruiterRepository;

@Service
public class RecruiterUserDetailsService implements UserDetailsService {
	

    @Autowired
    private RecruiterRepository recruiterRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		// Try finding Recruiter by email
				Optional<Recruiter> recruiter = recruiterRepo.findByEmail(email);
						
				if (recruiter.isPresent()) {
					Recruiter employee = recruiter.get();
				     return new CustomUserDetails(employee.getEmail(),employee.getPassword(),employee.getRole());
				}
				
				throw new UsernameNotFoundException("User not found with email: " + email);
	}

}
