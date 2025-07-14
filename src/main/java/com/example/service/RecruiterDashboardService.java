package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ApplicantDTO;
import com.example.dto.RecruiterDashboardSummaryDto;
import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.entity.profile.Education;
import com.example.entity.jobposting.JobPost;
import com.example.enums.ApplicationStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ApplicantRepository;
import com.example.repository.JobPostRepository;
import com.example.repository.RecruiterRepository;

@Service
public class RecruiterDashboardService {

    @Autowired
    private JobPostRepository jobPostRepository;
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    @Autowired
    private RecruiterRepository recruiterRepository;
    
    public RecruiterDashboardSummaryDto getDashboardSummary(Recruiter recruiter) {
        RecruiterDashboardSummaryDto summary = new RecruiterDashboardSummaryDto();
        
//        // Count new applications (last 7 days)
    summary.setNewApplications(applicantRepository.countByJobPost_RecruiterAndApplicationDateAfter(
            recruiter, LocalDateTime.now().minusDays(7)));
        
//        
        
//        // Count shortlisted candidates
 summary.setShortlisted(applicantRepository.countByJobPost_RecruiterAndStatus(
            recruiter, ApplicationStatus.SHORTLISTED));
        
        
        // Total views across recruiter's jobs
//        summary.setTotalViews(jobPostRepository.findByRecruiter(recruiter)
//            .stream().mapToInt(JobPost::getApplicants).sum());
//        
        return summary;
    }
    
    
    
    public List<ApplicantDTO> getRecentApplicants(int recruiterId) throws ResourceNotFoundException {
        // Validate recruiter ID
        if (recruiterId <= 0) {
            throw new IllegalArgumentException("Invalid recruiter ID: " + recruiterId);
        }

        // Get recruiter with basic existence check
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
            .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with ID: " + recruiterId));

        // Get active job posts for this recruiter
        List<JobPost> activeJobPosts = jobPostRepository.findByRecruiterAndLastDateToApplyAfter(
            recruiter, LocalDate.now());

        if (activeJobPosts.isEmpty()) {
            return Collections.emptyList();
        }

        // Get recent applicants with additional filters
        List<Applicant> applicants = applicantRepository
            .findTop9ByJobPostInAndStatusNotOrderByApplicationDateDesc(
                activeJobPosts, 
                ApplicationStatus.REJECTED);

        // Convert to DTOs with additional processing
        return applicants.stream()
            .map(applicant -> {
                ApplicantDTO dto = mapToDTO(applicant);
                
                // Add additional calculated fields
                dto.setDaysSinceApplication(ChronoUnit.DAYS.between(
                    applicant.getApplicationDate().toLocalDate(), 
                    LocalDate.now()));  // Add job post details
                dto.setJobPostTitle(applicant.getJobPost().getTitle());
                dto.JobPostLocation(applicant.getJobPost().getLocation());
                
                return dto;
            })
            .collect(Collectors.toList());
    }


    private ApplicantDTO mapToDTO(Applicant applicant) {
        if (applicant == null) {
            return null;
        }
        
        JobSeeker jobSeeker = applicant.getJobSeeker();
        if (jobSeeker == null) {
            throw new IllegalStateException("JobSeeker not found for applicant: " + applicant.getId());
        }

        // Get highest qualification
        String highestQualification = "N/A";
        if (jobSeeker.getEducationList() != null && !jobSeeker.getEducationList().isEmpty()) {
            highestQualification = jobSeeker.getEducationList().stream()
                .filter(edu -> edu.getPassingYear() != null)
                .sorted(Comparator.comparing(Education::getPassingYear).reversed())
                .map(Education::getDegree)
                .findFirst()
                .orElse("N/A");
        }

        // Calculate total experience
        int totalExperience = 0;
        if (jobSeeker.getExperienceList() != null) {
            totalExperience = (int) jobSeeker.getExperienceList().stream()
                .filter(exp -> exp != null && exp.getStartDate() != null && exp.getEndDate() != null)
                .count();
        }

        // Create and populate DTO
        ApplicantDTO dto = new ApplicantDTO();
        dto.setApplicationId((long) applicant.getId());
        dto.setJobPostId(applicant.getJobPost().getId());
       // dto.setCoverLetter(applicant.getCoverLetter());
       // dto.setExpectedSalary(applicant.getExpectedSalary());
        //dto.setAvailability(applicant.getAvailability());
        dto.setResumeFileName(extractFileName(applicant.getResumeFilePath()));
        dto.setJobSeekerName(jobSeeker.getFullName());
        dto.setEmail(jobSeeker.getEmail());
        //dto.setTotalExperience(totalExperience);
        dto.setSkills(jobSeeker.getSkills());
        //dto.setHighestQualification(highestQualification);
        dto.setAppliedDate(applicant.getApplicationDate().toLocalDate());
        dto.setStatus(applicant.getStatus().name());
        
        return dto;
    }

    private String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }
    
   
    
    
//  get Active jobs   
    public List<JobPost> getActiveJobPosts(Recruiter recruiter) {
        return jobPostRepository.findByRecruiterAndLastDateToApplyAfter(
            recruiter, LocalDate.now());
    }   
    
}