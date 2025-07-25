package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;

@Service
public class JobSeekerUserDetailsService implements UserDetailsService {
	
	 @Autowired
	    private JobSeekerRepository jobSeekerRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// find JobSeeker by email
				Optional<JobSeeker> jobSeeker = jobSeekerRepo.findByEmail(email);
				
				
				if (jobSeeker.isPresent()) {
					JobSeeker seeker = jobSeeker.get();
		            return new CustomUserDetails(seeker.getEmail(),seeker.getPassword(),seeker.getRole());
		        }
				
				
				throw new UsernameNotFoundException("User not found with email: " + email);
	}

}
