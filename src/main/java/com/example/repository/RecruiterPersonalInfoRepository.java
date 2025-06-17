package com.example.repository;

import com.example.entity.profile.RecruiterPersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterPersonalInfoRepository extends JpaRepository<RecruiterPersonalInfo, Integer> {
}