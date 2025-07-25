package com.example.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.enums.PaymentStatus;
import com.example.enums.SubscriptionPlan;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String paymentId;
	private double amount;
	private String currency;
	private String receipt;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	  @ManyToOne
	    @JoinColumn(name = "job_seeker_id")
	    private JobSeeker jobSeeker;

 
    @CreationTimestamp
	private LocalDateTime createdAt;
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Enumerated(EnumType.STRING)
	private SubscriptionPlan plan;



	public Payment(Long id, String paymentId, double amount, String currency, String receipt, PaymentStatus status,
			JobSeeker jobSeeker, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.paymentId = paymentId;
		this.amount = amount;
		this.currency = currency;
		this.receipt = receipt;
		this.status = status;
		this.jobSeeker = jobSeeker;
		this.createdAt = createdAt;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}



	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}



	public SubscriptionPlan getPlan() {
		return plan;
	}



	public void setPlan(SubscriptionPlan plan) {
		this.plan = plan;
	}



	public PaymentStatus getStatus() {
		return status;
	}



	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	
	


	
	
	
	
}
