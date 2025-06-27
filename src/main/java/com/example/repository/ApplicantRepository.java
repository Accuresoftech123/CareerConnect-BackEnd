package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.ApplicationStatus;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    // Basic query methods
    List<Applicant> findByJobPost(JobPost jobPost);
    List<Applicant> findByJobSeeker(JobSeeker jobSeeker);
    boolean existsByJobPostAndJobSeeker(JobPost jobPost, JobSeeker jobSeeker);
    long countByJobPost(JobPost jobPost);
    
    // ID-based query methods for better performance
    List<Applicant> findByJobPostId(Integer jobPostId);
    List<Applicant> findByJobSeekerId(Integer jobSeekerId);
    
    // Status-based query methods
    List<Applicant> findByJobPostAndStatus(JobPost jobPost, ApplicationStatus status);
    List<Applicant> findByJobSeekerAndStatus(JobSeeker jobSeeker, ApplicationStatus status);
    
    // Recruiter-specific queries
    List<Applicant> findByJobPost_Recruiter_Id(Integer recruiterId);
    List<Applicant> findByJobPost_Recruiter_IdAndStatus(Integer recruiterId, ApplicationStatus status);
    
    // Custom query with join fetch to avoid N+1 problem
    @Query("SELECT a FROM Applicant a JOIN FETCH a.jobPost WHERE a.jobSeeker.id = :jobSeekerId")
    List<Applicant> findApplicationsWithJobPostsByJobSeekerId(@Param("jobSeekerId") Integer jobSeekerId);
    
    // Count queries for statistics
    long countByJobSeeker(JobSeeker jobSeeker);
    long countByJobPostAndStatus(JobPost jobPost, ApplicationStatus status);
    
    // Find by composite key
    Optional<Applicant> findByJobPostAndJobSeeker(JobPost jobPost, JobSeeker jobSeeker);
    
    // Custom query for pagination
    @Query("SELECT a FROM Applicant a WHERE a.jobPost.id = :jobPostId ORDER BY a.applicationDate DESC")
    List<Applicant> findRecentApplicationsByJobPostId(@Param("jobPostId") Integer jobPostId);
	boolean existsByJobPost_Recruiter_Id(int recruiterId);
	
	
	
    List<Applicant> findByStatus(ApplicationStatus status);
    List<Applicant> findByJobPost_Recruiter(Recruiter recruiter);
    List<Applicant> findByApplicationDateBetween(LocalDateTime start, LocalDateTime end);
    
    long countByJobPost_RecruiterAndApplicationDateAfter(Recruiter recruiter, LocalDateTime date);
    
 // Count by recruiter and status
    long countByJobPost_RecruiterAndStatus(Recruiter recruiter, ApplicationStatus status);

    // Count recent applications by status
    long countByJobPost_RecruiterAndStatusAndApplicationDateAfter(
        Recruiter recruiter, ApplicationStatus status, LocalDateTime date);
    
 // Get top 5 most recent applicants for a recruiter
    List<Applicant> findTop9ByJobPost_RecruiterOrderByApplicationDateDesc(Recruiter recruiter);
    List<Applicant> findTop9ByJobPostInAndStatusNotOrderByApplicationDateDesc(
            List<JobPost> jobPosts, ApplicationStatus status);
  
}