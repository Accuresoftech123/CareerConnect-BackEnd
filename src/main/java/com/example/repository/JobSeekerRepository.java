package com.example.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JobSeeker;



/**
 * Repository interface for Job_Seeker entity.
 * Extends JpaRepository to provide built-in CRUD operations.
 */
@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

    /**
     * Custom finder method to search for a job seeker by email.
     * Returns an Optional that may contain a Job_Seeker if found.
     *
     * @param email the email to search by
     * @return Optional<Job_Seeker> if email exists, else empty
     */
    Optional<JobSeeker> findByEmail(String email);
    Optional<JobSeeker> findByMobileNumber(String mobileNumber);
    
   

}
