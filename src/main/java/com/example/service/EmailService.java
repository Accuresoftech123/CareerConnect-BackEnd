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
		Optional<JobSeeker> gmail = repo.findByEmail(email);
		if (gmail.isPresent()) {
			JobSeeker jobSeeker = gmail.get();

			// Check if OTP is expired (valid for 5 mins)
			if (jobSeeker.getOtpGeneratedTime() == null
					|| Duration.between(jobSeeker.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() > 5) {
				return "OTP has expired.\r\n" + "Please click \"Resend OTP\" to receive a new OTP.";
			}

			if (jobSeeker.getOtp().equals(otp)) {
				jobSeeker.setVerified(true);
				jobSeeker.setOtp(null);// clear otp
				jobSeeker.setOtpGeneratedTime(null);// clear otpGenerated time..
				repo.save(jobSeeker);
				return "Registration successful! ";
			} else {
				return "Invalid OTP!";
			}
		} else {
			return "Email not found";
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
