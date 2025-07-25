package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Admin;
import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.repository.AdminRepository;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	    @Autowired
	    private JobSeekerRepository jobSeekerRepo;

	    @Autowired
	    private RecruiterRepository recruiterRepo;
	    
		@Autowired
		 private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// find JobSeeker by email
		Optional<JobSeeker> jobSeeker = jobSeekerRepo.findByEmail(email);
		
		if (jobSeeker.isPresent()) {
			JobSeeker seeker = jobSeeker.get();
            return new CustomUserDetails(seeker.getEmail(),seeker.getPassword(),seeker.getRole());
        }
		
		// Try finding Recruiter by email
		Optional<Recruiter> recruiter = recruiterRepo.findByEmail(email);
				
		if (recruiter.isPresent()) {
			Recruiter employee = recruiter.get();
		     return new CustomUserDetails(employee.getEmail(),employee.getPassword(),employee.getRole());
		}
		
		
		// find admin by email
		Optional<Admin> admin = adminRepository.findByEmail(email);
		
		
		if (admin.isPresent()) {
			Admin user = admin.get();
            return new CustomUserDetails(user.getEmail(),user.getPassword(),user.getRole());
        }
		
		 throw new UsernameNotFoundException("User not found with email: " + email);
		
		
	}

}
