package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.JobSeekerPersonalInfo;

@Repository
public interface JobSeekerPersonalInfoRepository extends JpaRepository<JobSeekerPersonalInfo, Long> {

}
