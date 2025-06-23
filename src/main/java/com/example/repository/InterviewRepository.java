package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Applicant;
import com.example.entity.Interview;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.InterviewStatus;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {
    List<Interview> findByJobPost_Recruiter(Recruiter recruiter);
    List<Interview> findByApplicant(Applicant applicant);
    List<Interview> findByJobPost(JobPost jobPost);
    List<Interview> findByInterviewDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Interview> findByStatus(InterviewStatus status);
}