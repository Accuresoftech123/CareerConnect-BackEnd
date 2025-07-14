package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SavedJobPostReportDto;
import com.example.exception.JobAlreadySavedException;
import com.example.service.SavedJobService;

@RestController
@RequestMapping("/api/jobseekers/saved-jobs")
@CrossOrigin(origins = "http://localhost:3000")

public class SavedJobController {
	
	@Autowired
	SavedJobService savedJobService;
	
	 /**
     * Save a job for the job seeker.
     */
	 @PostMapping("/save/{jobSeekerId}/{jobPostId}")
	    public ResponseEntity<String> saveJob(@PathVariable int jobSeekerId, @PathVariable int jobPostId) {
	        try {
	            savedJobService.saveJob(jobSeekerId, jobPostId);
	            return ResponseEntity.ok("Job saved successfully");
	        } catch (JobAlreadySavedException e) {
	            // Job already saved — return 409 Conflict
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        } catch (RuntimeException e) {
	            // JobSeeker not found / JobPost not found — return 404 Not Found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	/**
     * Get all saved jobs for a job seeker.
     */
	
//	@GetMapping("/list/{jobSeekerId}")
//    public ResponseEntity<List<SavedJobPostReportDto>> getSavedJobs(@PathVariable int jobSeekerId) {
//        List<SavedJobPostReportDto> jobPosts = savedJobService.getSavedJobPostDtos(jobSeekerId);
//        return ResponseEntity.ok(jobPosts);
//    }


	
	 /**
     * Remove a saved job.
     */
	@DeleteMapping("/remove/{jobSeekerId}/{jobPostId}")
    public String removeSavedJob(@PathVariable int jobSeekerId, @PathVariable int jobPostId) {
        savedJobService.removeSavedJob(jobSeekerId, jobPostId);
        return "Job removed from saved list";
    }
	
	/**
	 * 
	 * Count of saved jobs
	 */
	
	@GetMapping("/count")
	public Long countOfSavedJobes() {
		return savedJobService.countOfSavedJobes();
	}
	
	
	

}
