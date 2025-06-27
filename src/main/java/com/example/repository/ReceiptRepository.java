package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	

    // Find all receipts by jobSeeker id
    List<Receipt> findByJobSeeker_Id(int jobSeekerId);
}
