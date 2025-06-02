package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.profile.SocialProfile;

@Repository
public interface JobSeekerSocialProfileRepository extends JpaRepository<SocialProfile, Long> {

}
