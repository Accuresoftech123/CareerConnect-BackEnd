package com.example.controller;

import java.io.BufferedReader;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PaymentConformDto;
import com.example.repository.PaymentRepository;
import com.example.service.PaymentService;
import com.example.service.ReceiptService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:3000" )
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	@Autowired
	PaymentRepository paymentRepository;
	
	@PostMapping("/create-order")
    public String createOrder(@RequestParam double amount) throws Exception {
        return paymentService.createOrder(amount);
    }
	 // 📌 Simple Webhook endpoint for Razorpay
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) {
        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();

            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            String payload = jsonBuffer.toString();
            System.out.println("📨 Received Webhook Payload: " + payload);

            JSONObject webhookPayload = new JSONObject(payload);

            String eventType = webhookPayload.getString("event");
            System.out.println("📌 Event Type: " + eventType);

            if (eventType.equals("payment.captured")) {
                String paymentId = webhookPayload.getJSONObject("payload")
                        .getJSONObject("payment")
                        .getJSONObject("entity")
                        .getString("id");

                System.out.println("✅ Payment Captured: " + paymentId);
            }

            return ResponseEntity.ok("Webhook received successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing webhook");
        }
    }

    @GetMapping("/total-amount")
    public ResponseEntity<Double> getTotalAmount() {
        Double totalAmount = paymentRepository.getTotalCollectedAmount();
        return ResponseEntity.ok(totalAmount != null ? totalAmount : 0.0);
    }
    
    //Receipt generation
    @Autowired
    ReceiptService receiptService;

    @PostMapping("/generate-receipt")
    public ResponseEntity<String> generateReceipt(@RequestParam String email, @RequestParam double amount) {
        try {
            receiptService.generateSendAndSaveReceipt(email, amount);
            return ResponseEntity.ok("Receipt generated, sent, and saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to process receipt");
        }
    }
    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestBody PaymentConformDto request) {
        try {
            paymentService.processSuccessfulPayment(request.getUserId(), request.getAmount());
            return ResponseEntity.ok("Receipt generated, emailed, and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to generate receipt.");
        }
    }


}
