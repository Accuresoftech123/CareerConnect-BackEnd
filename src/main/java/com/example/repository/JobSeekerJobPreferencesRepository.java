package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.JobPreferences;

@Repository
public interface JobSeekerJobPreferencesRepository extends JpaRepository<JobPreferences, Long> {

}
