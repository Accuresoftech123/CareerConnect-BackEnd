package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService{
	
	@Autowired
	 private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		        // find admin by email
				Optional<Admin> admin = adminRepository.findByEmail(email);
				
				
				if (admin.isPresent()) {
					Admin user = admin.get();
		            return new CustomUserDetails(user.getEmail(),user.getPassword(),user.getRole());
		        }
				
				
				throw new UsernameNotFoundException("User not found with email: " + email);
	}

}
