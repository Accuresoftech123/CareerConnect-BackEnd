package com.example.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
@Service
public class PaymentService {

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
        return order.toString(); // return JSON
	}
}
