package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.ApplicantDTO;
import com.example.dto.JobSeekerEducationDto;
import com.example.dto.JobSeekerExperienceDto;
import com.example.dto.SavedJobPostReportDto;
import com.example.entity.Applicant;
import com.example.entity.ApplicantEducation;
import com.example.entity.ApplicantExperience;
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
    public ApplicantDTO applyForJob(int jobSeekerId, int jobPostId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
            .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        JobPost jobPost = jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new RuntimeException("Job post not found"));

        Applicant applicant = new Applicant();
        applicant.setJobSeeker(jobSeeker);
        applicant.setJobPost(jobPost);
        applicant.setResumeFilePath(jobSeeker.getPersonalInfo().getResumeUrl());
        applicant.setStatus(ApplicationStatus.SUBMITTED);
        applicant.setApplicationDate(LocalDateTime.now());
        applicant.setFullName(jobSeeker.getFullName());
        applicant.setEmail(jobSeeker.getEmail());
        applicant.setMobileNumber(jobSeeker.getMobileNumber());
        applicant.setSkills(jobSeeker.getSkills());
        applicant.setJobPostTitle(jobPost.getTitle());
        applicant.setJobPostLocation(jobPost.getLocation());
        
        List<Applicant> existing = applicantRepository.findByJobSeekerIdAndJobPostId(jobSeekerId, jobPostId);
        if (!existing.isEmpty()) {
            throw new RuntimeException("You have already applied for this job.");
        }

        // Avoid lambda error with effectively final
        final Applicant finalApplicant = applicant;

        List<ApplicantEducation> applicantEducationList = jobSeeker.getEducationList().stream()
        	    .filter(edu -> edu.getDegree() != null || edu.getInstitution() != null || edu.getFieldOfStudy() != null || edu.getPassingYear() != null)
        	    .map(edu -> {
        	        ApplicantEducation ae = new ApplicantEducation();
        	        ae.setDegree(edu.getDegree());
        	        ae.setInstitute(edu.getInstitution());
        	        ae.setFieldOfStudy(edu.getFieldOfStudy());

        	        // ✅ Null-safe set
        	        ae.setPassingYear(edu.getPassingYear() != null ? edu.getPassingYear() : 0L);

        	        ae.setApplicant(finalApplicant);
        	        return ae;
        	    }).collect(Collectors.toList());

        List<ApplicantExperience> applicantExperienceList = jobSeeker.getExperienceList().stream()
            .map(exp -> {
                ApplicantExperience ax = new ApplicantExperience();
                ax.setCompany(exp.getCompanyName());
                ax.setEndDate(exp.getEndDate());
                ax.setStartDate(exp.getStartDate());
                ax.setJobTitle(exp.getJobTitle());
                ax.setKeyResponsibilities(exp.getKeyResponsibilities());
                ax.setApplicant(finalApplicant);
                return ax;
            }).collect(Collectors.toList());

        applicant.setEducationList(applicantEducationList);
        applicant.setExperienceList(applicantExperienceList);

        applicant = applicantRepository.save(applicant);
        return mapToDTO(applicant);
    }

    
    //get applications for jobpost 
    public List<Applicant> getApplicationsForJob(int jobPostId) {
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new NotFoundException("Job post not found with ID: " + jobPostId);
        }
        return applicantRepository.findByJobPostId(jobPostId);
    }

    //get applications for jobseeker 
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

        // Calculate days since application
        LocalDate appliedDate = applicant.getApplicationDate().toLocalDate();
        long daysSinceApplication = ChronoUnit.DAYS.between(appliedDate, LocalDate.now());

        ApplicantDTO dto = new ApplicantDTO();
        dto.setApplicationId((long) applicant.getId());
        dto.setJobPostId(applicant.getJobPost().getId());
        dto.setResumeFileName(applicant.getResumeFilePath());
        dto.setJobSeekerName(jobSeeker.getFullName());
        dto.setEmail(jobSeeker.getEmail());
        dto.setMobileNumber(jobSeeker.getMobileNumber());

        dto.setEducationList(jobSeeker.getEducationList().stream().map(edu -> new JobSeekerEducationDto(edu.getDegree(),edu.getFieldOfStudy(), 
        		edu.getInstitution(), edu.getPassingYear())).collect(Collectors.toList())
        );

        dto.setExperienceList(
            jobSeeker.getExperienceList().stream()
                .map(exp -> new JobSeekerExperienceDto(
                    exp.getJobTitle(), exp.getCompanyName(), exp.getStartDate(), exp.getEndDate(), exp.getKeyResponsibilities()))
                .collect(Collectors.toList())
        );

        dto.setSkills(jobSeeker.getSkills());
        dto.setAppliedDate(appliedDate);
        dto.setStatus(applicant.getStatus().name());
        dto.setDaysSinceApplication(daysSinceApplication);
        dto.setJobPostTitle(applicant.getJobPost().getTitle());
        dto.setJobPostLocation(applicant.getJobPost().getLocation());

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
//    public List<SavedJobPostReportDto> getAppliedJobsByJobSeeker(int jobSeekerId) {
//        JobSeeker seeker = jobSeekerRepository.findById(jobSeekerId)
//            .orElseThrow(() -> new RuntimeException("JobSeeker not found"));
//
//        List<Applicant> applicants = applicantRepository.findByJobSeeker(seeker);
//
//        return applicants.stream()
//            .map(applicant -> {
//                JobPost post = applicant.getJobPost();
//                return new SavedJobPostReportDto(
//                		post.getId(),
//                		post.getTitle(),
//                		post.getLocation(),
//                		post.getMinSalary(),
//                		post.getMaxSalary(),
//                		post.getMinExperience(),
//                		post.getMaxExperience(),
//                		post.getSkills(),
//                		post.getPostedDate()
//                );
//            })
//            .collect(Collectors.toList());
//    }
    
    // count of applied jobes  by jobseeker
    public long countAppliedJobs(int jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeeker with ID " + jobSeekerId + " not found"));

        return applicantRepository.countByJobSeeker(jobSeeker);
    }
    
    
    
    
}