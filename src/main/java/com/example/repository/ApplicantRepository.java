package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    List<Applicant> findByJobPost(JobPost jobPost);
    List<Applicant> findByJobSeeker(JobSeeker jobSeeker);
    boolean existsByJobPostAndJobSeeker(JobPost jobPost, JobSeeker jobSeeker);
    long countByJobPost(JobPost jobPost);
    List<Applicant> findByJobPost_Recruiter_Id(int recruiterId);

}