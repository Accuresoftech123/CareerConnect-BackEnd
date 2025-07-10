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
import com.example.entity.Recruiter;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private JobSeekerRepository repo;
	@Autowired
	private RecruiterRepository recruiterRepository;
	
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
		String otp = String.valueOf(100000 + new Random().nextInt(999999));

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

    // ====================== Recruiter Email OTP Methods =====================

    public void sendRecruiterOtpMail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Recruiter Email Verification OTP");
        message.setText("Your OTP is: " + otp + "\nIt will expire in 5 minutes. Please do not share it.");
        mailSender.send(message);
    }

    public void generateAndSendOtp(Recruiter recruiter) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        recruiter.setOtp(otp);
        recruiter.setOtpGeneratedTime(LocalDateTime.now());
        recruiterRepository.save(recruiter);
        sendRecruiterOtpMail(recruiter.getEmail(), otp);
    }

    public String verifyRecruiterOtp(String email, String otp) {
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findByEmail(email);
        if (optionalRecruiter.isEmpty()) {
            return EMAIL_NOT_FOUND;
        }

        Recruiter recruiter = optionalRecruiter.get();

        if (recruiter.getOtpGeneratedTime() == null ||
            Duration.between(recruiter.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() > 5) {
            return OTP_EXPIRED;
        }

        if (otp.equals(recruiter.getOtp())) {
            recruiter.setVerified(true);
            recruiter.setOtp(null);
            recruiter.setOtpGeneratedTime(null);
            recruiterRepository.save(recruiter);
            return OTP_SUCCESS;
        } else {
            return OTP_INVALID;
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
