package com.example.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.JobPostDto;
import com.example.dto.RecommendedJobPostDto;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.JobPostStatus;
import com.example.repository.JobPostRepository;
import com.example.service.ApplicantService;
import com.example.service.JobPostService;

@RestController
@RequestMapping("/api/jobposts")
@CrossOrigin(origins = "http://localhost:3000")

public class JobPostController {

    @Autowired
    private JobPostService jobPostService;


    // create a job post by recruiter
    @PostMapping("/recruiter/{recruiterId}")
    public ResponseEntity<JobPostDto> createJobPost(@RequestBody JobPostDto jobPostDto, @PathVariable Integer recruiterId) {
        JobPostDto createdJobPost = jobPostService.createJobPost(jobPostDto, recruiterId); // Pass recruiterId separately
        return ResponseEntity.ok(createdJobPost);
    }


    // fetch all job post by recruiter
    @GetMapping("/all-jobs/{jobSeekerId}")
    public ResponseEntity<List<JobPostDto>> getAllJobPostsWithBookmarks(@PathVariable int jobSeekerId) {
        List<JobPostDto> jobs = jobPostService.getAllJobPostsWithBookmarks(jobSeekerId);
        return ResponseEntity.ok(jobs);
    }

    // fetch job post according to specific id 
    @GetMapping("/jobposts/{id}")
    public ResponseEntity<JobPostDto> getJobPostById(@PathVariable Integer id) {
        JobPostDto jobPost = jobPostService.getJobPostById(id);
        return ResponseEntity.ok(jobPost);
    }
    

    // update existing job post by recruiter
    @PutMapping("/recruiters/{id}")
    public ResponseEntity<JobPostDto> updateJobPost(@PathVariable Integer id, @RequestBody JobPostDto jobPostDto) {
        JobPostDto updatedJobPost = jobPostService.updateJobPost(id, jobPostDto);
        return ResponseEntity.ok(updatedJobPost);
    }

    // delete the existing job post created by recruiter
    @DeleteMapping("/recruiters/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Integer id) {
        jobPostService.deleteJobPost(id);
        return ResponseEntity.ok("JobPost deleted successfully with ID: " + id);
    }
    // ✅ 6. Search jobs
    @GetMapping("/search")
    public ResponseEntity<List<JobPost>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experience){

        List<JobPost> results = jobPostService.searchJobs(title, location, experience);
        return ResponseEntity.ok(results);
    }

    
    // ✅ Close job post
    @PutMapping("/{jobId}/close")
    public ResponseEntity<Void> closeJobPost(@PathVariable Long jobId, @RequestParam Integer recruiterId) {
        jobPostService.closeJobPost(jobId, recruiterId);
        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/active")
    public ResponseEntity<List<JobPostDto>> getAllActiveJobPostsForApplicants() {
        List<JobPostDto> activePosts = jobPostService.getAllActiveJobPostsForApplicants();
        return ResponseEntity.ok(activePosts);
    }
    
    @GetMapping("/{id}/active")
    public ResponseEntity<JobPostDto> getActiveJobPostById(@PathVariable Integer id) {
        JobPostDto jobPostDto = jobPostService.getActiveJobPostById(id);
        return ResponseEntity.ok(jobPostDto);
    }
    
 // Get all closed job posts
    @GetMapping("/closed")
    public ResponseEntity<List<JobPostDto>> getAllClosedJobPosts() {
        List<JobPostDto> closedJobs = jobPostService.getAllClosedJobPosts();
        return ResponseEntity.ok(closedJobs);
    }

 // Get closed jobs by recruiter ID
    @GetMapping("/recruiters/{recruiterId}/closed")
    public ResponseEntity<List<JobPostDto>> getClosedJobsByRecruiter(
            @PathVariable Integer recruiterId) {
        List<JobPostDto> closedJobs = jobPostService.getClosedJobsByRecruiter(recruiterId);
        return ResponseEntity.ok(closedJobs);
    }
    
 // Get closed jobs with filters


    @GetMapping("/closed/filtered")
    public ResponseEntity<?> getClosedJobsWithFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary) {
        
        try {
            List<JobPostDto> filteredJobs = jobPostService.getClosedJobsWithFilters(
                title, 
                minSalary, 
                maxSalary
            );
            return ResponseEntity.ok(filteredJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error processing request: " + e.getMessage());
        }
    }
    
 // Get jobs closed before a specific date
    @GetMapping("/closed/before-date")
    public ResponseEntity<List<JobPostDto>> getJobsClosedBeforeDate(
            @RequestParam String date) { // Format: "yyyy-MM-dd"
        
        LocalDate localDate = LocalDate.parse(date);
        List<JobPostDto> jobs = jobPostService.getJobsClosedBeforeDate(localDate);
        return ResponseEntity.ok(jobs);
    }

   //get a latest job post added by recruiter 
    @GetMapping("/recruiter/{recruiterId}/last")
    public ResponseEntity<JobPostDto> getLastAddedJobPost(
            @PathVariable Integer recruiterId) {
        Optional<JobPostDto> lastPost = jobPostService.getLastAddedJobPost(recruiterId);
        return lastPost.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.ok().build());
    }
    
    
    //save as draft post
    @PostMapping("/recruiter/{recruiterId}/save-draft")
    public ResponseEntity<JobPostDto> saveJobPostAsDraft(@RequestBody JobPostDto jobPostDto,
                                                        @PathVariable Integer recruiterId) {
        JobPostDto draftJobPost = jobPostService.saveJobPostAsDraft(jobPostDto, recruiterId);
        return ResponseEntity.ok(draftJobPost);
    }
    
    @GetMapping("/recruiter/{recruiterId}/drafts")
    public ResponseEntity<List<JobPostDto>> getDraftJobPosts(@PathVariable Integer recruiterId) {
        List<JobPostDto> drafts = jobPostService.getDraftJobPostsByRecruiter(recruiterId);
        return ResponseEntity.ok(drafts);
    }
    

    
    //shoertlist applicants
    @PutMapping("/applicants/{applicantId}/shortlist")
    public ResponseEntity<String> shortlistApplicant(@PathVariable Integer applicantId) {
        jobPostService.shortlistApplicant(applicantId);
        return ResponseEntity.ok("Applicant with ID " + applicantId + " has been shortlisted.");
    }
    
    
 // Add endpoint for previous jobs
    @GetMapping("/recruiter/{recruiterId}/previous-jobs")
    public ResponseEntity<List<JobPostDto>> getPreviousJobPosts(
            @PathVariable Integer recruiterId) {
        List<JobPostDto> previousJobs = jobPostService.getPreviousJobPostsByRecruiter(recruiterId);
        return ResponseEntity.ok(previousJobs);
    }

    //for recommended jobs 
    @GetMapping("/jobseeker/{jobSeekerId}/recommended")
    public ResponseEntity<List<RecommendedJobPostDto>> getRecommendedJobs(@PathVariable int jobSeekerId) {
        List<RecommendedJobPostDto> recommendedJobs = jobPostService.getRecommendedJobsForJobSeeker(jobSeekerId);
        return ResponseEntity.ok(recommendedJobs);
    }

    //TO FEATCH COUNT
   
    @GetMapping("/active/count")
    public ResponseEntity<Long> getActiveJobPostCount() {
        return ResponseEntity.ok(jobPostService.getActiveJobPostCount());
    }


    @GetMapping("/close/count")
    public ResponseEntity<Long> GetcloseJobPost() {
        return ResponseEntity.ok(jobPostService.getCloseJobPost());
    }
//Get List of matching JobPost
    @GetMapping("/today-matches/{jobSeekerId}")
    public ResponseEntity<List<JobPost>> getTodayMatches(@PathVariable int jobSeekerId) {
        List<JobPost> jobs = jobPostService.getTodayJobMatchesListForSeeker(jobSeekerId);
        return ResponseEntity.ok(jobs);
    }
// Get Count of Matching JobPost Count
    @GetMapping("/today-matches-count/{jobSeekerId}")
    public ResponseEntity<Long> getTodayMatchesCount(@PathVariable int jobSeekerId) {
        Long count = jobPostService.getTodayJobMatchesCountForSeeker(jobSeekerId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/jobposts/recent/count")
    public ResponseEntity<Long> countRecentJobPosts() {
        return ResponseEntity.ok(jobPostService.countRecentJobPosts());
    }


    
    // count total jobpost by recruiter
    @GetMapping("/jobpost-count/{recruiterId}")
    public ResponseEntity<Long> getJobPostCount(@PathVariable int recruiterId) {
        long count = jobPostService.getTotalJobPostsByRecruiter(recruiterId);
        return ResponseEntity.ok(count);
    }
}
