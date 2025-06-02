package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.JobSeekerProfileDto;
import com.example.dto.RecruiterProfileDto;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;

/**
 * Service class to handle admin-level operations such as 
 * retrieving counts and reports for job seekers and recruiters.
 */
@Service
public class AdminService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    /**
     * Returns the total number of job seekers.
     *
     * @return count of job seekers
     */
    @Transactional(readOnly = true)
    public long countJobSeekers() {
        return jobSeekerRepository.count();
    }

    /**
     * Returns the total number of recruiters.
     *
     * @return count of recruiters
     */
    @Transactional(readOnly = true)
    public long countRecruiters() {
        return recruiterRepository.count();
    }

    /**
     * Generates a detailed report of all job seekers including their count.
     *
     * @return a map containing:
     *         - "jobSeekerCount": total number of job seekers
     *         - "jobSeekerList": list of job seekers with their profile details
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getJobSeekersReportWithCount() {
        // Convert each JobSeeker entity to a JobSeekerProfileDto
        List<JobSeekerProfileDto> jobSeekerProfiles = jobSeekerRepository.findAll().stream()
            .map(js -> new JobSeekerProfileDto(
              
              
            ))
            .collect(Collectors.toList());

        // Prepare the result map
        Map<String, Object> result = new HashMap<>();
        result.put("jobSeekerCount", jobSeekerProfiles.size());
        result.put("jobSeekerList", jobSeekerProfiles);
        return result;
    }

    /**
     * Generates a detailed report of all recruiters including their count.
     *
     * @return a map containing:
     *         - "totalRecruiters": total number of recruiters
     *         - "recruiters": list of recruiters with their profile details
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getRecruitersReportWithCount() {
        // Convert each Recruiter entity to a RecruiterProfileDto
        List<RecruiterProfileDto> recruiterProfiles = recruiterRepository.findAll().stream()
            .map(r -> new RecruiterProfileDto(
                r.getCompanyName(),
                r.getCompanyAddress(),
                r.getCompanyDescription(),
                r.getCompanyWebsiteUrl(),
                r.getNumberOfEmployees(),
                r.getIndustryType(),
                r.getFirstName(),
                r.getLastName(),
                r.getEmail(),
                r.getPhoneNumber(),
                r.getCity()
            ))
            .collect(Collectors.toList());

        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("totalRecruiters", recruiterProfiles.size());
        response.put("recruiters", recruiterProfiles);

        return response;
    }
}
