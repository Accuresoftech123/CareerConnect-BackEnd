package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.ApplicantDTO;
import com.example.dto.SavedJobPostReportDto;
import com.example.entity.Applicant;
import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.entity.profile.Education;
import com.example.enums.ApplicationStatus;
import com.example.exception.NotFoundException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.DuplicateApplicationException;
import com.example.repository.ApplicantRepository;
import com.example.repository.JobPostRepository;
import com.example.repository.JobSeekerRepository;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final JobPostRepository jobPostRepository;
    private final JobSeekerRepository jobSeekerRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,
                          JobPostRepository jobPostRepository,
                          JobSeekerRepository jobSeekerRepository) {
        this.applicantRepository = applicantRepository;
        this.jobPostRepository = jobPostRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Transactional
    public Applicant applyForJob(ApplicantDTO applicantDTO, int jobSeekerId, int jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new NotFoundException("Job post not found with ID: " + jobPostId));

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new NotFoundException("Job seeker not found with ID: " + jobSeekerId));

        if (applicantRepository.existsByJobPostAndJobSeeker(jobPost, jobSeeker)) {
            throw new DuplicateApplicationException("You have already applied for this job");
        }

        Applicant applicant = new Applicant();
        applicant.setJobPost(jobPost);
        applicant.setJobSeeker(jobSeeker);
        applicant.setStatus(ApplicationStatus.SUBMITTED);
        applicant.setCoverLetter(applicantDTO.getCoverLetter());
        applicant.setExpectedSalary(applicantDTO.getExpectedSalary());
        applicant.setAvailability(applicantDTO.getAvailability());
        applicant.setResumeFilePath(applicantDTO.getResumeFileName());

        return applicantRepository.save(applicant);
    }

    public List<Applicant> getApplicationsForJob(int jobPostId) {
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new NotFoundException("Job post not found with ID: " + jobPostId);
        }
        return applicantRepository.findByJobPostId(jobPostId);
    }

    public List<Applicant> getApplicationsByJobSeeker(int jobSeekerId) {
        if (!jobSeekerRepository.existsById(jobSeekerId)) {
            throw new NotFoundException("Job seeker not found with ID: " + jobSeekerId);
        }
        return applicantRepository.findByJobSeekerId(jobSeekerId);
    }

    @Transactional
    public Applicant updateApplicationStatus(int applicationId, ApplicationStatus status) {
        Applicant applicant = applicantRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with ID: " + applicationId));
        applicant.setStatus(status);
        return applicantRepository.save(applicant);
    }

    public Applicant getApplicationById(int applicationId) {
        return applicantRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with ID: " + applicationId));
    }

    @Transactional
    public void withdrawApplication(int applicationId) {
        if (!applicantRepository.existsById(applicationId)) {
            throw new NotFoundException("Application not found with ID: " + applicationId);
        }
        applicantRepository.deleteById(applicationId);
    }

    @Transactional
    public void shortlistApplication(int applicationId) {
        Applicant applicant = applicantRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with ID: " + applicationId));
        applicant.setStatus(ApplicationStatus.SHORTLISTED);
        applicantRepository.save(applicant);
    }

    public ApplicantDTO mapToDTO(Applicant applicant) {
        JobSeeker jobSeeker = applicant.getJobSeeker();

        String highestQualification = jobSeeker.getEducationList().stream()
                .sorted((e1, e2) -> e2.getPassingYear().compareTo(e1.getPassingYear()))
                .map(Education::getDegree)
                .findFirst()
                .orElse("N/A");

        int totalExperience = jobSeeker.getExperienceList() != null ? jobSeeker.getExperienceList().size() : 0;

        ApplicantDTO dto = new ApplicantDTO();
        dto.setApplicationId((long) applicant.getId());
        dto.setJobPostId(applicant.getJobPost().getId());
        dto.setCoverLetter(applicant.getCoverLetter());
        dto.setExpectedSalary(applicant.getExpectedSalary());
        dto.setAvailability(applicant.getAvailability());
        dto.setResumeFileName(applicant.getResumeFilePath());
        dto.setJobSeekerName(jobSeeker.getFullName());
        dto.setEmail(jobSeeker.getEmail());
        dto.setTotalExperience(totalExperience);
        dto.setSkills(jobSeeker.getSkills());
        dto.setHighestQualification(highestQualification);
        dto.setAppliedDate(applicant.getApplicationDate().toLocalDate());
        dto.setStatus(applicant.getStatus().name());
        
        return dto;
    }

    public List<ApplicantDTO> getApplicantsForRecruiter(int recruiterId) {
        if (!applicantRepository.existsByJobPost_Recruiter_Id(recruiterId)) {
            throw new NotFoundException("No applications found for recruiter with ID: " + recruiterId);
        }
        
        return applicantRepository.findByJobPost_Recruiter_Id(recruiterId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    
    // get appied job by jobseeker
    public List<SavedJobPostReportDto> getAppliedJobsByJobSeeker(int jobSeekerId) {
        JobSeeker seeker = jobSeekerRepository.findById(jobSeekerId)
            .orElseThrow(() -> new RuntimeException("JobSeeker not found"));

        List<Applicant> applicants = applicantRepository.findByJobSeeker(seeker);

        return applicants.stream()
            .map(applicant -> {
                JobPost post = applicant.getJobPost();
                return new SavedJobPostReportDto(
                		post.getId(),
                		post.getTitle(),
                		post.getCompanyName(),
                		post.getLocation(),
                		post.getSalary(),
                		post.getJobType(),
                		post.getSkills()
                );
            })
            .collect(Collectors.toList());
    }
    
    // count of applied jobes  by jobseeker
    public long countAppliedJobs(int jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeeker with ID " + jobSeekerId + " not found"));

        return applicantRepository.countByJobSeeker(jobSeeker);
    }
    
    
    
    
}