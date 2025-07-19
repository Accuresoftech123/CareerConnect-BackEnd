package com.example.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApplicantDTO;
import com.example.dto.RecruiterDashboardSummaryDto;
import com.example.entity.Applicant;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.ApplicationStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.RecruiterRepository;
import com.example.service.ApplicantService;
import com.example.service.RecruiterDashboardService;



@RestController
@RequestMapping("/api/recruiters/dashboard")
public class RecruiterDashboardController {
	
	
	

    @Autowired
    private RecruiterDashboardService dashboardService;
    
    @Autowired
    RecruiterRepository RecRepo;
    
    // ✅ Dashboard Summary
    @GetMapping("/summary/{recruiterId}")  
    public ResponseEntity<RecruiterDashboardSummaryDto> getDashboardSummary(@PathVariable Integer recruiterId) {
        Recruiter recruiter = RecRepo.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + recruiterId));
        
        return ResponseEntity.ok(dashboardService.getDashboardSummary(recruiter));
    }

    
 
 // ✅ Recent Applicants
    @GetMapping("/recent-applicants")   
    public ResponseEntity<List<ApplicantDTO>> getRecentApplicants(
            @RequestParam("recruiterId") int recruiterId) {
        try {
            List<ApplicantDTO> recentApplicants = dashboardService.getRecentApplicants(recruiterId);
            return ResponseEntity.ok(recentApplicants);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // Return empty list with bad request status
            return ResponseEntity.badRequest().body(Collections.emptyList());
            // OR if you want to include the error message:
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
 
    // ✅ Active Job Posts
    @GetMapping("/job-posts/active")    
    public ResponseEntity<List<JobPost>> getActiveJobPosts(
             Recruiter recruiter) {
        return ResponseEntity.ok(dashboardService.getActiveJobPosts(recruiter));
 }

    

    
//  
}