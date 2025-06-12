package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.JobPostDto;
import com.example.entity.jobposting.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    
}
