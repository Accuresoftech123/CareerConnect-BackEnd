package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ApplicantDTO;
import com.example.entity.Applicant;
import com.example.enums.ApplicationStatus;
import com.example.service.ApplicantService;

@RestController
@RequestMapping("/applications")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/job-seeker/{jobSeekerId}/job-post/{jobPostId}")
    public ResponseEntity<Applicant> applyForJob(
            @RequestBody ApplicantDTO applicantDTO,
            @PathVariable("jobSeekerId") int jobSeekerId,
            @PathVariable("jobPostId") int jobPostId) {
        Applicant application = applicantService.applyForJob(applicantDTO, jobSeekerId, jobPostId);
        return ResponseEntity.ok(application);
    }

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

    @GetMapping("/{applicationId}")
    public ResponseEntity<Applicant> getApplicationById(
            @PathVariable("applicationId") int applicationId) {
        Applicant application = applicantService.getApplicationById(applicationId);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> withdrawApplication(
            @PathVariable("applicationId") int applicationId) {
        applicantService.withdrawApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}