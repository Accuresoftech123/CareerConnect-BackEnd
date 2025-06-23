package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.entity.jobposting.SavedJob;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long>{
	
	 List<SavedJob> findByJobSeeker(JobSeeker jobSeeker);
	    Optional<SavedJob> findByJobSeekerAndJobPost(JobSeeker jobSeeker, JobPost jobPost);
	    void deleteByJobSeekerIdAndJobPostId(int jobSeekerId, int jobPostId);

}
