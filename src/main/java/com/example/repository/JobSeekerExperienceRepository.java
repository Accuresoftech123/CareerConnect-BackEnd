package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.Experience;

@Repository
public interface JobSeekerExperienceRepository extends JpaRepository<Experience, Long>{

}
