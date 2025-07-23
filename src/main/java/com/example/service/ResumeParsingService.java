package com.example.service;

import com.example.Utils.ResumeParserUtil;
import com.example.dto.*;
import com.example.entity.JobSeeker;
import com.example.entity.profile.*;
import com.example.repository.JobSeekerEducationRepository;
import com.example.repository.JobSeekerExperienceRepository;
import com.example.repository.JobSeekerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ResumeParsingService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobSeekerEducationRepository educationRepository;

    @Autowired
    private JobSeekerExperienceRepository experienceRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

   // @Async
    public JobSeeker parseAndSaveResume(MultipartFile file, int jobSeekerId) throws IOException {
        // Validate file extension
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are supported.");
        }

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        String content = ResumeParserUtil.extractTextFromPdf(file);

        // Parse fields
        String fullName = ResumeParserUtil.extractName(content);
        String phone = ResumeParserUtil.extractPhone(content);
        List<String> skills = ResumeParserUtil.extractSkills(content);
        String city = ResumeParserUtil.extractCity(content);
        String linkedin = ResumeParserUtil.extractLinkedin(content);
        List<Education> educations = ResumeParserUtil.extractEducations(content);
        List<Experience> experiences = ResumeParserUtil.extractExperiences(content);

        // Basic info
        if (fullName != null && !fullName.isBlank()) jobSeeker.setFullName(fullName);
        if (phone != null && !phone.isBlank()) jobSeeker.setMobileNumber(phone);
        if (skills != null && !skills.isEmpty()) jobSeeker.setSkills(skills);

        // Personal info
        JobSeekerPersonalInfo personalInfo = jobSeeker.getPersonalInfo();
        if (personalInfo == null) {
            personalInfo = new JobSeekerPersonalInfo();
            personalInfo.setJobSeeker(jobSeeker);
        }
        personalInfo.setCity(city);
        personalInfo.setResumeUrl(saveResumeFile(file, jobSeekerId));
        jobSeeker.setPersonalInfo(personalInfo);

        // Social profile
        SocialProfile social = jobSeeker.getSocialProfile();
        if (social == null) {
            social = new SocialProfile();
            social.setJobSeeker(jobSeeker);
        }
        if (linkedin != null) {
            social.setLinkedinUrl(linkedin);
        }
        jobSeeker.setSocialProfile(social);

        // Education
        if (jobSeeker.getEducationList() != null) {
            educationRepository.deleteAll(jobSeeker.getEducationList());
        }
        for (Education edu : educations) {
            edu.setJobSeeker(jobSeeker);
        }
        jobSeeker.setEducationList(educations);

        // Experience
        if (jobSeeker.getExperienceList() != null) {
            experienceRepository.deleteAll(jobSeeker.getExperienceList());
        }
        for (Experience exp : experiences) {
            exp.setJobSeeker(jobSeeker);
        }
        jobSeeker.setExperienceList(experiences);

        return jobSeekerRepository.save(jobSeeker);
    }

    private String saveResumeFile(MultipartFile file, int jobSeekerId) throws IOException {
        String folder = "resumes/" + jobSeekerId;
        return cloudinaryService.uploadFile(file, folder);
    }

    public JobSeekerProfileDto mapToDto(JobSeeker jobSeeker) {
        JobSeekerProfileDto dto = new JobSeekerProfileDto();
        dto.setId(jobSeeker.getId());
        dto.setFullName(jobSeeker.getFullName());
        dto.setMobileNumber(jobSeeker.getMobileNumber());
        dto.setSkills(jobSeeker.getSkills());

        // Personal Info
        if (jobSeeker.getPersonalInfo() != null) {
            JobSeekerPersonalInfoDto personalInfoDto = new JobSeekerPersonalInfoDto();
            personalInfoDto.setCity(jobSeeker.getPersonalInfo().getCity());
            personalInfoDto.setResumeUrl(jobSeeker.getPersonalInfo().getResumeUrl());
            dto.setPersonalInfo(personalInfoDto);
        }

        // Social Profile
        if (jobSeeker.getSocialProfile() != null) {
            JobSeekerSocialProfileDto socialDto = new JobSeekerSocialProfileDto();
            socialDto.setLinkedinUrl(jobSeeker.getSocialProfile().getLinkedinUrl());
            dto.setScoicalProfile(socialDto);
        }

        // Education List
        List<JobSeekerEducationDto> educationDtos = jobSeeker.getEducationList().stream()
                .map(edu -> {
                    JobSeekerEducationDto eduDto = new JobSeekerEducationDto();
                    eduDto.setDegree(edu.getDegree());
                    eduDto.setInstitution(edu.getInstitution());
                    eduDto.setPassingYear(edu.getPassingYear());
                    return eduDto;
                }).toList();
        dto.setEducationList(educationDtos);

        // Experience List
        List<JobSeekerExperienceDto> experienceDtos = jobSeeker.getExperienceList().stream()
                .map(exp -> {
                    JobSeekerExperienceDto expDto = new JobSeekerExperienceDto();
                    expDto.setCompanyName(exp.getCompanyName());
                    expDto.setJobTitle(exp.getJobTitle());
                    expDto.setStartDate(exp.getStartDate());
                    expDto.setEndDate(exp.getEndDate());
                    return expDto;
                }).toList();
        dto.setExperienceList(experienceDtos);

        // Optional: Map job preferences in future

        return dto;
    }
}
