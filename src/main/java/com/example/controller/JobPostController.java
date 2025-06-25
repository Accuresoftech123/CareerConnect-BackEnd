package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.JobPostDto;
import com.example.entity.jobposting.JobPost;
import com.example.service.JobPostService;

@RestController
@RequestMapping("/jobposts")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    // create a job post by recruiter
    @PostMapping("/recruiter/{recruiterId}/jobposts")
    public ResponseEntity<JobPostDto> createJobPost(@RequestBody JobPostDto jobPostDto, @PathVariable Integer recruiterId) {
        JobPostDto createdJobPost = jobPostService.createJobPost(jobPostDto, recruiterId);
        return ResponseEntity.ok(createdJobPost);
    }

    // fetch all job post by recruiter
    @GetMapping("/recruiters/jobposts")
    public ResponseEntity<List<JobPostDto>> getAllJobPosts() {
        List<JobPostDto> jobPosts = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPosts);
    }

    // fetch job post according to specific id 
    @GetMapping("/recruiters/{id}/jobposts")
    public ResponseEntity<JobPostDto> getJobPostById(@PathVariable Integer id) {
        JobPostDto jobPost = jobPostService.getJobPostById(id);
        return ResponseEntity.ok(jobPost);
    }

    // update existing job post by recruiter
    @PutMapping("/recruiters/{id}/update-jobpost")
    public ResponseEntity<JobPostDto> updateJobPost(@PathVariable Integer id, @RequestBody JobPostDto jobPostDto) {
        JobPostDto updatedJobPost = jobPostService.updateJobPost(id, jobPostDto);
        return ResponseEntity.ok(updatedJobPost);
    }

    // delete the existing job post created by recruiter
    @DeleteMapping("/recruiters/{id}/delete-jobpost")
    public ResponseEntity<String> deleteJobPost(@PathVariable Integer id) {
        jobPostService.deleteJobPost(id);
        return ResponseEntity.ok("JobPost deleted successfully with ID: " + id);
    }
    //search job by job seeker according to title,location,experience
    @GetMapping("/search")
    public ResponseEntity<List<JobPost>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experience){

        List<JobPost> results = jobPostService.searchJobs(title, location, experience);
        return ResponseEntity.ok(results);
    }

}
