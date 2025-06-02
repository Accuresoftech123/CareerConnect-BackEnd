package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.Education;

@Repository
public interface JobSeekerEducationRepository extends JpaRepository<Education, Long>{

}
