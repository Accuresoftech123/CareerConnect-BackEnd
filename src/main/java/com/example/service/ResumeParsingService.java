package com.example.service;

import com.example.Utils.ResumeParserUtil;
import com.example.dto.JobSeekerEducationDto;
import com.example.dto.JobSeekerExperienceDto;
import com.example.dto.JobSeekerPersonalInfoDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerSocialProfileDto;
import com.example.entity.JobSeeker;
import com.example.entity.profile.*;
import com.example.repository.JobSeekerRepository;
import com.example.repository.JobSeekerEducationRepository;
import com.example.repository.JobSeekerExperienceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    
    public JobSeeker parseAndSaveResume(MultipartFile file, int jobSeekerId) throws IOException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        String content = ResumeParserUtil.extractTextFromPdf(file);

        // Parse resume fields
        String fullName = ResumeParserUtil.extractName(content);
      //  String email = ResumeParserUtil.extractEmail(content);
        String phone = ResumeParserUtil.extractPhone(content);
        List<String> skills = ResumeParserUtil.extractSkills(content);
        String city = ResumeParserUtil.extractCity(content);
        String linkedin = ResumeParserUtil.extractLinkedin(content);
        List<Education> educations = ResumeParserUtil.extractEducations(content);
        List<Experience> experiences = ResumeParserUtil.extractExperiences(content);

        // Update basic info
        if (fullName != null && !fullName.isBlank()) jobSeeker.setFullName(fullName);
      //  if (email != null && !email.isBlank()) jobSeeker.setEmail(email);
        if (phone != null && !phone.isBlank()) jobSeeker.setMobileNumber(phone);
        if (skills != null && !skills.isEmpty()) jobSeeker.setSkills(skills);

        // Personal Info
        JobSeekerPersonalInfo personalInfo = jobSeeker.getPersonalInfo();
        if (personalInfo == null) {
            personalInfo = new JobSeekerPersonalInfo();
            personalInfo.setJobSeeker(jobSeeker);
        }
        personalInfo.setCity(city);
        personalInfo.setResumeUrl(saveResumeFile(file, jobSeekerId));
        jobSeeker.setPersonalInfo(personalInfo);

        // Social Info
        SocialProfile social = jobSeeker.getSocialProfile();
        if (social == null) {
            social = new SocialProfile();
            social.setJobSeeker(jobSeeker);
        }
        if (linkedin != null && !linkedin.equals("Not found")) {
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

        // Save and return updated entity
        return jobSeekerRepository.save(jobSeeker);
    }

    private String saveResumeFile(MultipartFile file, int jobSeekerId) throws IOException {
        String directory = "uploads/resumes/" + jobSeekerId;
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();

        String filePath = directory + "/" + file.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(file.getBytes());
        }
        return filePath;
    }
    public JobSeekerProfileDto mapToDto(JobSeeker jobSeeker) {
        JobSeekerProfileDto dto = new JobSeekerProfileDto();
        dto.setId(jobSeeker.getId());
        dto.setFullName(jobSeeker.getFullName());
       // dto.setEmail(jobSeeker.getEmail());
        dto.setMobileNumber(jobSeeker.getMobileNumber());
        dto.setSkills(jobSeeker.getSkills());

        // Personal Info Mapping
        if (jobSeeker.getPersonalInfo() != null) {
            JobSeekerPersonalInfoDto personalInfoDto = new JobSeekerPersonalInfoDto();
            personalInfoDto.setCity(jobSeeker.getPersonalInfo().getCity());
            personalInfoDto.setResumeUrl(jobSeeker.getPersonalInfo().getResumeUrl());
            dto.setPersonalInfo(personalInfoDto);
        }

        // Social Profile Mapping
        if (jobSeeker.getSocialProfile() != null) {
            JobSeekerSocialProfileDto socialDto = new JobSeekerSocialProfileDto();
            socialDto.setLinkedinUrl(jobSeeker.getSocialProfile().getLinkedinUrl());
            dto.setScoicalProfile(socialDto);
        }

        // Education Mapping (assuming you're using MapStruct or manual mapping logic)
        List<JobSeekerEducationDto> educationDtos = jobSeeker.getEducationList().stream()
            .map(edu -> {
                JobSeekerEducationDto eduDto = new JobSeekerEducationDto();
                eduDto.setDegree(edu.getDegree());
                eduDto.setInstitution(edu.getInstitution());
                eduDto.setPassingYear(edu.getPassingYear());
                return eduDto;
            }).toList();
        dto.setEducationList(educationDtos);

        // Experience Mapping
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

//        // Job Preferences (if available)
//        if (jobSeeker.getJobPreferences() != null) {
//            JobSeekerJonPreferencesDto preferencesDto = new JobSeekerJonPreferencesDto();
//            preferencesDto.setPreferredCity(jobSeeker.getJobPreferences().getPreferredCity());
//            preferencesDto.setExpectedSalary(jobSeeker.getJobPreferences().getExpectedSalary());
//            dto.setJobPreferences(preferencesDto);
//        }

        return dto;
    }

}
