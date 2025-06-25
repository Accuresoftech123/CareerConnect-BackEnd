package com.example.dto;

public class PaymentConformDto {
	
    private int userId;
    private double amount;
	public PaymentConformDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentConformDto(int userId, double amount) {
		super();
		this.userId = userId;
		this.amount = amount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
    
    

}
