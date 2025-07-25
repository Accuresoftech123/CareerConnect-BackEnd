package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.Experience;

@Repository
public interface JobSeekerExperienceRepository extends JpaRepository<Experience, Long>{
	 List<Experience> findByJobSeekerId(int jobSeekerId);
}
