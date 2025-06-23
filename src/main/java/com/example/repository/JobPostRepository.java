package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.JobPostDto;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.JobPostStatus;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

	@Query("SELECT j FROM JobPost j WHERE " +
		       "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
		       "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
		       "(:experience IS NULL OR j.experience = :experience)")
		List<JobPost> searchJobs(
		        @Param("title") String title,
		        @Param("location") String location,
		        @Param("experience") String experience);
	
	List<JobPost> findByRecruiter(Recruiter recruiter);
    List<JobPost> findByTitleContainingIgnoreCase(String title);
    List<JobPost> findByJobType(String jobType);
    List<JobPost> findByExperience(String experience);
    int countByRecruiter(Recruiter recruiter);

    List<JobPost> findByRecruiterAndLastDateToApplyAfter(
            Recruiter recruiter, LocalDate date);
    
    
    @Modifying
    @Query("UPDATE JobPost j SET j.status = 'CLOSED' WHERE j.id = :jobId AND j.recruiter = :recruiter")
    int closeJobPost(@Param("jobId") Long jobId, @Param("recruiter") Recruiter recruiter);

    @Query("SELECT jp FROM JobPost jp WHERE jp.status = 'OPEN' AND jp.lastDateToApply >= :currentDate")
    List<JobPost> findAllActiveJobPosts(@Param("currentDate") LocalDate currentDate);
    
    
    // Basic method to find all closed jobs
    List<JobPost> findByStatus(JobPostStatus status);
    
 // Find closed jobs by recruiter
    List<JobPost> findByRecruiterIdAndStatus(Integer recruiterId, JobPostStatus status);
    
    // Find closed jobs with additional filters
    @Query("SELECT j FROM JobPost j WHERE j.status = 'CLOSED' " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:minSalary IS NULL OR j.salary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR j.salary <= :maxSalary)")
     List<JobPost> findClosedJobsWithFilters(
             @Param("title") String title,
             @Param("minSalary") Double minSalary,
             @Param("maxSalary") Double maxSalary);
    
    // Find jobs closed before a certain date
    List<JobPost> findByStatusAndLastDateToApplyBefore(JobPostStatus status, LocalDate date);

    
 
}
