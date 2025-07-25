package com.example.enums;

public enum PaymentStatus {
    CREATED,     // Order created but not paid
    SUCCESS,  // Payment successful
    FAILED,      // Payment failed
    PENDING,     // Waiting for confirmation
    CANCELLED    // Razorpay modal closed or error occurred
}