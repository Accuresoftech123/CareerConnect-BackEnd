package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SavedJobPostReportDto;
import com.example.service.SavedJobService;

@RestController
@RequestMapping("/jobseekers/saved-jobs")
public class SavedJobController {
	
	@Autowired
	SavedJobService savedJobService;
	
	 /**
     * Save a job for the job seeker.
     */
	@PostMapping("/save/{jobSeekerId}/{jobPostId}")
    public String saveJob(@PathVariable int jobSeekerId, @PathVariable int jobPostId) {
        savedJobService.saveJob(jobSeekerId, jobPostId);
        return "Job saved successfully";
    }
	
	/**
     * Get all saved jobs for a job seeker.
     */
//	@GetMapping("/saved-jobs/{jobSeekerId}")
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
