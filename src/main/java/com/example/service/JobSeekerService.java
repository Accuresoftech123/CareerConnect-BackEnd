package com.example.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.JobSeekerEducationDto;
import com.example.dto.JobSeekerExperienceDto;
import com.example.dto.JobSeekerJonPreferencesDto;
import com.example.dto.JobSeekerPersonalInfoDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerRegistrationDto;
import com.example.dto.JobSeekerSocialProfileDto;
import com.example.entity.JobSeeker;
import com.example.entity.profile.Education;
import com.example.entity.profile.Experience;
import com.example.entity.profile.JobPreferences;
import com.example.entity.profile.JobSeekerPersonalInfo;
import com.example.entity.profile.SocialProfile;
import com.example.repository.JobSeekerRepository;

/**
 * Service class to manage Job Seeker registration, login, and profile updates.
 */
@Service
public class JobSeekerService {

	@Autowired
	private JobSeekerRepository repo;
	@Autowired
	private EmailService emailService;

	/**
	 * Registers a new job seeker if the email is not already taken.
	 *
	 * @param newJobSeeker the registration details of the new job seeker
	 * @return message indicating registration success or failure
	 */

	public String register(JobSeekerRegistrationDto newJobSeeker) {
		// Check if a JobSeeker with the given email already exists
		Optional<JobSeeker> existing = repo.findByEmail(newJobSeeker.getEmail());

		if (existing.isPresent()) {
			return "Email already registered!";
		}

		// Create a new JobSeeker entity from DTO
		JobSeeker jobSeeker = new JobSeeker();
		jobSeeker.setFullName(newJobSeeker.getFullName());
		jobSeeker.setEmail(newJobSeeker.getEmail());
		jobSeeker.setMobileNumber(newJobSeeker.getMobileNumber());
		jobSeeker.setPassword(newJobSeeker.getPassword());
		jobSeeker.setConfirmPassword(newJobSeeker.getConfirmPassword());

		// calling OTP generation method from email service.......
		emailService.generateAndSendOtp(jobSeeker);

		return "OTP has been sent to your email. Please verify your account to complete the registration process.";
	}

	/**
	 * Validates login credentials.
	 *
	 * @param email    the email address
	 * @param password the password
	 * @return JobSeeker object if login is successful, otherwise null
	 */
	public JobSeeker login(String email, String password) {
		// Find JobSeeker by email
		JobSeeker existingJobSeeker = repo.findByEmail(email).orElse(null);

		// Verify password match
		if (existingJobSeeker != null && existingJobSeeker.getPassword().equals(password)) {
			return existingJobSeeker;
		}
		return null;
	}

	// update jobseeker profile

	public String updateJobSeekerProfile(int id, JobSeekerProfileDto dto) {

		JobSeeker jobSeeker = repo.findById(id).orElse(null);

		if (jobSeeker == null) {

			return "Job Seeker not found with ID: " + id;
		}

		// update registration data

		if (dto.getFullName() != null) {
			jobSeeker.setFullName(dto.getFullName());
		}

		if (dto.getMobileNumber() != null) {
			jobSeeker.setMobileNumber(dto.getMobileNumber());
		}

		// update personal info
		JobSeekerPersonalInfoDto personalInfoDto = dto.getPersonalInfo();

		if (personalInfoDto != null) {

			JobSeekerPersonalInfo personalInfo = jobSeeker.getPersonalInfo();

			// check if the personalInfo is null , then create new object
			if (personalInfo == null) {
				personalInfo = new JobSeekerPersonalInfo();
			}

			if (personalInfoDto.getCity() != null)
				personalInfo.setCity(personalInfoDto.getCity());
			if (personalInfoDto.getState() != null)
				personalInfo.setState(personalInfoDto.getState());
			if (personalInfoDto.getCountry() != null)
				personalInfo.setCountry(personalInfoDto.getCountry());
			if (personalInfoDto.getResumeUrl() != null)
				personalInfo.setResumeUrl(personalInfoDto.getResumeUrl());
			if (personalInfoDto.getIntroVideoUrl() != null)
				personalInfo.setIntroVideoUrl(personalInfoDto.getIntroVideoUrl());
			if (personalInfoDto.getProfileImageUrl() != null)
				personalInfo.setProfileImageUrl(personalInfoDto.getProfileImageUrl());
			personalInfo.setJobSeeker(jobSeeker);

			jobSeeker.setPersonalInfo(personalInfo);

		}

		// update education

		// seperate the educationdto from profile dto
		List<JobSeekerEducationDto> educationDtoList = dto.getEducationList();

		if (educationDtoList != null) {

			// store the new education object
			List<Education> updateEducations = new ArrayList<>();

			for (JobSeekerEducationDto num : educationDtoList) {

				Education education = new Education();

				// create object or save each object of education
				if (num.getDegree() != null)
					education.setDegree(num.getDegree());
				if (num.getFieldOfStudy() != null)
					education.setFieldOfStudy(num.getFieldOfStudy());
				if (num.getInstitution() != null)
					education.setInstitution(num.getInstitution());
				if (num.getPassingYear() != null && num.getPassingYear() >= 1950) {
					education.setPassingYear(num.getPassingYear());
				}
				education.setJobSeeker(jobSeeker);

				// add education object in array list

				updateEducations.add(education);
			}

			jobSeeker.setEducationList(updateEducations);

		}

		// update experience

		List<JobSeekerExperienceDto> experienceDtoList = dto.getExperienceList();

		if (experienceDtoList != null) {

			// store the new experience object
			List<Experience> updateExperienceList = new ArrayList<>();

			for (JobSeekerExperienceDto num : experienceDtoList) {

				// create object of experience to access the data
				Experience experience = new Experience();

				if (num.getJobTitle() != null)
					experience.setJobTitle(num.getJobTitle());
				if (num.getCompanyName() != null)
					experience.setCompanyName(num.getCompanyName());
				if (num.getStartDate() != null)
					experience.setStartDate(num.getStartDate());
				if (num.getEndDate() != null) {
					experience.setEndDate(num.getEndDate());
				} else {
					experience.setEndDate(LocalDate.now());
				}
				if (num.getKeyResponsibilities() != null)
					experience.setKeyResponsibilities(num.getKeyResponsibilities());
				experience.setJobSeeker(jobSeeker);

				updateExperienceList.add(experience);

			}

			jobSeeker.setExperienceList(updateExperienceList);
		}

		// update skills

		List<String> skills = dto.getSkills();

		if (skills != null && !skills.isEmpty()) {

			List<String> existingSkills = jobSeeker.getSkills();

			if (existingSkills == null) {

				existingSkills = new ArrayList<>();
			}

			for (String skill : skills) {

				if (skill != null && !existingSkills.contains(skill)) {
					existingSkills.add(skill);
				}
			}

			jobSeeker.setSkills(existingSkills);
		}

		// update social media

		JobSeekerSocialProfileDto socialProfileDto = dto.getScoicalProfile();

		if (socialProfileDto != null) {

			SocialProfile socialProfile = jobSeeker.getSocialProfile();
			// null check and initialization for socialprofile

			if (socialProfile == null) {
				socialProfile = new SocialProfile();
			}

			if (socialProfileDto.getLinkedinUrl() != null)
				socialProfile.setLinkedinUrl(socialProfileDto.getLinkedinUrl());
			if (socialProfileDto.getGithubUrl() != null)
				socialProfile.setGithubUrl(socialProfileDto.getGithubUrl());
			if (socialProfileDto.getPortfolioWebsite() != null)
				socialProfile.setPortfolioWebsite(socialProfileDto.getPortfolioWebsite());
			socialProfile.setJobSeeker(jobSeeker);

			jobSeeker.setSocialProfile(socialProfile);
		}

		// update job preferences

		JobSeekerJonPreferencesDto preferencesDto = dto.getJobPreferences();

		if (preferencesDto != null) {

			// null check and intilization for job preference
			JobPreferences preferences = jobSeeker.getJobPrefeences();
			if (preferences == null) {
				preferences = new JobPreferences();
			}

			if (preferencesDto.getDesiredJobTitle() != null)
				preferences.setDesiredJobTitle(preferencesDto.getDesiredJobTitle());
			if (preferencesDto.getJobType() != null)
				preferences.setJobType(preferencesDto.getJobType());
			if (preferencesDto.getExpectedSalary() != 0)
				preferences.setExpectedSalary(preferencesDto.getExpectedSalary());
			if (preferencesDto.getPreferredLocation() != null)
				preferences.setPreferredLocation(preferencesDto.getPreferredLocation());
			preferences.setJobSeeker(jobSeeker);

			jobSeeker.setJobPrefeences(preferences);
		}

		repo.save(jobSeeker);

		return "Profile updated successfully";
	}

//    /**
//     * Updates an existing job seeker's profile with the provided data.
//     *
//     * @param id  ID of the job seeker
//     * @param dto DTO containing the profile information to be updated
//     * @return status message
//     */
//    public String updateJobSeekerProfile(int id, JobSeekerProfileDto dto) {
//        JobSeeker jobSeeker = repo.findById(id).orElse(null);
//
//        if (jobSeeker == null) {
//            return "Job Seeker not found with ID: " + id;
//        }
//
//        // Proceed only if DTO contains data
//        if (!isDtoEmpty(dto)) {
//            // Update non-null and valid fields
//            if (dto.getFullName() != null) jobSeeker.setFullName(dto.getFullName());         
//            if (dto.getMobileNumber() != null) jobSeeker.setMobileNumber(dto.getMobileNumber());
//            if (dto.getAddress() != null) jobSeeker.setAddress(dto.getAddress());
//            if (dto.getGender() != null) jobSeeker.setGender(dto.getGender());
//            if (dto.getDateOfBirth() != null) jobSeeker.setDateOfBirth(dto.getDateOfBirth());
//            if (dto.getProfileSummary() != null) jobSeeker.setProfileSummary(dto.getProfileSummary());
//            if (dto.getHighestEducationQualification() != null) jobSeeker.setHighestEducationQualification(dto.getHighestEducationQualification());
//            if (dto.getYearOfPassing() != null) jobSeeker.setYearOfPassing(dto.getYearOfPassing());
//            if (dto.getCollegeName() != null) jobSeeker.setCollegeName(dto.getCollegeName());
//            if (dto.getSkills() != null) jobSeeker.setSkills(dto.getSkills());
//            if (dto.getYearsOfExperience() > 0) jobSeeker.setYearsOfExperience(dto.getYearsOfExperience());
//            if (dto.getResumeUrl() != null) jobSeeker.setResumeUrl(dto.getResumeUrl());
//            if (dto.getGithubProfileUrl()!= null) jobSeeker.setGithubProfileUrl(dto.getGithubProfileUrl());
//            if (dto.getProfileImageUrl() != null) jobSeeker.setProfileImageUrl(dto.getProfileImageUrl());
//            if (dto.getPreferredJobLocation() != null) jobSeeker.setPreferredJobLocation(dto.getPreferredJobLocation());
//            if (dto.getNoticePeriod() != null) jobSeeker.setNoticePeriod(dto.getNoticePeriod());
//            if (dto.getCurrentCtc() != null) jobSeeker.setCurrentCtc(dto.getCurrentCtc());
//            if (dto.getExpectedCtc() != null) jobSeeker.setExpectedCtc(dto.getExpectedCtc());
//
//            jobSeeker.setProfileComplete(dto.isProfileComplete());
//
//            jobSeeker.setUpdatedAt(LocalDateTime.now());
//
//            // Save the updated entity
//            repo.save(jobSeeker);
//            return "Job Seeker profile updated successfully!";
//        } else {
//            return "Profile data is empty.";
//        }
//    }
//
//    /**
//     * Checks if the JobSeekerProfileDto is completely empty (i.e., no data to update).
//     *
//     * @param dto the DTO to check
//     * @return true if empty, false otherwise
//     */
//    private boolean isDtoEmpty(JobSeekerProfileDto dto) {
//        return dto.getFullName() == null &&          
//               dto.getMobileNumber() == null &&
//               dto.getAddress() == null &&
//               dto.getGender() == null &&
//               dto.getDateOfBirth() == null &&
//               dto.getProfileSummary() == null &&
//               dto.getHighestEducationQualification() == null &&
//               dto.getYearOfPassing() == null &&
//               dto.getCollegeName() == null &&
//               dto.getSkills() == null &&
//               dto.getYearsOfExperience() <= 0 &&
//               dto.getResumeUrl() == null &&
//               dto.getGithubProfileUrl() == null &&
//               dto.getProfileImageUrl() == null &&
//               dto.getPreferredJobLocation() == null &&
//               dto.getNoticePeriod() == null &&
//               dto.getCurrentCtc() == null &&
//               dto.getExpectedCtc() == null &&
//               !dto.isProfileComplete();  // false means profile is complete → not empty
//    }
}
