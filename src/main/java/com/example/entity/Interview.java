package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.entity.jobposting.JobPost;
import com.example.enums.InterviewStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;
    
  
    private LocalDateTime interviewDateTime;
    
    
    private String round; // Technical round 1, Technical round 2, HR Introduction
    
    private String interviewer;
    private String feedback;
    
    @Enumerated(EnumType.STRING)
    private InterviewStatus status; // SCHEDULED, COMPLETED, CANCELLED
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant applicant) { this.applicant = applicant; }
    
    public JobPost getJobPost() { return jobPost; }
    public void setJobPost(JobPost jobPost) { this.jobPost = jobPost; }
    
    public LocalDateTime getInterviewDateTime() { return interviewDateTime; }
    public void setInterviewDateTime(LocalDateTime interviewDateTime) { this.interviewDateTime = interviewDateTime; }
    
    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }
    
    public String getInterviewer() { return interviewer; }
    public void setInterviewer(String interviewer) { this.interviewer = interviewer; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    public InterviewStatus getStatus() { return status; }
    public void setStatus(InterviewStatus status) { this.status = status; }
}

