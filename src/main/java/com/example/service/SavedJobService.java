package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.SavedJobPostReportDto;
import com.example.entity.JobSeeker;
import com.example.entity.jobposting.JobPost;
import com.example.entity.jobposting.SavedJob;
import com.example.exception.JobAlreadySavedException;
import com.example.repository.JobPostRepository;
import com.example.repository.JobSeekerRepository;
import com.example.repository.SavedJobRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class SavedJobService {

	@Autowired
	private SavedJobRepository savedJobRepo;

	@Autowired
	private JobSeekerRepository jobSeekerReposiotry;

	@Autowired
	private JobPostRepository jobPostRepository;

	// function for saved job
	public void saveJob(int jobSeekerId, int jobPostId) {
	    JobSeeker jobSeeker = jobSeekerReposiotry.findById(jobSeekerId)
	            .orElseThrow(() -> new RuntimeException("JobSeeker not found"));

	    JobPost jobPost = jobPostRepository.findById(jobPostId)
	            .orElseThrow(() -> new RuntimeException("JobPost not found"));

	    savedJobRepo.findByJobSeekerAndJobPost(jobSeeker, jobPost).ifPresent(saved -> {
	        throw new JobAlreadySavedException("Job already saved");
	    });

	    SavedJob savedJob = new SavedJob();
	    savedJob.setJobSeeker(jobSeeker);
	    savedJob.setJobPost(jobPost);
	    savedJobRepo.save(savedJob);
	}


	// function for get all saved jobs

//	public List<SavedJobPostReportDto> getSavedJobPostDtos(int jobSeekerId) {
//		JobSeeker jobSeeker = jobSeekerReposiotry.findById(jobSeekerId)
//				.orElseThrow(() -> new RuntimeException("JobSeeker not found"));
//
//		List<SavedJob> savedJobs = savedJobRepo.findByJobSeeker(jobSeeker);
//
//		return savedJobs.stream().map(savedJob -> {
//			JobPost job = savedJob.getJobPost();
//			return new SavedJobPostReportDto(job.getId(), job.getTitle(), job.getCompanyName(), job.getLocation(),
//					job.getJobType(), job.getMinSalary(), job.getMaxSalary(), job.getMinExperience(),
//					job.getMaxExperience(), job.getSkills(), job.getPostedDate());
//		}).collect(Collectors.toList());
//
//	}

	@Transactional
	public void removeSavedJob(int jobSeekerId, int jobPostId) {
		if (!jobSeekerReposiotry.existsById(jobSeekerId)) {
			throw new RuntimeException("JobSeeker not found");
		}
		if (!jobPostRepository.existsById(jobPostId)) {
			throw new RuntimeException("JobPost not found");
		}

		savedJobRepo.deleteByJobSeekerIdAndJobPostId(jobSeekerId, jobPostId);
	}

	public Long countOfSavedJobes() {
		return savedJobRepo.count();
	}

}
