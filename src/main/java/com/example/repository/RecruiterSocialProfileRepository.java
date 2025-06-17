package com.example.repository;

import com.example.entity.profile.RecruiterSocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterSocialProfileRepository extends JpaRepository<RecruiterSocialProfile, Long> {
}