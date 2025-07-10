package com.example.service;

import com.example.dto.*;
import com.example.entity.*;
import com.example.entity.profile.*;
import com.example.enums.Status;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.*;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecruiterProfileService {

    @Autowired
    private RecruiterRepository recruiterRepository;
    
    @Autowired
    private CompanyProfileRepository companyProfileRepository;
    
    @Autowired
    private CompanyLocationRepository companyLocationRepository;
    
    @Autowired
    private RecruiterPersonalInfoRepository personalInfoRepository;
    
    @Autowired
    private RecruiterSocialProfileRepository socialProfileRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;
  
    public ResponseEntity<Map<String, Object>> createProfile(int recruiterId, RecruiterProfileDto dto, MultipartFile imageFile)
{
        Map<String, Object> response = new HashMap<>();

        /// 1. Fetch recruiter by ID
        Optional<Recruiter> existingRecruiter = recruiterRepository.findById(recruiterId);
        
        if (!existingRecruiter.isPresent()) {
            response.put("success", false);
            response.put("message", "Recruiter not found with ID: " + recruiterId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // 2. Proceed with profile creation since email is registered
        Recruiter recruiter = existingRecruiter.get();
     // Update recruiter details
        recruiter.setFullName(dto.getFullName());
        // Handle company profile
        CompanyProfile companyProfile = recruiter.getCompanyProfile() != null ? 
            recruiter.getCompanyProfile() : new CompanyProfile();
        
        companyProfile.setCompanyName(dto.getCompanyProfile().getCompanyName());
        companyProfile.setWebsite(dto.getCompanyProfile().getWebsite());
        companyProfile.setIndustryType(dto.getCompanyProfile().getIndustryType());
        companyProfile.setAbout(dto.getCompanyProfile().getAbout());
        companyProfile.setImg(dto.getCompanyProfile().getImg());
        companyProfile.setCompanyEmail(dto.getCompanyProfile().getCompanyEmail());
        companyProfile.setCompanySize(dto.getCompanyProfile().getCompanySize());
        companyProfile.setFoundingYear(dto.getCompanyProfile().getFoundingYear());
        companyProfile.setHrContactEmail(dto.getCompanyProfile().getHrContactEmail());
        companyProfile.setHrContactMobileNumber(dto.getCompanyProfile().getHrContactMobileNumber());
        
     // Upload image to Cloudinary
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadedImageUrl = cloudinaryService.uploadFile(imageFile, "recruiter/company_profiles");
                companyProfile.setImg(uploadedImageUrl);
            }
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error uploading image to Cloudinary: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        companyProfile.setRecruiter(recruiter);
        recruiter.setCompanyProfile(companyProfile);// Handle locations (clear existing and add new)
        recruiter.getCompanyLocations().clear();
        dto.getCompanyLocation().forEach(locationDto -> {
            CompanyLocation location = new CompanyLocation();
            location.setCity(locationDto.getCity());
            location.setState(locationDto.getState());
            location.setCountry(locationDto.getCountry());
            location.setAddress(locationDto.getAddress());
            location.setRecruiter(recruiter);
            recruiter.getCompanyLocations().add(location);
        });

        recruiterRepository.save(recruiter);

        response.put("success", true);
        response.put("message", "Profile updated successfully");
        return ResponseEntity.ok(response);
    }

    
    
    

    
    

    public RecruiterDTO getRecruiterProfile(Integer recruiterId) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + recruiterId));
        return convertToDTO(recruiter);
    }
    @Transactional
    public RecruiterDTO updateRecruiterProfile(Integer recruiterId, RecruiterDTO recruiterDTO) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + recruiterId));

        // Update basic info
        if (recruiterDTO.getFullName() != null) {
            recruiter.setFullName(recruiterDTO.getFullName());
        }
        if (recruiterDTO.getMobileNumber() != 0) {
            recruiter.setMobileNumber(recruiterDTO.getMobileNumber());
        }

        // Update company profile
        if (recruiterDTO.getCompanyProfile() != null) {
            updateCompanyProfile(recruiter, recruiterDTO.getCompanyProfile());
        }

        // Update company locations
        if (recruiterDTO.getCompanyLocations() != null) {
            updateCompanyLocations(recruiter, recruiterDTO.getCompanyLocations());
        }

        // Update industries
        if (recruiterDTO.getIndustries() != null) {
            recruiter.setIndustries(recruiterDTO.getIndustries());
        }

        // Update personal info
        if (recruiterDTO.getPersonalInfo() != null) {
            updatePersonalInfo(recruiter, recruiterDTO.getPersonalInfo());
        }

        // Update social profile
        if (recruiterDTO.getSocialProfile() != null) {
            updateSocialProfile(recruiter, recruiterDTO.getSocialProfile());
        }

        recruiter = recruiterRepository.save(recruiter);
        return convertToDTO(recruiter);
    }

    @Transactional
    public void deleteRecruiterProfile(Integer recruiterId) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + recruiterId));
        
        // Delete all related entities
        if (recruiter.getCompanyProfile() != null) {
            companyProfileRepository.delete(recruiter.getCompanyProfile());
        }
        
        if (recruiter.getCompanyLocations() != null && !recruiter.getCompanyLocations().isEmpty()) {
            companyLocationRepository.deleteAll(recruiter.getCompanyLocations());
        }
        
        if (recruiter.getPersonalInfo() != null) {
            personalInfoRepository.delete(recruiter.getPersonalInfo());
        }
        
        if (recruiter.getSocialProfile() != null) {
            socialProfileRepository.delete(recruiter.getSocialProfile());
        }
        
        recruiterRepository.delete(recruiter);
    }

    // Helper methods for saving related entities
    private void saveCompanyProfile(Recruiter recruiter, CompanyProfileDTO companyProfileDTO) {
        CompanyProfile companyProfile = new CompanyProfile();
        companyProfile.setCompanyName(companyProfileDTO.getCompanyName());
        companyProfile.setWebsite(companyProfileDTO.getWebsite());
        companyProfile.setAbout(companyProfileDTO.getAbout());
        companyProfile.setFoundingYear(companyProfileDTO.getFoundingYear());
        companyProfile.setCompanySize(companyProfileDTO.getCompanySize());
        companyProfile.setRecruiter(recruiter);
        companyProfileRepository.save(companyProfile);
        recruiter.setCompanyProfile(companyProfile);
    }

    private void saveCompanyLocations(Recruiter recruiter, List<CompanyLocationDTO> locationDTOs) {
        List<CompanyLocation> locations = locationDTOs.stream()
                .map(dto -> {
                    CompanyLocation location = new CompanyLocation();
                    location.setAddress(dto.getAddress());
                    location.setCity(dto.getCity());
                    location.setState(dto.getState());
                    location.setCountry(dto.getCountry());
                    location.setPostalCode(dto.getPostalCode());
                    location.setHeadquarters(dto.isHeadquarters());
                    location.setRecruiter(recruiter);
                    return location;
                })
                .collect(Collectors.toList());
        
        companyLocationRepository.saveAll(locations);
        recruiter.setCompanyLocations(locations);
    }

    private void savePersonalInfo(Recruiter recruiter, RecruiterPersonalInfoDTO personalInfoDTO) {
        RecruiterPersonalInfo personalInfo = new RecruiterPersonalInfo();
        personalInfo.setJobTitle(personalInfoDTO.getJobTitle());
        personalInfo.setDepartment(personalInfoDTO.getDepartment());
        personalInfo.setHireDate(personalInfoDTO.getHireDate());
        personalInfo.setRecruiter(recruiter);
        personalInfoRepository.save(personalInfo);
        recruiter.setPersonalInfo(personalInfo);
    }

    private void saveSocialProfile(Recruiter recruiter, RecruiterSocialProfileDTO socialProfileDTO) {
        RecruiterSocialProfile socialProfile = new RecruiterSocialProfile();
        socialProfile.setLinkedinUrl(socialProfileDTO.getLinkedinUrl());
        socialProfile.setGithubUrl(socialProfileDTO.getGithubUrl());
        socialProfile.setPortfolioWebsite(socialProfileDTO.getPortfolioWebsite());
        socialProfile.setTwitterUrl(socialProfileDTO.getTwitterUrl());
        socialProfile.setFacebookUrl(socialProfileDTO.getFacebookUrl());
        socialProfile.setOtherUrl(socialProfileDTO.getOtherUrl());
        socialProfile.setRecruiter(recruiter);
        socialProfileRepository.save(socialProfile);
        recruiter.setSocialProfile(socialProfile);
    }

    // Helper methods for updating related entities
    private void updateCompanyProfile(Recruiter recruiter, CompanyProfileDTO companyProfileDTO) {
        CompanyProfile companyProfile = recruiter.getCompanyProfile();
        if (companyProfile == null) {
            saveCompanyProfile(recruiter, companyProfileDTO);
            return;
        }

        if (companyProfileDTO.getCompanyName() != null) {
            companyProfile.setCompanyName(companyProfileDTO.getCompanyName());
        }
        if (companyProfileDTO.getWebsite() != null) {
            companyProfile.setWebsite(companyProfileDTO.getWebsite());
        }
        if (companyProfileDTO.getAbout() != null) {
            companyProfile.setAbout(companyProfileDTO.getAbout());
        }
        if (companyProfileDTO.getFoundingYear() != null) {
            companyProfile.setFoundingYear(companyProfileDTO.getFoundingYear());
        }
        if (companyProfileDTO.getCompanySize() != null) {
            companyProfile.setCompanySize(companyProfileDTO.getCompanySize());
        }
        
        companyProfileRepository.save(companyProfile);
    }

    private void updateCompanyLocations(Recruiter recruiter, List<CompanyLocationDTO> locationDTOs) {
        // First remove existing locations not in the new list
        List<Integer> newLocationIds = locationDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .map(CompanyLocationDTO::getId)
                .collect(Collectors.toList());
        
        recruiter.getCompanyLocations().removeIf(location -> !newLocationIds.contains(location.getId()));
        
        // Update existing or add new locations
        for (CompanyLocationDTO dto : locationDTOs) {
            if (dto.getId() == null) {
                // New location
                CompanyLocation location = new CompanyLocation();
                location.setAddress(dto.getAddress());
                location.setCity(dto.getCity());
                location.setState(dto.getState());
                location.setCountry(dto.getCountry());
                location.setPostalCode(dto.getPostalCode());
                location.setHeadquarters(dto.isHeadquarters());
                location.setRecruiter(recruiter);
                companyLocationRepository.save(location);
                recruiter.getCompanyLocations().add(location);
            } else {
                // Existing location
                CompanyLocation location = companyLocationRepository.findById(dto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Company location not found with id: " + dto.getId()));
                if (dto.getAddress() != null) {
                    location.setAddress(dto.getAddress());
                }
                if (dto.getCity() != null) {
                    location.setCity(dto.getCity());
                }
                if (dto.getState() != null) {
                    location.setState(dto.getState());
                }
                if (dto.getCountry() != null) {
                    location.setCountry(dto.getCountry());
                }
                if (dto.getPostalCode() != null) {
                    location.setPostalCode(dto.getPostalCode());
                }
                location.setHeadquarters(dto.isHeadquarters());
                companyLocationRepository.save(location);
            }
        }
    }

    private void updatePersonalInfo(Recruiter recruiter, RecruiterPersonalInfoDTO personalInfoDTO) {
        RecruiterPersonalInfo personalInfo = recruiter.getPersonalInfo();
        if (personalInfo == null) {
            savePersonalInfo(recruiter, personalInfoDTO);
            return;
        }

        if (personalInfoDTO.getJobTitle() != null) {
            personalInfo.setJobTitle(personalInfoDTO.getJobTitle());
        }
        if (personalInfoDTO.getDepartment() != null) {
            personalInfo.setDepartment(personalInfoDTO.getDepartment());
        }
        if (personalInfoDTO.getHireDate() != null) {
            personalInfo.setHireDate(personalInfoDTO.getHireDate());
        }
        
        personalInfoRepository.save(personalInfo);
    }

    private void updateSocialProfile(Recruiter recruiter, RecruiterSocialProfileDTO socialProfileDTO) {
        RecruiterSocialProfile socialProfile = recruiter.getSocialProfile();
        if (socialProfile == null) {
            saveSocialProfile(recruiter, socialProfileDTO);
            return;
        }

        if (socialProfileDTO.getLinkedinUrl() != null) {
            socialProfile.setLinkedinUrl(socialProfileDTO.getLinkedinUrl());
        }
        if (socialProfileDTO.getGithubUrl() != null) {
            socialProfile.setGithubUrl(socialProfileDTO.getGithubUrl());
        }
        if (socialProfileDTO.getPortfolioWebsite() != null) {
            socialProfile.setPortfolioWebsite(socialProfileDTO.getPortfolioWebsite());
        }
        if (socialProfileDTO.getTwitterUrl() != null) {
            socialProfile.setTwitterUrl(socialProfileDTO.getTwitterUrl());
        }
        if (socialProfileDTO.getFacebookUrl() != null) {
            socialProfile.setFacebookUrl(socialProfileDTO.getFacebookUrl());
        }
        if (socialProfileDTO.getOtherUrl() != null) {
            socialProfile.setOtherUrl(socialProfileDTO.getOtherUrl());
        }
        
        socialProfileRepository.save(socialProfile);
    }

    // Conversion methods between entities and DTOs
    private RecruiterDTO convertToDTO(Recruiter recruiter) {
        RecruiterDTO dto = new RecruiterDTO();
        dto.setId(recruiter.getId());
        dto.setFullName(recruiter.getFullName());
        dto.setEmail(recruiter.getEmail());
        dto.setMobileNumber(recruiter.getMobileNumber());
        dto.setIsVerified(recruiter.isVerified());
        dto.setIsMobileVerified(recruiter.isMobileVerified());
        dto.setStatus(recruiter.getStatus());
        
        if (recruiter.getCompanyProfile() != null) {
            dto.setCompanyProfile(convertToDTO(recruiter.getCompanyProfile()));
        }
        
        if (recruiter.getCompanyLocations() != null && !recruiter.getCompanyLocations().isEmpty()) {
            dto.setCompanyLocations(recruiter.getCompanyLocations().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));
        }
        
        if (recruiter.getIndustries() != null) {
            dto.setIndustries(recruiter.getIndustries());
        }
        
        if (recruiter.getPersonalInfo() != null) {
            dto.setPersonalInfo(convertToDTO(recruiter.getPersonalInfo()));
        }
        
        if (recruiter.getSocialProfile() != null) {
            dto.setSocialProfile(convertToDTO(recruiter.getSocialProfile()));
        }
        
        return dto;
    }

    private CompanyProfileDTO convertToDTO(CompanyProfile companyProfile) {
        CompanyProfileDTO dto = new CompanyProfileDTO();
        dto.setId(companyProfile.getId());
        dto.setCompanyName(companyProfile.getCompanyName());
        dto.setWebsite(companyProfile.getWebsite());
        dto.setAbout(companyProfile.getAbout());
        dto.setFoundingYear(companyProfile.getFoundingYear());
        dto.setCompanySize(companyProfile.getCompanySize());
        return dto;
    }

    private CompanyLocationDTO convertToDTO(CompanyLocation location) {
        CompanyLocationDTO dto = new CompanyLocationDTO();
        dto.setId(location.getId());
        dto.setAddress(location.getAddress());
        dto.setCity(location.getCity());
        dto.setState(location.getState());
        dto.setCountry(location.getCountry());
        dto.setPostalCode(location.getPostalCode());
        dto.setHeadquarters(location.isHeadquarters());
        return dto;
    }

    private RecruiterPersonalInfoDTO convertToDTO(RecruiterPersonalInfo personalInfo) {
        RecruiterPersonalInfoDTO dto = new RecruiterPersonalInfoDTO();
        dto.setId(personalInfo.getId());
        dto.setJobTitle(personalInfo.getJobTitle());
        dto.setDepartment(personalInfo.getDepartment());
        dto.setHireDate(personalInfo.getHireDate());
        return dto;
    }

    private RecruiterSocialProfileDTO convertToDTO(RecruiterSocialProfile socialProfile) {
        RecruiterSocialProfileDTO dto = new RecruiterSocialProfileDTO();
        dto.setId(socialProfile.getId());
        dto.setLinkedinUrl(socialProfile.getLinkedinUrl());
        dto.setGithubUrl(socialProfile.getGithubUrl());
        dto.setPortfolioWebsite(socialProfile.getPortfolioWebsite());
        dto.setTwitterUrl(socialProfile.getTwitterUrl());
        dto.setFacebookUrl(socialProfile.getFacebookUrl());
        dto.setOtherUrl(socialProfile.getOtherUrl());
        return dto;
    }
}