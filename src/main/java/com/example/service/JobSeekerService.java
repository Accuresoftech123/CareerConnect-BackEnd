package com.example.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public ResponseEntity<?> register(JobSeekerRegistrationDto newJobSeeker) {
		// Check if a JobSeeker with the given email already exists
		Optional<JobSeeker> existing = repo.findByEmail(newJobSeeker.getEmail());

		if (existing.isPresent()) {
			 return ResponseEntity.status(HttpStatus.CONFLICT)
                     .body("Email already registered!");
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

		// pass the id to fronted
		Map<String, Object> response = new HashMap<>();
	    response.put("message", "OTP sent. Please verify your account.");
	    response.put("jobSeekerId", jobSeeker.getId());

	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

	public ResponseEntity<?> updateJobSeekerProfile(int id, JobSeekerProfileDto dto) {

		JobSeeker jobSeeker = repo.findById(id).orElse(null);

		if (jobSeeker == null) {

			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body("Job Seeker not found with ID: ");
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
		//List<JobSeekerEducationDto> educationDtoList = dto.getEducationList();

		if (dto.getEducationList() != null) {

			// store the new education object
			List<Education> updateEducations = new ArrayList<>();

			for (JobSeekerEducationDto num : dto.getEducationList()) {

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

	//	List<JobSeekerExperienceDto> experienceDtoList = dto.getExperienceList();

		if (dto.getExperienceList() != null) {

			// store the new experience object
			List<Experience> updateExperienceList = new ArrayList<>();

			for (JobSeekerExperienceDto num : dto.getExperienceList()) {

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

		return ResponseEntity.ok("Profile updated successfully");
	}



	
	
	//track profile
	public Map<String, Object> getProfileCompletionStatus(JobSeeker jobSeeker) {
	    int totalFields = 9;
	    int filledFields = 0;
	    List<String> emptyFields = new ArrayList<>();

	    if (jobSeeker.getFullName() != null && !jobSeeker.getFullName().isBlank()) filledFields++;
	    else emptyFields.add("Full Name");

	    if (jobSeeker.getMobileNumber() != null && !jobSeeker.getMobileNumber().isBlank()) filledFields++;
	    else emptyFields.add("Mobile Number");

	    if (jobSeeker.getPersonalInfo() != null &&
	        jobSeeker.getPersonalInfo().getCity() != null &&
	        !jobSeeker.getPersonalInfo().getCity().isBlank()) filledFields++;
	    else emptyFields.add("Personal Info");

	    if (jobSeeker.getEducationList() != null && !jobSeeker.getEducationList().isEmpty()) filledFields++;
	    else emptyFields.add("Education");

	    if (jobSeeker.getExperienceList() != null && !jobSeeker.getExperienceList().isEmpty()) filledFields++;
	    else emptyFields.add("Experience");

	    if (jobSeeker.getSkills() != null && !jobSeeker.getSkills().isEmpty()) filledFields++;
	    else emptyFields.add("Skills");

	    if (jobSeeker.getSocialProfile() != null &&
	        jobSeeker.getSocialProfile().getLinkedinUrl() != null &&
	        !jobSeeker.getSocialProfile().getLinkedinUrl().isBlank()) filledFields++;
	    else emptyFields.add("Social Profile");

	    if (jobSeeker.getJobPrefeences() != null &&
	        jobSeeker.getJobPrefeences().getDesiredJobTitle() != null &&
	        !jobSeeker.getJobPrefeences().getDesiredJobTitle().isBlank()) filledFields++;
	    else emptyFields.add("Job Preferences");

	    if (jobSeeker.isVerified()) filledFields++;
	    else emptyFields.add("Email Verification");

	    int percentage = (filledFields * 100) / totalFields;

	    Map<String, Object> response = new HashMap<>();
	    response.put("completionPercentage", percentage);
	    response.put("missingFields", emptyFields);

	    return response;
	}
	//Forgate password
	//Validate Otp and reset password
	
	
	public boolean validateOtpAndResetPassword(String email, String inputOtp, String newPassword ) {
		JobSeeker seeker = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		if(seeker.getOtpGeneratedTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
			return false;
		}
		
		if(seeker.getOtp().equals(inputOtp)) {
			seeker.setPassword(newPassword);
			seeker.setOtp(null);
			seeker.setOtpGeneratedTime(null);
			repo.save(seeker);
			return true;
		}
		return false;
	}
	
	
	
	
	
}
