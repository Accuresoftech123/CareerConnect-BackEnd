package com.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.example.entity.JobSeeker;
import com.example.entity.Payment;
import com.example.enums.PaymentStatus;
import com.example.enums.SubscriptionPlan;
import com.example.repository.JobSeekerRepository;
import com.example.repository.PaymentRepository;
import com.razorpay.Order;
@Service
public class PaymentService {

	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private JobSeekerRepository jobSeekerRepository;
	
	@Autowired
	private ReceiptService receiptService;
	
	
	@Value("${razorpay.key_id}") 
	private String keyId;
	
	@Value("${razorpay.key_secret}")
	private String keySecret;
	
	  // ✅ 1️⃣ Create Order and save Payment with status CREATED
    public Map<String, Object> createOrder(int userId, double amount,SubscriptionPlan plan) throws Exception {
    	
    	 if (plan == SubscriptionPlan.FREEPLAN || amount <= 0) {
    	        // Fetch JobSeeker
    	        JobSeeker jobSeeker = jobSeekerRepository.findById(userId)
    	                .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));

    	        // Create fake Payment entry
    	        Payment payment = new Payment();
    	        payment.setPaymentId("FREE_" + System.currentTimeMillis()); // Fake ID
    	        payment.setAmount(0);
    	        payment.setPlan(plan);
    	        payment.setCurrency("INR");
    	        payment.setReceipt("FREE_RECEIPT_" + System.currentTimeMillis());
    	        payment.setStatus(PaymentStatus.SUCCESS);
    	        payment.setJobSeeker(jobSeeker);
    	        paymentRepository.save(payment);

    	        // Update JobSeeker
    	        jobSeeker.setSubscriptionPlan(plan);
    	        jobSeeker.setPayment(payment);
    	        jobSeekerRepository.save(jobSeeker);

    	        // Optionally send receipt email
//    	        receiptService.generateSendAndSaveReceipt(
//    	                jobSeeker.getEmail(),
//    	                0,
//    	                payment,
//    	                jobSeeker
//    	        );
    	        // Response for frontend
    	        Map<String, Object> response = new HashMap<>();
    	        response.put("message", "Free plan activated successfully");
    	        response.put("plan", plan.name());
    	        return response;
    	    }

        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (amount * 100)); // in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
        orderRequest.put("payment_capture", 1);

        Order order = razorpayClient.orders.create(orderRequest);

        // Fetch JobSeeker
        JobSeeker jobSeeker = jobSeekerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));

        // Save Payment with status CREATED
        Payment payment = new Payment();
        payment.setPaymentId(order.get("id")); // Razorpay Order ID
        payment.setAmount(amount);
        payment.setPlan(plan);
        payment.setCurrency(order.get("currency"));
        payment.setReceipt(order.get("receipt"));
        payment.setStatus(PaymentStatus.CREATED);
        payment.setJobSeeker(jobSeeker);

        paymentRepository.save(payment);

        // Prepare response for frontend
        Map<String, Object> response = new HashMap<>();
        response.put("id", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));

        return response;
    }

    // ✅ 2️⃣ Process Successful Payment — Update existing Payment to SUCCESS
    public void processSuccessfulPayment(String paymentId) {
        try {
            // Fetch existing Payment by paymentId (Razorpay Order ID)
            Payment payment = paymentRepository.findByPaymentId(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found for id: " + paymentId));

            // Update status to SUCCESS
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            
            // ✅ Set subscription plan on JobSeeker
            JobSeeker jobSeeker = payment.getJobSeeker();
            jobSeeker.setSubscriptionPlan(payment.getPlan());
            jobSeeker.setPayment(payment);
            jobSeekerRepository.save(jobSeeker);

            // Send receipt email etc.
            receiptService.generateSendAndSaveReceipt(
                    payment.getJobSeeker().getEmail(),
                    payment.getAmount(),
                    payment,
                    payment.getJobSeeker()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
