package com.example.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private JobSeekerRepository repo;
	
	public static final String OTP_SUCCESS = "OTP Success"; // 🔍 Fixed spelling, proper format

	public static final String OTP_EXPIRED = "OTP has expired. Please click 'Resend OTP' to receive a new OTP.";
    public static final String OTP_INVALID = "Invalid OTP!";
    public static final String EMAIL_NOT_FOUND = "Email not found";


	public void sendOtpMail(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your Email Verification OTP");
		message.setText(
				"Your OTP is: " + otp + "\r\n It will expire in 5 minutes. Please do not share it with anyone.");
		mailSender.send(message);
	}

	// OTP Generation function
	public void generateAndSendOtp(JobSeeker jobSeeker) {
		// Generate 6-digit OTP
		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		// Set OTP and generation time
		jobSeeker.setOtp(otp);
		jobSeeker.setOtpGeneratedTime(LocalDateTime.now());

		// Save to database
		repo.save(jobSeeker);

		// Send email
		sendOtpMail(jobSeeker.getEmail(), otp);
	}

	// Email verification method..

	
	public String verifyOtp(String email, String otp) {
        Optional<JobSeeker> optionalJobSeeker = repo.findByEmail(email);

        if (optionalJobSeeker.isEmpty()) {
            return EMAIL_NOT_FOUND;
        }

        JobSeeker jobSeeker = optionalJobSeeker.get();

        if (jobSeeker.getOtpGeneratedTime() == null ||
            Duration.between(jobSeeker.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() > 5) {
            return OTP_EXPIRED;
        }

        if (otp.equals(jobSeeker.getOtp())) {
            jobSeeker.setVerified(true);
            jobSeeker.setOtp(null);
            jobSeeker.setOtpGeneratedTime(null);
            repo.save(jobSeeker);
            return OTP_SUCCESS;
        } else {
            return OTP_INVALID;
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

}
