package com.example.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.JobPostDto;

import com.example.entity.Applicant;

import com.example.dto.RecommendedJobPostDto;
import com.example.entity.JobSeeker;

import com.example.entity.Recruiter;
import com.example.entity.jobposting.JobPost;
import com.example.entity.profile.Experience;
import com.example.entity.profile.JobSeekerPersonalInfo;
import com.example.enums.ApplicationStatus;
import com.example.enums.JobPostStatus;
import com.example.enums.Status;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ApplicantRepository;
import com.example.repository.JobPostRepository;
import com.example.repository.JobSeekerExperienceRepository;
import com.example.repository.JobSeekerPersonalInfoRepository;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;
import com.example.repository.SavedJobRepository;

import jakarta.transaction.Transactional;

@Service
public class JobPostService {

	@Autowired
	private JobPostRepository jobPostRepository;

	@Autowired
	private RecruiterRepository recruiterRepository;

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	private JobSeekerRepository jobSeekerRepository;
	@Autowired
	private JobSeekerPersonalInfoRepository jobSeekerPersonalInfoRepository;

	@Autowired
	private JobSeekerExperienceRepository experienceRepository;
	
	@Autowired
	private SavedJobRepository savedJobRepo;

	// Create
	// CREATE JobPost
	public JobPostDto createJobPost(JobPostDto jobPostDto, Integer recruiterId) {
		Recruiter recruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RuntimeException("Recruiter not found with ID: " + recruiterId));

		JobPost jobPost = new JobPost();
		jobPost.setTitle(jobPostDto.getTitle());
		jobPost.setDescription(jobPostDto.getDescription());
		jobPost.setLocation(jobPostDto.getLocation());
		jobPost.setEmploymentType(jobPostDto.getEmploymentType());
		jobPost.setMinExperience(jobPostDto.getMinExperience());
		jobPost.setMaxExperience(jobPostDto.getMaxExperience());
		jobPost.setLastDateToApply(jobPostDto.getLastDateToApply());
		jobPost.setMinSalary(jobPostDto.getMinSalary());
		jobPost.setMaxSalary(jobPostDto.getMaxSalary());
		jobPost.setSkills(jobPostDto.getSkills());
		jobPost.setBenefits(jobPostDto.getBenefits());
		jobPost.setNumberOfOpenings(jobPostDto.getNumberOfOpenings());
		jobPost.setStatus(JobPostStatus.OPEN);
		jobPost.setRecruiter(recruiter);
		jobPost.setPrefillRequest(jobPostDto.isPrefillRequest());
		jobPost.setPrefillFromJobId(jobPostDto.getPrefillFromJobId());

		JobPost savedPost = jobPostRepository.save(jobPost);

		// Convert back to DTO if needed
		return convertToDto(savedPost); // You need to implement this mapping method
	}

	private JobPostDto convertToDto(JobPost jobPost) {
		JobPostDto dto = new JobPostDto();

		dto.setId(jobPost.getId());
		dto.setTitle(jobPost.getTitle());
		dto.setDescription(jobPost.getDescription());
		dto.setLocation(jobPost.getLocation());
		dto.setEmploymentType(jobPost.getEmploymentType());
		dto.setMinExperience(jobPost.getMinExperience());
		dto.setMaxExperience(jobPost.getMaxExperience());
		dto.setLastDateToApply(jobPost.getLastDateToApply());
		dto.setPostedDate(jobPost.getPostedDate());
		dto.setMinSalary(jobPost.getMinSalary());
		dto.setMaxSalary(jobPost.getMaxSalary());
		dto.setSkills(jobPost.getSkills());
		dto.setBenefits(jobPost.getBenefits());
		dto.setNumberOfOpenings(jobPost.getNumberOfOpenings());
		dto.setStatus(jobPost.getStatus());
		dto.setPrefillRequest(jobPost.isPrefillRequest());
		dto.setPrefillFromJobId(jobPost.getPrefillFromJobId());
		dto.setRecruiterId(jobPost.getRecruiter().getId()); // recruiter ID only

		return dto;
	}

	public List<JobPostDto> getAllJobPostsWithBookmarks(int jobSeekerId) {
	    List<JobPost> jobPosts = jobPostRepository.findAll();

	    List<Long> savedJobIds = savedJobRepo.findSavedJobIdsByJobSeekerId(jobSeekerId);

	    return jobPosts.stream().map(job -> {
	        JobPostDto dto = mapToDto(job);
	        dto.setBookmarked(savedJobIds.contains((long) dto.getId()));  // cast dto.getId() to long if needed
	        return dto;
	    }).collect(Collectors.toList());
	}

    // Read (Get by ID)
    public JobPostDto getJobPostById(Integer id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobPost not found with id: " + id));

        JobPostDto dto = mapToDto(jobPost);

        // Determine if the job post is closed based on lastDateToApply
        if (jobPost.getLastDateToApply() != null && jobPost.getLastDateToApply().isBefore(LocalDate.now())) {
            dto.setStatus(null);
        } else {
            dto.setStatus(jobPost.getStatus());
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
		existingJobPost.setMaxSalary(jobPostDto.getMaxSalary());
		existingJobPost.setMinSalary(jobPostDto.getMinSalary());
		existingJobPost.setEmploymentType(jobPostDto.getEmploymentType());
		existingJobPost.setMinExperience(jobPostDto.getMinExperience());
		existingJobPost.setMaxExperience(jobPostDto.getMaxExperience());
		existingJobPost.setLastDateToApply(jobPostDto.getLastDateToApply());
		existingJobPost.setPostedDate(jobPostDto.getPostedDate());

		existingJobPost.setNumberOfOpenings(jobPostDto.getNumberOfOpenings());
		// existingJobPost.setCompanyName(jobPostDto.getCompanyName());
		// existingJobPost.setJobType(jobPostDto.getJobType());
		// existingJobPost.setWorkLocation(jobPostDto.getWorkLocation());
		// existingJobPost.setGender(jobPostDto.getGender());

		existingJobPost.setSkills(jobPostDto.getSkills());
		// existingJobPost.setJobShift(jobPostDto.getJobShift());
		// existingJobPost.setEducation(jobPostDto.getEducation());

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

		if (jobPostDto.getId() != 0) {
			jobPost.setId(jobPostDto.getId());
		}

		jobPost.setTitle(jobPostDto.getTitle());
		jobPost.setDescription(jobPostDto.getDescription());
		jobPost.setLocation(jobPostDto.getLocation());

		jobPost.setEmploymentType(jobPostDto.getEmploymentType());
		jobPost.setMaxSalary(jobPostDto.getMaxSalary());
		jobPost.setMinSalary(jobPostDto.getMinSalary());
		jobPost.setLastDateToApply(jobPostDto.getLastDateToApply());
		jobPost.setPostedDate(jobPostDto.getPostedDate());

		jobPost.setNumberOfOpenings(jobPostDto.getNumberOfOpenings());

		jobPost.setMinExperience(jobPostDto.getMinExperience());
		jobPost.setMaxExperience(jobPostDto.getMaxExperience());
		jobPost.setSkills(jobPostDto.getSkills());
		jobPost.setBenefits(jobPostDto.getBenefits());

		return jobPost;
	}

	// Entity to DTO
	private JobPostDto mapToDto(JobPost jobPost) {
		JobPostDto dto = new JobPostDto();
		dto.setId(jobPost.getId());
		dto.setTitle(jobPost.getTitle());
		dto.setDescription(jobPost.getDescription());
		dto.setLocation(jobPost.getLocation());
		dto.setMaxSalary(jobPost.getMaxSalary());
		dto.setMinSalary(jobPost.getMinSalary());
		dto.setEmploymentType(jobPost.getEmploymentType());
		dto.setMinExperience(jobPost.getMinExperience());
		dto.setMaxExperience(jobPost.getMaxExperience());
		dto.setLastDateToApply(jobPost.getLastDateToApply());
		dto.setPostedDate(jobPost.getPostedDate());

		dto.setNumberOfOpenings(jobPost.getNumberOfOpenings());

		dto.setSkills(jobPost.getSkills());
		dto.setBenefits(jobPost.getBenefits());

		if (jobPost.getRecruiter() != null && jobPost.getRecruiter().getCompanyProfile() != null
				&& jobPost.getRecruiter().getCompanyProfile().getImg() != null) {
			dto.setCompanyImageUrl(jobPost.getRecruiter().getCompanyProfile().getImg());
		}

		if (jobPost.getRecruiter() != null && jobPost.getRecruiter().getCompanyProfile() != null
				&& jobPost.getRecruiter().getCompanyProfile().getCompanyName() != null) {
			dto.setCompanyName(jobPost.getRecruiter().getCompanyProfile().getCompanyName());
		}
		
		if (jobPost.getRecruiter() != null && jobPost.getRecruiter().getCompanyProfile() != null
				&& jobPost.getRecruiter().getCompanyProfile().getImg() != null) {
			dto.setHrName(jobPost.getRecruiter().getFullName());
		}
		
        
        if(jobPost.getRecruiter()!= null
        		&& jobPost.getRecruiter().getCompanyProfile()!=null
        		&& jobPost.getRecruiter().getCompanyProfile().getHrContactEmail()!=null){
        	dto.setCompanyMail(jobPost.getRecruiter().getCompanyProfile().getHrContactEmail());
        }
        
        if(jobPost.getRecruiter()!= null
        		&& jobPost.getRecruiter().getCompanyProfile()!=null
        		&& jobPost.getRecruiter().getCompanyProfile().getHrContactMobileNumber()!=null){
        	dto.setCompanyHr(jobPost.getRecruiter().getCompanyProfile().getHrContactMobileNumber());
        }
        
        if(jobPost.getRecruiter()!= null
        		&& jobPost.getRecruiter().getCompanyProfile()!=null
        		&& jobPost.getRecruiter().getCompanyProfile().getIndustryType()!=null){
        	dto.setCompanyIndustry(jobPost.getRecruiter().getCompanyProfile().getIndustryType());
        }
        
        if(jobPost.getRecruiter()!= null
        		&& jobPost.getRecruiter().getCompanyProfile()!=null
        		&& jobPost.getRecruiter().getCompanyProfile().getWebsite()!=null){
        	dto.setCompanyWebsite(jobPost.getRecruiter().getCompanyProfile().getWebsite());
        }
        if(jobPost.getRecruiter()!= null
        		&& jobPost.getRecruiter().getCompanyProfile()!=null
        		&& jobPost.getRecruiter().getCompanyProfile().getWebsite()!=null){
        	dto.setCompanyAbout(jobPost.getRecruiter().getCompanyProfile().getAbout());
        }


		return dto;
	}

	// find by title, location, experience
	public List<JobPost> searchJobs(String title, String location, String experience) {
		return jobPostRepository.searchJobs(title, location, experience);
	}

	// close jobpost

	@Transactional
	public void closeJobPost(Long jobId, Integer recruiterId) {
		boolean exists = jobPostRepository.existsByIdAndRecruiterIdAndStatus(jobId, recruiterId, JobPostStatus.OPEN);
		if (!exists) {
			throw new ResourceNotFoundException("Job post not found or not open for recruiter with id: " + recruiterId);
		}

		int updated = jobPostRepository.updateStatus(jobId, recruiterId, JobPostStatus.CLOSED);
		if (updated == 0) {
			throw new ResourceNotFoundException("Failed to close job post with id: " + jobId);
		}
	}

	public List<JobPostDto> getAllActiveJobPostsForApplicants() {
		List<JobPost> activeJobPosts = jobPostRepository.findAllActiveJobPosts(LocalDate.now());
		return activeJobPosts.stream().map(this::mapToDto).collect(Collectors.toList());
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
		return closedJobs.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// Get closed jobs by recruiter
	public List<JobPostDto> getClosedJobsByRecruiter(Integer recruiterId) {
		List<JobPost> closedJobs = jobPostRepository.findByRecruiterIdAndStatus(recruiterId, JobPostStatus.CLOSED);
		return closedJobs.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// Get closed jobs with filters
	public List<JobPostDto> getClosedJobsWithFilters(String title, Double minSalary, Double maxSalary) {
		try {
			List<JobPost> filteredJobs = jobPostRepository.findClosedJobsWithFilters(title, minSalary, maxSalary);
			return filteredJobs.stream().map(this::mapToDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("Error filtering closed jobs: " + e.getMessage(), e);
		}
	}

	// Get jobs closed before a certain date
	public List<JobPostDto> getJobsClosedBeforeDate(LocalDate date) {
		List<JobPost> jobs = jobPostRepository.findByStatusAndLastDateToApplyBefore(JobPostStatus.CLOSED, date);
		return jobs.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// get latest added jobpost
	public Optional<JobPostDto> getLastAddedJobPost(Integer recruiterId) {
		return jobPostRepository.findLatestByRecruiterId(recruiterId)

				.map(this::mapToDto);
	}

	// save as draft method

	public JobPostDto saveJobPostAsDraft(JobPostDto jobPostDto, Integer recruiterId) {
		Recruiter recruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + recruiterId));

		JobPost jobPost = mapToEntity(jobPostDto);
		jobPost.setRecruiter(recruiter);
		jobPost.setStatus(JobPostStatus.DRAFT); // Mark as Draft

		JobPost savedJobPost = jobPostRepository.save(jobPost);
		return mapToDto(savedJobPost);
	}

	// get all save draft methods
	public List<JobPostDto> getDraftJobPostsByRecruiter(Integer recruiterId) {
		List<JobPost> drafts = jobPostRepository.findByRecruiterIdAndStatus(recruiterId, JobPostStatus.DRAFT);
		return drafts.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// Shortlist Applicant
	@Transactional
	public void shortlistApplicant(Integer applicantId) {
		Applicant applicant = applicantRepository.findById(applicantId)
				.orElseThrow(() -> new ResourceNotFoundException("Applicant not found"));

		applicant.setStatus(ApplicationStatus.SHORTLISTED);
		applicantRepository.save(applicant);
	}

	// Recommended jobs for job seekers
	public List<RecommendedJobPostDto> getRecommendedJobsForJobSeeker(int jobSeekerId) {

		// Step 1: Fetch JobSeeker by ID
		JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
				.orElseThrow(() -> new RuntimeException("JobSeeker not found with ID: " + jobSeekerId));

		// Step 2: Get seeker’s skills and preferred location
		List<String> jobSeekersSkills = jobSeeker.getSkills();
		String preferredLocation = jobSeeker.getJobPrefeences().getPreferredLocation();

		// Step 3: get all jobes
		List<JobPostDto> allJobs = getAllActiveJobPostsForApplicants();

		// Step 4: filter matching jobs

		List<RecommendedJobPostDto> recommendedJobs = new ArrayList<>();

		for (JobPostDto job : allJobs) {

			boolean matchesSkill = false;

			if (jobSeekersSkills != null && job.getSkills() != null) {

				for (String skill : jobSeekersSkills) {

					for (String jobSkill : job.getSkills()) {
						if (skill != null && jobSkill != null && skill.trim().equalsIgnoreCase(jobSkill.trim())) {
							matchesSkill = true;
							break;
						}
					}
				}
			}

			boolean matchesLocation = preferredLocation == null
					|| job.getLocation().equalsIgnoreCase(preferredLocation);
			System.out.println(matchesSkill);
			System.out.println(matchesLocation);
			System.out.println("Seeker Skills: " + jobSeekersSkills);

			System.out.println("Job Skills: " + job.getSkills());
			if (matchesSkill || matchesLocation) {
				recommendedJobs.add(convertRecommendedJobPostDto(job));
			}

			// limit to 5 jobs
			if (recommendedJobs.size() >= 8) {
				break;
			}
		}

		return recommendedJobs;
	}

	// convert jobpost object to recommendejob post object
	private RecommendedJobPostDto convertRecommendedJobPostDto(JobPostDto job) {

		RecommendedJobPostDto dto = new RecommendedJobPostDto();
		dto.setId(job.getId());
		dto.setTitle(job.getTitle());
		dto.setCompanyName(job.getCompanyName());
		dto.setLocation(job.getLocation());
		dto.setEmploymentType(job.getEmploymentType());
		dto.setMinSalary(job.getMinSalary());
		dto.setMaxSalary(job.getMaxSalary());
		dto.setSkills(job.getSkills());

		return dto;
	}

	// Add method to get previous jobs for prefill
	public List<JobPostDto> getPreviousJobPostsByRecruiter(Integer recruiterId) {
		List<JobPost> previousJobs = jobPostRepository.findAllByRecruiterIdOrderByPostedDateDesc(recruiterId);

		return previousJobs.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// Get Count of Active Jobs
	public long getActiveJobPostCount() {
		return jobPostRepository.countByStatus(JobPostStatus.OPEN);
	}

//get Count of close Jobs
	public long getCloseJobPost() {
		return jobPostRepository.countByStatus(JobPostStatus.CLOSED);
	}

//To get count of Todays match Jobpost
	 public double calculateTotalExperience(int jobSeekerId) {
	        List<Experience> experiences = experienceRepository.findByJobSeekerId(jobSeekerId);

	        long totalMonths = experiences.stream()
	            .mapToLong(exp -> {
	                LocalDate start = exp.getStartDate();
	                LocalDate end = exp.getEndDate() != null ? exp.getEndDate() : LocalDate.now();
	                return Period.between(start, end).toTotalMonths();
	            })
	            .sum();

	        return totalMonths / 12.0;
	    }

	    public List<JobPost> getTodayJobMatchesListForSeeker(int jobSeekerId) {
	        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found"));

	        JobSeekerPersonalInfo personalInfo = jobSeekerPersonalInfoRepository.findByJobSeekerId(jobSeekerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Personal info not found"));

	        double totalYears = calculateTotalExperience(jobSeekerId);
	        int roundedYears = (int) Math.floor(totalYears);

	        LocalDate today = LocalDate.now();
	       
	        
	        return jobPostRepository.findTodayMatches(
	                today,
	                personalInfo.getCity(),
	                jobSeeker.getSkills(),
	                roundedYears
	                
	               
	        );
	    }

	    public Long getTodayJobMatchesCountForSeeker(int jobSeekerId) {
	        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found"));

	        JobSeekerPersonalInfo personalInfo = jobSeekerPersonalInfoRepository.findByJobSeekerId(jobSeekerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Personal info not found"));

	        double totalYears = calculateTotalExperience(jobSeekerId);
	        int roundedYears = (int) Math.floor(totalYears);

	        LocalDate today = LocalDate.now();
	        
	     
	      
	        return jobPostRepository.countTodayMatches(
	                today,
	                personalInfo.getCity().trim(),
	                jobSeeker.getSkills(),
	                roundedYears
	        );
	    }
}
