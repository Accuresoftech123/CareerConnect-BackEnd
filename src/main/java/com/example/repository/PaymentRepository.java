package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Payment;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment,Long>{
	
	 // To get total collected amount
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'captured'")
    Double getTotalCollectedAmount();

}
