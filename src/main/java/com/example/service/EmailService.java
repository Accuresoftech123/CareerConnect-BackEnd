package com.example.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;
import com.example.security.CustomUserDetails;
import com.example.security.CustomUserDetailsService;
import com.example.security.JobSeekerUserDetailsService;
import com.example.security.JwtUtil;
import com.example.security.RecruiterUserDetailsService;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private JobSeekerRepository repo;
	@Autowired
	private RecruiterRepository recruiterRepository;
	
	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private JobSeekerUserDetailsService jobSeekerUserDetailsService;
	    
	    @Autowired
	    private RecruiterUserDetailsService recruiterUserDetailsService;
	    

	    @Autowired
	    private CustomUserDetailsService userDetailsService;
	    
	  
	
	public static final String OTP_SUCCESS = "OTP Success"; // 🔍 Fixed spelling, proper format

	public static final String OTP_EXPIRED = "OTP has expired. Please click 'Resend OTP' to receive a new OTP.";
    public static final String OTP_INVALID = "Invalid OTP!";
    public static final String EMAIL_NOT_FOUND = "Email not found";
    public static final String EMAIL_ALREADY_REGISTERED = "Email already registered";

    @Async
	public void sendOtpMail(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your Email Verification OTP");
		message.setText(
				"Your OTP is: " + otp + "\r\n It will expire in 5 minutes. Please do not share it with anyone.");
		mailSender.send(message);
	}

	// OTP Generation function
	@Async
	public void generateAndSendOtp(JobSeeker jobSeeker) {
	    // Generate 6-digit OTP
	    String otp = String.valueOf(new Random().nextInt(900000) + 100000);

	    // Set OTP and generation time
	    jobSeeker.setOtp(otp);
	    jobSeeker.setOtpGeneratedTime(LocalDateTime.now());

	    // Save to database
	    repo.save(jobSeeker);

	    // Send email
	    sendOtpMail(jobSeeker.getEmail(), otp);
	}

	
	// generate token function
		public String generateToken(String email) {
			 UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			 CustomUserDetails customUser = (CustomUserDetails) userDetails;
		    return jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());
		}
	
	

	// Email verification method..

	
	public Map<String, Object> verifyOtp(String email, String otp) {

        Optional<JobSeeker> optionalJobSeeker = repo.findByEmail(email);

        if (optionalJobSeeker.isEmpty()) {
        	return Map.of("success", false, "message", "Email not found");

        }

        JobSeeker jobSeeker = optionalJobSeeker.get();

        if (jobSeeker.getOtpGeneratedTime() == null ||
            Duration.between(jobSeeker.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() > 5) {
        	 return Map.of("success", false, "message", "OTP expired");
        }

        if (otp.equals(jobSeeker.getOtp())) {
            jobSeeker.setVerified(true);
            jobSeeker.setOtp(null);
            jobSeeker.setOtpGeneratedTime(null);
            repo.save(jobSeeker);
            
            //call the generate token function 
         // ✅ Load user details for JWT

    		UserDetails userDetails = jobSeekerUserDetailsService.loadUserByUsername(email);
    		CustomUserDetails customUser = (CustomUserDetails) userDetails;

    		// Generate JWT token with role
    		String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());
            
            return Map.of(
                    "success", true,
                    "message", "OTP verified successfully",
                    "token", token,
                    "role", jobSeeker.getRole().name(),
                    "id", jobSeeker.getId()
                );
            
            
        } else {
        	return Map.of("success", false, "message", "Invalid OTP");
        }
	}


	// Resend Otp
	public String resendOtp(String email) {
		Optional<JobSeeker> jobSeekerOpt = repo.findByEmail(email);
		if (jobSeekerOpt.isEmpty()) {
			return "Email not found!";
		}

		JobSeeker jobSeeker = jobSeekerOpt.get();

		generateAndSendOtp(jobSeeker);

		return "New OTP sent successfully!";
	}

    // ====================== Recruiter Email OTP Methods =====================
	@Async
    public void sendRecruiterOtpMail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Recruiter Email Verification OTP");
        message.setText("Your OTP is: " + otp + "\nIt will expire in 5 minutes. Please do not share it.");
        mailSender.send(message);
    }

    @Async
    public void generateAndSendOtp(Recruiter recruiter) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        recruiter.setOtp(otp);
        recruiter.setOtpGeneratedTime(LocalDateTime.now());
        recruiterRepository.save(recruiter);
        sendRecruiterOtpMail(recruiter.getEmail(), otp);
    }

    
    public Map<String, Object> verifyRecruiterOtp(String email, String otp) {
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findByEmail(email);

        if (optionalRecruiter.isEmpty()) {
        	return Map.of("success", false, "message", "Email not found");

        }

        Recruiter recruiter = optionalRecruiter.get();

        // Check if OTP is expired (older than 5 minutes)
        if (recruiter.getOtpGeneratedTime() == null ||
            Duration.between(recruiter.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() > 5) {
        	 return Map.of("success", false, "message", "OTP expired");
        }

        if (otp.equals(recruiter.getOtp())) {
            recruiter.setVerified(true);
            recruiter.setOtp(null);
            recruiter.setOtpGeneratedTime(null);
            recruiterRepository.save(recruiter);
            
            //call the generate token function 
            //String token = generateToken(email);
            
         // ✅ Load user details for JWT

    		UserDetails userDetails = recruiterUserDetailsService.loadUserByUsername(email);
    		CustomUserDetails customUser = (CustomUserDetails) userDetails;

    		// Generate JWT token with role
    		String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());
            
            return Map.of(
                    "success", true,
                    "message", "OTP verified successfully",
                    "token", token,
                    "role", recruiter.getRole().name(),
                    "id", recruiter.getId()
                );
            
            
        } else {
        	return Map.of("success", false, "message", "Invalid OTP");
        }
    }


    public String resendRecruiterOtp(String email) {
        Optional<Recruiter> recruiterOpt = recruiterRepository.findByEmail(email);
        if (recruiterOpt.isEmpty()) {
            return EMAIL_NOT_FOUND;
        }

        Recruiter recruiter = recruiterOpt.get();
        generateAndSendOtp(recruiter);
        return "New OTP sent successfully!";
    }


}
