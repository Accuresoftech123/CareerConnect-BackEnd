package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.JobPostDto;
import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.enums.JobPostStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.JobPostRepository;
import com.example.repository.RecruiterRepository;

import jakarta.transaction.Transactional;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    // Create
    public JobPostDto createJobPost(JobPostDto jobPostDto, Integer recruiterId) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + recruiterId));

        JobPost jobPost = mapToEntity(jobPostDto);
        jobPost.setRecruiter(recruiter);
        JobPost savedJobPost = jobPostRepository.save(jobPost);
        return mapToDto(savedJobPost);
    }

    // Read (Get All)
    public List<JobPostDto> getAllJobPosts() {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        return jobPosts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Read (Get by ID)
    public JobPostDto getJobPostById(Integer id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobPost not found with id: " + id));

        JobPostDto dto = mapToDto(jobPost);

        // Determine if the job post is closed based on lastDateToApply
        if (jobPost.getLastDateToApply() != null && jobPost.getLastDateToApply().isBefore(LocalDate.now())) {
            dto.setClosed(true);
        } else {
            dto.setClosed(false);
        }

        return dto;
    }


    // Update
    public JobPostDto updateJobPost(Integer id, JobPostDto jobPostDto) {
        JobPost existingJobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobPost not found with id: " + id));

        existingJobPost.setTitle(jobPostDto.getTitle());
        existingJobPost.setDescription(jobPostDto.getDescription());
        existingJobPost.setLocation(jobPostDto.getLocation());
        existingJobPost.setSalary(jobPostDto.getSalary());
        existingJobPost.setEmploymentType(jobPostDto.getEmploymentType());
        existingJobPost.setExperience(jobPostDto.getExperience());
        existingJobPost.setLastDateToApply(jobPostDto.getLastDateToApply());
        existingJobPost.setPostedDate(jobPostDto.getPostedDate());
        existingJobPost.setJobCategory(jobPostDto.getJobCategory());
        existingJobPost.setNumberOfOpenings(jobPostDto.getNumberOfOpenings());
        existingJobPost.setCompanyName(jobPostDto.getCompanyName());
        existingJobPost.setJobType(jobPostDto.getJobType());
        existingJobPost.setWorkLocation(jobPostDto.getWorkLocation());
        existingJobPost.setGender(jobPostDto.getGender());
        existingJobPost.setRequiredExperience(jobPostDto.getRequiredExperience());
        existingJobPost.setSkills(jobPostDto.getSkills());
        existingJobPost.setJobShift(jobPostDto.getJobShift());
        existingJobPost.setEducation(jobPostDto.getEducation());
        

        JobPost updatedJobPost = jobPostRepository.save(existingJobPost);
        return mapToDto(updatedJobPost);
    }

    // Delete
    public void deleteJobPost(Integer id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobPost not found with id: " + id));
        jobPostRepository.delete(jobPost);
    }

    // DTO to Entity
    public JobPost mapToEntity(JobPostDto jobPostDto) {
        JobPost jobPost = new JobPost();
        
        if (jobPostDto.getId() != null) {
            jobPost.setId(jobPostDto.getId());
        }
        
        jobPost.setTitle(jobPostDto.getTitle());
        jobPost.setDescription(jobPostDto.getDescription());
        jobPost.setLocation(jobPostDto.getLocation());
        jobPost.setSalary(jobPostDto.getSalary());
        jobPost.setEmploymentType(jobPostDto.getEmploymentType());
        jobPost.setExperience(jobPostDto.getExperience());
        jobPost.setLastDateToApply(jobPostDto.getLastDateToApply());
        jobPost.setPostedDate(jobPostDto.getPostedDate());
        jobPost.setJobCategory(jobPostDto.getJobCategory());
        jobPost.setNumberOfOpenings(jobPostDto.getNumberOfOpenings());
        jobPost.setCompanyName(jobPostDto.getCompanyName());
        jobPost.setJobType(jobPostDto.getJobType());
        jobPost.setWorkLocation(jobPostDto.getWorkLocation());
        jobPost.setGender(jobPostDto.getGender());
        jobPost.setRequiredExperience(jobPostDto.getRequiredExperience());
        jobPost.setSkills(jobPostDto.getSkills());
        jobPost.setJobShift(jobPostDto.getJobShift());
        jobPost.setEducation(jobPostDto.getEducation());
       

        return jobPost;
    }

    // Entity to DTO
    private JobPostDto mapToDto(JobPost jobPost) {
        JobPostDto dto = new JobPostDto();
        dto.setId(jobPost.getId());
        dto.setTitle(jobPost.getTitle());
        dto.setDescription(jobPost.getDescription());
        dto.setLocation(jobPost.getLocation());
        dto.setSalary(jobPost.getSalary());
        dto.setEmploymentType(jobPost.getEmploymentType());
        dto.setExperience(jobPost.getExperience());
        dto.setLastDateToApply(jobPost.getLastDateToApply());
        dto.setPostedDate(jobPost.getPostedDate());
        dto.setJobCategory(jobPost.getJobCategory());
        dto.setNumberOfOpenings(jobPost.getNumberOfOpenings());
        dto.setCompanyName(jobPost.getCompanyName());
        dto.setJobType(jobPost.getJobType());
        dto.setWorkLocation(jobPost.getWorkLocation());
        dto.setGender(jobPost.getGender());
        dto.setRequiredExperience(jobPost.getRequiredExperience());
        dto.setSkills(jobPost.getSkills());
        dto.setJobShift(jobPost.getJobShift());
        dto.setEducation(jobPost.getEducation());
        
        return dto;
    }
    //find by title, location, experience
    public List<JobPost> searchJobs(String title, String location, String experience) {
        return jobPostRepository.searchJobs(title, location, experience);
    }

    @Transactional
    public void closeJobPost(Long jobId, Recruiter recruiter) {
        int updated = jobPostRepository.closeJobPost(jobId, recruiter);
        if (updated == 0) {
            throw new ResourceNotFoundException(
                "Job post not found with id: " + jobId + " for recruiter: " + recruiter.getId());
        }
    }
    
    public List<JobPostDto> getAllActiveJobPostsForApplicants() {
        List<JobPost> activeJobPosts = jobPostRepository.findAllActiveJobPosts(LocalDate.now());
        return activeJobPosts.stream()
                             .map(this::mapToDto)
                             .collect(Collectors.toList());
    }
    
    public JobPostDto getActiveJobPostById(Integer id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost not found with id: " + id));

        if (jobPost.getStatus() == JobPostStatus.CLOSED || jobPost.getLastDateToApply().isBefore(LocalDate.now())) {
            throw new ResourceNotFoundException("This job post is closed and cannot be viewed by applicants.");
        }

        return mapToDto(jobPost);
    }
    
    
    
 // Get all closed jobs
    public List<JobPostDto> getAllClosedJobPosts() {
        List<JobPost> closedJobs = jobPostRepository.findByStatus(JobPostStatus.CLOSED);
        return closedJobs.stream()
                       .map(this::mapToDto)
                       .collect(Collectors.toList());
    }
    
 // Get closed jobs by recruiter
    public List<JobPostDto> getClosedJobsByRecruiter(Integer recruiterId) {
        List<JobPost> closedJobs = jobPostRepository.findByRecruiterIdAndStatus(recruiterId, JobPostStatus.CLOSED);
        return closedJobs.stream()
                       .map(this::mapToDto)
                       .collect(Collectors.toList());
    }
    
    // Get closed jobs with filters
    public List<JobPostDto> getClosedJobsWithFilters(String title, Double minSalary, Double maxSalary) {
        try {
            List<JobPost> filteredJobs = jobPostRepository.findClosedJobsWithFilters(
                title, 
                minSalary, 
                maxSalary
            );
            return filteredJobs.stream()
                             .map(this::mapToDto)
                             .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error filtering closed jobs: " + e.getMessage(), e);
        }
    }
    // Get jobs closed before a certain date
    public List<JobPostDto> getJobsClosedBeforeDate(LocalDate date) {
        List<JobPost> jobs = jobPostRepository.findByStatusAndLastDateToApplyBefore(JobPostStatus.CLOSED, date);
        return jobs.stream()
                  .map(this::mapToDto)
                  .collect(Collectors.toList());
    }
    
    


}
