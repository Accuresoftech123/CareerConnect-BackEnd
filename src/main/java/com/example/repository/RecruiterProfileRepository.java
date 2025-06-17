package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Recruiter;
import java.util.Optional;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<Recruiter, Integer> {
    // Replace findByRecruiterId with findById
    Optional<Recruiter> findById(Integer id);
    
    // If you need to find by email (since email is unique)
    Optional<Recruiter> findByEmail(String email);
    
    // If you need to find by mobile number
    Optional<Recruiter> findByMobileNumber(long mobileNumber);
}