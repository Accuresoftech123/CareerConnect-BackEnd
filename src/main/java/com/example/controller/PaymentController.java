package com.example.controller;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PaymentConformDto;
import com.example.entity.Receipt;
import com.example.repository.PaymentRepository;
import com.example.service.PaymentService;
import com.example.service.ReceiptService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000" )
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	@Autowired
	PaymentRepository paymentRepository;

    // ✅ 1️⃣ API to Create Razorpay Order (and create Payment record with status CREATED)
    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestParam int userId,
            @RequestParam double amount) {
        try {
            Map<String, Object> response = paymentService.createOrder(userId, amount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to create order"));
        }
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

    // ✅ 2️⃣ API to Confirm Payment (update existing Payment record to SUCCESS)
    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestParam String paymentId) {
        try {
            paymentService.processSuccessfulPayment(paymentId);
            return ResponseEntity.ok("Payment confirmed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to confirm payment");
        }
    }


    // GET Receipts by JobSeeker ID
    @GetMapping("/jobseeker/{jobSeekerId}")
    public ResponseEntity<List<Receipt>> getReceiptsByJobSeekerId(@PathVariable int jobSeekerId) {
        List<Receipt> receipts = receiptService.getReceiptsByJobSeekerId(jobSeekerId);
        return ResponseEntity.ok(receipts);
    }

}
