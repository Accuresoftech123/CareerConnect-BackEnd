package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.InterviewDTO;
import com.example.entity.Applicant;
import com.example.entity.Interview;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.InterviewStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ApplicantRepository;
import com.example.repository.InterviewRepository;
import com.example.repository.JobPostRepository;

@Service
public class InterviewService {

    @Autowired   
    private InterviewRepository interviewRepository;
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    @Autowired
    private JobPostRepository jobPostRepository;
    
//    public List<InterviewDTO> getUpcomingInterviews(Recruiter recruiter) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime end = now.plusDays(14);
//        
//        return interviewRepository.findByJobPost_RecruiterAndInterviewDateTimeBetweenAndStatus(
//            recruiter, now, end, InterviewStatus.SCHEDULED)
//            .stream()
//            .map(this::convertToDTO)
//            .collect(Collectors.toList());
//    }
    
    public InterviewDTO scheduleInterview(InterviewDTO interviewDTO) {
        Applicant applicant = applicantRepository.findById(interviewDTO.getApplicantId())
            .orElseThrow(() -> new ResourceNotFoundException("Applicant not found"));
        
        JobPost jobPost = jobPostRepository.findById(interviewDTO.getJobPostId())
            .orElseThrow(() -> new ResourceNotFoundException("Job post not found"));
        
        Interview interview = new Interview();
        interview.setApplicant(applicant);
        interview.setJobPost(jobPost);
        interview.setInterviewDateTime(interviewDTO.getInterviewDateTime());
        interview.setRound(interviewDTO.getRound());
        interview.setInterviewer(interviewDTO.getInterviewer());
        interview.setStatus(InterviewStatus.SCHEDULED);
        
        Interview savedInterview = interviewRepository.save(interview);
        return convertToDTO(savedInterview);
    }
    
    public InterviewDTO updateInterviewStatus(int id, InterviewStatus status) {
        Interview interview = interviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));
        
        interview.setStatus(status);
        Interview updatedInterview = interviewRepository.save(interview);
        return convertToDTO(updatedInterview);
    }
    
    private InterviewDTO convertToDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        dto.setApplicantId(interview.getApplicant().getId());
        dto.setApplicantName(interview.getApplicant().getJobSeeker().getFullName());
        dto.setJobPostId(interview.getJobPost().getId());
        dto.setJobTitle(interview.getJobPost().getTitle());
        dto.setInterviewDateTime(interview.getInterviewDateTime());
        dto.setRound(interview.getRound());
        dto.setInterviewer(interview.getInterviewer());
        dto.setFeedback(interview.getFeedback());
        dto.setStatus(interview.getStatus().toString());
        return dto;
    }
}