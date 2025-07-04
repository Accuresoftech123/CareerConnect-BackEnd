package com.example.service;

import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.example.entity.JobSeeker;
import com.example.entity.Payment;
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
	
	
	public String createOrder(double amount) throws Exception {
	
		RazorpayClient razorpayClient=new RazorpayClient(keyId,keySecret);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", (int)(amount * 100)); // in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
		orderRequest.put("payment_capture", 1); // auto-capture

        Order order = razorpayClient.orders.create(orderRequest);
        
        //Save Payment details in database
        Payment payment=new Payment();
        payment.setPaymentId(order.get("id"));
        payment.setAmount(amount);
        payment.setCurrency(order.get("currency"));
        payment.setReceipt(order.get("receipt"));
        payment.setStatus(order.get("status"));
        paymentRepository.save(payment);
        
        return order.toString(); // return JSON
	}
	
    
	public void processSuccessfulPayment(int userId, double amount) {
	    try {
	        // Fetch user by ID
	        Optional<JobSeeker> optionalUser = jobSeekerRepository.findById(userId);
	        if (optionalUser.isPresent()) {
	            JobSeeker jobSeeker = optionalUser.get();

	            // Create and save Payment
	            Payment payment = new Payment();
	            payment.setAmount(amount);
	            payment.setCurrency("INR");
	            payment.setStatus("SUCCESS");
	            payment.setPaymentId(UUID.randomUUID().toString());
	            payment.setReceipt("RCP-" + System.currentTimeMillis());
	            payment.setJobSeeker(jobSeeker);

	            paymentRepository.save(payment);

	            // Now generate and save Receipt, passing payment and jobSeeker
	            receiptService.generateSendAndSaveReceipt(jobSeeker.getEmail(), amount, payment, jobSeeker);

	        } else {
	            throw new RuntimeException("User not found for id: " + userId);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
