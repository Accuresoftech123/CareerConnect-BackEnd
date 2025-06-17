package com.example.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Applicant;
import com.example.entity.Recruiter;
import com.example.enums.Status;

/**
 * Repository interface for Recruiter entity.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {

    /**
     * Custom method to find a recruiter by email.
     * Spring Data JPA will automatically generate the query based on method name.
     *
     * @param email - email of the recruiter
     * @return Optional containing Recruiter if found, or empty if not
     */
    Optional<Recruiter> findByEmail(String email);
    //it is used to countbyStatus
    long countByStatus(Status status);
    boolean existsByEmail(String email);
    
//    @Query("SELECT a FROM Applicant a WHERE a.jobPost.recruiter.id = :recruiterId")
//    List<Applicant> findApplicantsByRecruiterId(@Param("recruiterId") int recruiterId);
}
