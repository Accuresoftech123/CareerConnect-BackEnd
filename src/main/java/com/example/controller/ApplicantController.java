package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ApplicantDTO;
import com.example.dto.SavedJobPostReportDto;
import com.example.entity.Applicant;
import com.example.enums.ApplicationStatus;
import com.example.service.ApplicantService;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/applyjob/{jobSeekerId}/job-post/{jobPostId}")
    public ResponseEntity<ApplicantDTO> applyForJob(
            @PathVariable int jobSeekerId,
            @PathVariable int jobPostId) {
        
        ApplicantDTO dto = applicantService.applyForJob(jobSeekerId, jobPostId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //get alll application applied job
    @GetMapping("/job-post/{jobPostId}")
    public ResponseEntity<List<Applicant>> getApplicationsForJob(
            @PathVariable("jobPostId") int jobPostId) {
        List<Applicant> applications = applicantService.getApplicationsForJob(jobPostId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job-seeker/{jobSeekerId}")
    public ResponseEntity<List<Applicant>> getMyApplications(
            @PathVariable("jobSeekerId") int jobSeekerId) {
        List<Applicant> applications = applicantService.getApplicationsByJobSeeker(jobSeekerId);
        return ResponseEntity.ok(applications);
    }

    @PatchMapping("/{applicationId}/status/{status}")
    public ResponseEntity<Applicant> updateApplicationStatus(
            @PathVariable("applicationId") int applicationId,
            @PathVariable("status") ApplicationStatus status) {
        Applicant updatedApplication = applicantService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updatedApplication);
    }
    
    @PatchMapping("/{applicationId}/shortlist")
    public ResponseEntity<Void> shortlistApplication(
            @PathVariable("applicationId") int applicationId) {
        applicantService.shortlistApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    //get appllications how many jobs appllied by recruiter
    @GetMapping("/{applicationId}")
    public ResponseEntity<Applicant> getApplicationById(
            @PathVariable("applicationId") int applicationId) {
        Applicant application = applicantService.getApplicationById(applicationId);
        return ResponseEntity.ok(application);
    }

    //withdrw application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> withdrawApplication(
            @PathVariable("applicationId") int applicationId) {
        applicantService.withdrawApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
    
    
    // get appied job by jobseeker
//    @GetMapping("/jobseeker/{jobSeekerId}/applied-jobs")
//    public ResponseEntity<List<SavedJobPostReportDto>> getAppliedJobs(@PathVariable int jobSeekerId) {
//        List<SavedJobPostReportDto> jobs = applicantService.getAppliedJobsByJobSeeker(jobSeekerId);
//        return ResponseEntity.ok(jobs);
//    }
    
    // count of applied jobes  by jobseeker
    @GetMapping("/jobseeker/{jobSeekerId}/applied-jobs/count")
    public ResponseEntity<Long> getAppliedJobsCount(@PathVariable int jobSeekerId) {
        long count = applicantService.countAppliedJobs(jobSeekerId);
        return ResponseEntity.ok(count);
    }

}