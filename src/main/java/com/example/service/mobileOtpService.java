package com.example.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.config.RestTemplateConfig;
import com.example.entity.JobSeeker;
import com.example.repository.JobSeekerRepository;

@Service
public class mobileOtpService {

    private final RestTemplateConfig restTemplateConfig;
	

	    @Autowired
	    private RestTemplate restTemplate;

	    @Autowired
	    private JobSeekerRepository repo;

	    @Value("${fast2sms.api.key}")
	    private String apiKey;

	    @Value("${fast2sms.sender.id}")
	    private String senderId;

	    @Value("${fast2sms.message.id}")
	    private String messageId;

	    @Value("${fast2sms.sms.url}")
	    private String smsUrl;

    mobileOtpService(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }

	    // Generate and send OTP
	    public void generateAndSendMobileOtp(JobSeeker jobSeeker) {
	        String otp = String.valueOf(100000 + new Random().nextInt(900000));

	        jobSeeker.setMobileOtp(otp);
	        jobSeeker.setMobileOtpGeneratedTime(LocalDateTime.now());
	        repo.save(jobSeeker);

	        sendOtpSms(jobSeeker.getMobileNumber(), otp);
	
	    }

	    // Send SMS via Fast2SMS API
	    public void sendOtpSms(String mobileNumber, String otp) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("authorization", apiKey);
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        JSONObject request = new JSONObject();
	        request.put("sender_id", senderId);
	        request.put("message", messageId);
	        request.put("variables_values", otp);
	        request.put("route", "dlt");
	        request.put("numbers", mobileNumber);

	        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

	        try {
	            ResponseEntity<String> response = restTemplate.exchange(
	                    smsUrl, HttpMethod.POST, entity, String.class);
	            System.out.println("SMS API Response: " + response.getBody());
	        } catch (HttpClientErrorException e) {
	            System.out.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	        }
	    }
	}


