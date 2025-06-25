package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.LoginWithGoogle;

@Repository
public interface LoginWithGoogleRepo  extends JpaRepository<LoginWithGoogle, Long> {
    Optional<LoginWithGoogle> findByEmail(String email);



}
