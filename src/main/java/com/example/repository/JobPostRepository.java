package com.example.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.JobPostStatus;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

    // Search Jobs with optional filters: title, location, experience
    @Query("SELECT j FROM JobPost j WHERE " +
           "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:experience IS NULL OR j.minExperience = :experience)")
    List<JobPost> searchJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("experience") String experience);

    // Find jobs by recruiter
    List<JobPost> findByRecruiter(Recruiter recruiter);

    // Count job posts by recruiter
    int countByRecruiter(Recruiter recruiter);

    // Find active job posts by recruiter
    List<JobPost> findByRecruiterAndLastDateToApplyAfter(Recruiter recruiter, LocalDate date);

  //close jobpost
    
    boolean existsByIdAndRecruiterIdAndStatus(Long jobId, Integer recruiterId, JobPostStatus status);

    @Modifying
    @Query("UPDATE JobPost j SET j.status = :status WHERE j.id = :jobId AND j.recruiter.id = :recruiterId")
    int updateStatus(@Param("jobId") Long jobId,
                     @Param("recruiterId") Integer recruiterId,
                     @Param("status") JobPostStatus status);

    // Find all active (open) job posts
    @Query("SELECT jp FROM JobPost jp WHERE jp.status = 'OPEN' AND jp.lastDateToApply >= :currentDate")
    List<JobPost> findAllActiveJobPosts(@Param("currentDate") LocalDate currentDate);

    // Find job posts by status (OPEN or CLOSED)
    List<JobPost> findByStatus(JobPostStatus status);

    // Find job posts by recruiter and status
    List<JobPost> findByRecruiterIdAndStatus(Integer recruiterId, JobPostStatus status);

    // Find closed jobs with additional filters like title and salary range
    @Query("SELECT j FROM JobPost j WHERE j.status = 'CLOSED' " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:minSalary IS NULL OR j.minSalary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR j.maxSalary <= :maxSalary)")
    List<JobPost> findClosedJobsWithFilters(
            @Param("title") String title,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary);

    // Find job posts closed before a certain date
    List<JobPost> findByStatusAndLastDateToApplyBefore(JobPostStatus status, LocalDate date);
    
   
    
    //get latest added jobpost
    @Query("SELECT j FROM JobPost j WHERE j.recruiter.id = :recruiterId ORDER BY j.postedDate DESC LIMIT 1")
    Optional<JobPost> findLatestByRecruiterId(@Param("recruiterId") Integer recruiterId);
    
    
    
 // Add this method to find non-draft jobs by recruiter
    List<JobPost> findByRecruiterIdAndStatusNot(Integer recruiterId, JobPostStatus status);
    
    @Query("SELECT j FROM JobPost j WHERE j.recruiter.id = :recruiterId ORDER BY j.postedDate DESC")
    List<JobPost> findAllByRecruiterIdOrderByPostedDateDesc(@Param("recruiterId") Integer recruiterId);
    
    long countByStatus(JobPostStatus status);
    
    //For Todays Match JobPost
    @Query("SELECT j FROM JobPost j " +
    	       "WHERE j.postedDate = :today " +
    	       "AND ( j.location = :location " +
    	       "OR :experience BETWEEN j.minExperience AND j.maxExperience ) " +
    	       "OR EXISTS (SELECT s FROM j.skills s WHERE s IN :skills)")
    	List<JobPost> findTodayMatches(
    	    @Param("today") LocalDate today,
    	    @Param("location") String location,
    	    @Param("skills") List<String> skills,
    	    @Param("experience") Integer experience);

    @Query("SELECT COUNT(j) FROM JobPost j " +
    	       "WHERE j.postedDate = :today " +
    	       "AND ( j.location = :location " +
    	       "OR :experience BETWEEN j.minExperience AND j.maxExperience ) " +
    	       "OR EXISTS (SELECT s FROM j.skills s WHERE s IN :skills)")
    	Long countTodayMatches(
    	    @Param("today") LocalDate today,
    	    @Param("location") String location,
    	    @Param("skills") List<String> skills,
    	    @Param("experience") Integer experience);

}
