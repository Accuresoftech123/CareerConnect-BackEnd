package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.JobPostDto;
import com.example.service.JobPostService;

@RestController
@RequestMapping("/jobposts")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    // Create
    @PostMapping("/recruiter/{recruiterId}")
    public ResponseEntity<JobPostDto> createJobPost(@RequestBody JobPostDto jobPostDto, @PathVariable Integer recruiterId) {
        JobPostDto createdJobPost = jobPostService.createJobPost(jobPostDto, recruiterId);
        return ResponseEntity.ok(createdJobPost);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<JobPostDto>> getAllJobPosts() {
        List<JobPostDto> jobPosts = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPosts);
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobPostDto> getJobPostById(@PathVariable Integer id) {
        JobPostDto jobPost = jobPostService.getJobPostById(id);
        return ResponseEntity.ok(jobPost);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<JobPostDto> updateJobPost(@PathVariable Integer id, @RequestBody JobPostDto jobPostDto) {
        JobPostDto updatedJobPost = jobPostService.updateJobPost(id, jobPostDto);
        return ResponseEntity.ok(updatedJobPost);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Integer id) {
        jobPostService.deleteJobPost(id);
        return ResponseEntity.ok("JobPost deleted successfully with ID: " + id);
    }
}
