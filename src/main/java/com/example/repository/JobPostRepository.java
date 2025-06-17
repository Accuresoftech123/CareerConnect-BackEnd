package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.JobPostDto;
import com.example.entity.jobposting.JobPost;

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
 
}
