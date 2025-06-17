package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ApplicantDTO;
import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.enums.ApplicationStatus; // Assuming it's in 'enums' package
import com.example.service.ApplicantService;

@RestController
@RequestMapping("/applications")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @PostMapping("/{jobSeekerId}")
    public ResponseEntity<Applicant> applyForJob(@RequestBody ApplicantDTO applicantDTO,
                                                 @PathVariable int jobSeekerId) {
        return ResponseEntity.ok(applicantService.applyForJob(applicantDTO, jobSeekerId));
    }


    @GetMapping("/job/{jobPostId}")
    public ResponseEntity<List<Applicant>> getApplicationsForJob(@PathVariable int jobPostId) {
        return ResponseEntity.ok(applicantService.getApplicationsForJob(jobPostId));
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<Applicant>> getMyApplications(@PathVariable int jobSeekerId) {
        return ResponseEntity.ok(applicantService.getApplicationsByJobSeeker(jobSeekerId));
    }

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<Applicant> updateApplicationStatus(
            @PathVariable int applicationId,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicantService.updateApplicationStatus(applicationId, status));
    }
    
    @PatchMapping("/{applicationId}/shortlist")
    public ResponseEntity<Void> shortlistApplication(@PathVariable int applicationId) {
        applicantService.shortlistApplication(applicationId);
        return ResponseEntity.ok().build();
    }

}
