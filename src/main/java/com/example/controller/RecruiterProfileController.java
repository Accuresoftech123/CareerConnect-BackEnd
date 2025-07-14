package com.example.controller;

import com.example.dto.*;
import com.example.service.RecruiterProfileService;
import com.example.service.RecruiterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruitersProfile")
@CrossOrigin(origins = "http://localhost:3000")
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(RecruiterProfileService recruiterProfileService) {
        this.recruiterProfileService = recruiterProfileService;
    }

    @PostMapping(value = "/create-profile/{recruiterId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createProfile(
            @PathVariable int recruiterId,
            @RequestPart("profileDto") String profileDtoJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        RecruiterProfileDto profileDto = objectMapper.readValue(profileDtoJson, RecruiterProfileDto.class);

        return recruiterProfileService.createProfile(recruiterId, profileDto, imageFile);
    }


    

    @GetMapping("/{recruiterId}")
    public ResponseEntity<RecruiterDTO> getRecruiterProfile(@PathVariable Integer recruiterId) {
        RecruiterDTO recruiterDTO = recruiterProfileService.getRecruiterProfile(recruiterId);
        return ResponseEntity.ok(recruiterDTO);
    }

    @PutMapping("/{recruiterId}")
    public ResponseEntity<RecruiterDTO> updateRecruiterProfile(
            @PathVariable Integer recruiterId,
             @RequestBody RecruiterDTO recruiterDTO) {
        RecruiterDTO updatedProfile = recruiterProfileService.updateRecruiterProfile(recruiterId, recruiterDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<Void> deleteRecruiterProfile(@PathVariable Integer recruiterId) {
        recruiterProfileService.deleteRecruiterProfile(recruiterId);
        return ResponseEntity.noContent().build();
    }

    // Additional endpoints for specific profile sections

    @GetMapping("/{recruiterId}/company-profile")
    public ResponseEntity<CompanyProfileDTO> getCompanyProfile(@PathVariable Integer recruiterId) {
        RecruiterDTO recruiterDTO = recruiterProfileService.getRecruiterProfile(recruiterId);
        return ResponseEntity.ok(recruiterDTO.getCompanyProfile());
    }

    @PutMapping("/{recruiterId}/company-profile")
    public ResponseEntity<CompanyProfileDTO> updateCompanyProfile(
            @PathVariable Integer recruiterId,
            @RequestBody CompanyProfileDTO companyProfileDTO) {
        RecruiterDTO recruiterDTO = new RecruiterDTO();
        recruiterDTO.setCompanyProfile(companyProfileDTO);
        RecruiterDTO updatedProfile = recruiterProfileService.updateRecruiterProfile(recruiterId, recruiterDTO);
        return ResponseEntity.ok(updatedProfile.getCompanyProfile());
    }

    @GetMapping("/{recruiterId}/locations")
    public ResponseEntity<List<CompanyLocationDTO>> getCompanyLocations(@PathVariable Integer recruiterId) {
        RecruiterDTO recruiterDTO = recruiterProfileService.getRecruiterProfile(recruiterId);
        return ResponseEntity.ok(recruiterDTO.getCompanyLocations());
    }

    @PostMapping("/{recruiterId}/locations")
    public ResponseEntity<List<CompanyLocationDTO>> addCompanyLocation(
            @PathVariable Integer recruiterId,
           @RequestBody List<CompanyLocationDTO> locationDTOs) {
        RecruiterDTO recruiterDTO = new RecruiterDTO();
        recruiterDTO.setCompanyLocations(locationDTOs);
        RecruiterDTO updatedProfile = recruiterProfileService.updateRecruiterProfile(recruiterId, recruiterDTO);
        return ResponseEntity.ok(updatedProfile.getCompanyLocations());
    }

    @GetMapping("/{recruiterId}/personal-info")
    public ResponseEntity<RecruiterPersonalInfoDTO> getPersonalInfo(@PathVariable Integer recruiterId) {
        RecruiterDTO recruiterDTO = recruiterProfileService.getRecruiterProfile(recruiterId);
        return ResponseEntity.ok(recruiterDTO.getPersonalInfo());
    }

    @PutMapping("/{recruiterId}/personal-info")
    public ResponseEntity<RecruiterPersonalInfoDTO> updatePersonalInfo(
            @PathVariable Integer recruiterId,
           @RequestBody RecruiterPersonalInfoDTO personalInfoDTO) {
        RecruiterDTO recruiterDTO = new RecruiterDTO();
        recruiterDTO.setPersonalInfo(personalInfoDTO);
        RecruiterDTO updatedProfile = recruiterProfileService.updateRecruiterProfile(recruiterId, recruiterDTO);
        return ResponseEntity.ok(updatedProfile.getPersonalInfo());
    }

    @GetMapping("/{recruiterId}/social-profile")
    public ResponseEntity<RecruiterSocialProfileDTO> getSocialProfile(@PathVariable Integer recruiterId) {
        RecruiterDTO recruiterDTO = recruiterProfileService.getRecruiterProfile(recruiterId);
        return ResponseEntity.ok(recruiterDTO.getSocialProfile());
    }

    @PutMapping("/{recruiterId}/social-profile")
    public ResponseEntity<RecruiterSocialProfileDTO> updateSocialProfile(
            @PathVariable Integer recruiterId,
            @RequestBody RecruiterSocialProfileDTO socialProfileDTO) {
        RecruiterDTO recruiterDTO = new RecruiterDTO();
        recruiterDTO.setSocialProfile(socialProfileDTO);
        RecruiterDTO updatedProfile = recruiterProfileService.updateRecruiterProfile(recruiterId, recruiterDTO);
        return ResponseEntity.ok(updatedProfile.getSocialProfile());
    }
}