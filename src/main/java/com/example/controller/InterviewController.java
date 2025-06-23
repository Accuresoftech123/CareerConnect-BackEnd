package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.InterviewDTO;
import com.example.entity.Recruiter;
import com.example.enums.InterviewStatus;
import com.example.service.InterviewService;

@RestController
@RequestMapping("/recruiter/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;
    
//    @GetMapping("/upcoming")
//    public ResponseEntity<List<InterviewDTO>> getUpcomingInterviews(
//            @AuthenticationPrincipal Recruiter recruiter) {
//        return ResponseEntity.ok(interviewService.getUpcomingInterviews(recruiter));
//    }
//    
    @PostMapping
    public ResponseEntity<InterviewDTO> scheduleInterview(
            @RequestBody InterviewDTO interviewDTO,
             Recruiter recruiter) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(interviewService.scheduleInterview(interviewDTO));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<InterviewDTO> updateInterviewStatus(
            @PathVariable int id,
            @RequestParam InterviewStatus status) {
        return ResponseEntity.ok(interviewService.updateInterviewStatus(id, status));
    }
    
//    @PutMapping("/{id}/feedback")
//    public ResponseEntity<InterviewDTO> updateInterviewFeedback(
//            @PathVariable int id,
//            @RequestParam String feedback) {
//    	
//        // Similar implementation to update status
//    }
}