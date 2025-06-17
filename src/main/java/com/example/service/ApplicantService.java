package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ApplicantDTO;
import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.entity.profile.Education;
import com.example.enums.ApplicationStatus;
import com.example.repository.ApplicantRepository;
import com.example.repository.JobPostRepository;
import com.example.repository.JobSeekerRepository;

import jakarta.transaction.Transactional;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Transactional
    public Applicant applyForJob(ApplicantDTO applicantDTO, int jobSeekerId) {
        JobPost jobPost = jobPostRepository.findById(applicantDTO.getJobPostId())
                .orElseThrow(() -> new RuntimeException("Job post not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        if (applicantRepository.existsByJobPostAndJobSeeker(jobPost, jobSeeker)) {
            throw new RuntimeException("You have already applied for this job");
        }

        // Simulated file upload logic (commented out for now)
        String resumePath = null;
//        if (applicantDTO.getResumeFileName() != null) {
//            resumePath = fileStorageService.storeFile(applicantDTO.getResumeFileName());
//        }

        Applicant applicant = new Applicant();
        applicant.setJobPost(jobPost);
        applicant.setJobSeeker(jobSeeker);
        applicant.setStatus(ApplicationStatus.SUBMITTED);
        applicant.setCoverLetter(applicantDTO.getCoverLetter());
        applicant.setExpectedSalary(applicantDTO.getExpectedSalary());
        applicant.setAvailability(applicantDTO.getAvailability());
        applicant.setResumeFilePath(resumePath);

        return applicantRepository.save(applicant);
    }

    public List<Applicant> getApplicationsForJob(int jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Job post not found"));
        return applicantRepository.findByJobPost(jobPost);
    }

    public List<Applicant> getApplicationsByJobSeeker(int jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        return applicantRepository.findByJobSeeker(jobSeeker);
    }

    @Transactional
    public Applicant updateApplicationStatus(int applicationId, ApplicationStatus status) {
        Applicant applicant = applicantRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        applicant.setStatus(status);
        return applicantRepository.save(applicant);
    }
    
    public ApplicantDTO mapToDTO(Applicant applicant) {
        JobSeeker jobSeeker = applicant.getJobSeeker();

        String highestQualification = jobSeeker.getEducationList().stream()
            .sorted((e1, e2) -> e2.getPassingYear().compareTo(e1.getPassingYear())) // get latest education
            .map(Education::getDegree)
            .findFirst()
            .orElse("N/A");

        int totalExperience = jobSeeker.getExperienceList() != null ? jobSeeker.getExperienceList().size() : 0;

        return new ApplicantDTO(
            (long) applicant.getId(),
            applicant.getJobPost().getId(),
            applicant.getCoverLetter(),
            applicant.getExpectedSalary(),
            applicant.getAvailability(),
            applicant.getResumeFilePath(),
            jobSeeker.getFullName(),
            jobSeeker.getEmail(),
            totalExperience,
            jobSeeker.getSkills(),
            highestQualification,
            applicant.getResumeFilePath(),
            applicant.getApplicationDate().toLocalDate(),
            applicant.getStatus().name()
        );
    }
    
    public List<ApplicantDTO> getApplicantsForRecruiter(int recruiterId) {
        List<Applicant> applicants = applicantRepository.findByJobPost_Recruiter_Id(recruiterId);
        return applicants.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public void shortlistApplication(int applicationId) {
        Applicant applicant = applicantRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Applicant not found with ID: " + applicationId));

        applicant.setStatus(ApplicationStatus.SHORTLISTED); // Using Enum properly
        applicantRepository.save(applicant);
    }




}
