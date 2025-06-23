package com.example.dto;

import java.time.LocalDateTime;

public class InterviewDTO {
    private int id;
    private int applicantId;
    private String applicantName;
    private Integer jobPostId;
    private String jobTitle;
    private LocalDateTime interviewDateTime;
    private String round;
    private String interviewer;
    private String feedback;
    private String status;
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getApplicantId() { return applicantId; }
    public void setApplicantId(int applicantId) { this.applicantId = applicantId; }
    
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    
    public Integer getJobPostId() { return jobPostId; }
    public void setJobPostId(Integer jobPostId) { this.jobPostId = jobPostId; }
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public LocalDateTime getInterviewDateTime() { return interviewDateTime; }
    public void setInterviewDateTime(LocalDateTime interviewDateTime) { this.interviewDateTime = interviewDateTime; }
    
    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }
    
    public String getInterviewer() { return interviewer; }
    public void setInterviewer(String interviewer) { this.interviewer = interviewer; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}