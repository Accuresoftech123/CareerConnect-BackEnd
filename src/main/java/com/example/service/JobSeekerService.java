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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.example.enums.Role;
import com.example.repository.JobSeekerEducationRepository;
import com.example.repository.JobSeekerExperienceRepository;
import com.example.repository.JobSeekerPersonalInfoRepository;
import com.example.repository.JobSeekerRepository;
import com.itextpdf.io.exceptions.IOException;
import com.example.security.CustomUserDetails;
import com.example.security.CustomUserDetailsService;
import com.example.security.JobSeekerUserDetailsService;
import com.example.security.JwtUtil;

/**
 * Service class to manage Job Seeker registration, login, and profile updates.
 */
@Service
public class JobSeekerService {

	@Autowired
	private JwtUtil jwtUtil;

	    @Autowired
	    private JobSeekerUserDetailsService jobSeekerUserDetailsService;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private JobSeekerPersonalInfoRepository personalInfoRepo;
	    
	    @Autowired
	    private JobSeekerEducationRepository educationRepository;
	    
	    @Autowired
	    private JobSeekerExperienceRepository experienceRepository;
	
	

	@Autowired
	private JobSeekerRepository repo;
	@Autowired
	private EmailService emailService;

	@Autowired
	private CloudinaryService cloudinaryService;

	/**
	 * Registers a new job seeker if the email is not already taken.
	 *
	 * @param newJobSeeker the registration details of the new job seeker
	 * @return message indicating registration success or failure
	 */

	public ResponseEntity<?> register(JobSeekerRegistrationDto newJobSeeker) {
		// Check if JobSeeker already exists
		Optional<JobSeeker> existing = repo.findByEmail(newJobSeeker.getEmail());

		if (existing.isPresent()) {
			JobSeeker existingJobSeeker = existing.get();

			if (!existingJobSeeker.isVerified()) {
				// Resend OTP to unverified JobSeeker
				emailService.generateAndSendOtp(existingJobSeeker);

				return ResponseEntity.ok(
						Map.of("success", true, "message", "Email already registered but not verified. OTP re-sent.",
								"jobSeekerId", existingJobSeeker.getId()));
			}

			// Already verified
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(Map.of("success", false, "message", "Email already registered and verified. Please login."));
		}

		// Create a new JobSeeker entity from DTO
		JobSeeker jobSeeker = new JobSeeker();
		jobSeeker.setFullName(newJobSeeker.getFullName());
		jobSeeker.setEmail(newJobSeeker.getEmail());
		jobSeeker.setMobileNumber(newJobSeeker.getMobileNumber());

		// ✅ Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newJobSeeker.getPassword());
		jobSeeker.setPassword(encodedPassword);

		// (Optional) You don't need to store confirm password, but if you must:
		// jobSeeker.setConfirmPassword(encodedPassword);

		jobSeeker.setConfirmPassword(newJobSeeker.getConfirmPassword());
		jobSeeker.setRole(Role.ROLE_JOBSEEKER);

		// ✅ Save job seeker first so it has an ID before sending OTP
		// repo.save(jobSeeker);
		JobSeeker saved = repo.save(jobSeeker);

		// calling OTP generation method from email service.......
		emailService.generateAndSendOtp(saved);

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
//	public JobSeeker login(String email, String password) {
//		// Find JobSeeker by email
//		JobSeeker existingJobSeeker = repo.findByEmail(email).orElse(null);
//
//		// Verify password match
//		if (existingJobSeeker != null && existingJobSeeker.getPassword().equals(password)) {
//			return existingJobSeeker;
//		}
//		return null;
//	}

	public ResponseEntity<?> login(String email, String password) {

		JobSeeker jobSeeker = repo.findByEmail(email).orElse(null);

		if (jobSeeker == null || !passwordEncoder.matches(password, jobSeeker.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}

		// ✅ Load user details for JWT

		UserDetails userDetails = jobSeekerUserDetailsService.loadUserByUsername(email);
		CustomUserDetails customUser = (CustomUserDetails) userDetails;

		// Generate JWT token with role
		String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());

		// ✅ Return token and role
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("role", jobSeeker.getRole().name());
		response.put("id", jobSeeker.getId());

		return ResponseEntity.ok(response);
	}

	// update jobseeker profile
	public ResponseEntity<?> updateJobSeekerProfile(int id, JobSeekerProfileDto dto, MultipartFile resumeFile,
			MultipartFile videoFile, MultipartFile imageFile) throws java.io.IOException {

		JobSeeker jobSeeker = repo.findById(id).orElse(null);

		if (jobSeeker == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Seeker not found with ID: " + id);

		}

		// update registration data

		if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
			jobSeeker.setFullName(dto.getFullName());
		}

		if (dto.getMobileNumber() != null && !dto.getMobileNumber().isEmpty()) {
			jobSeeker.setMobileNumber(dto.getMobileNumber());
		}

		// update personal info
		JobSeekerPersonalInfoDto personalInfoDto = dto.getPersonalInfo();
		
		CompletableFuture<String> resumeFuture = null;
	    CompletableFuture<String> videoFuture = null;
	    CompletableFuture<String> imageFuture = null;
	    
	    if (resumeFile != null && !resumeFile.isEmpty()) {
	        resumeFuture = cloudinaryService.uploadFileAsync(resumeFile, "jobseeker/resumes");
	    }

	    if (videoFile != null && !videoFile.isEmpty()) {
	        videoFuture = cloudinaryService.uploadFileAsync(videoFile, "jobseeker/videos");
	    }

	    if (imageFile != null && !imageFile.isEmpty()) {
	        imageFuture = cloudinaryService.uploadFileAsync(imageFile, "jobseeker/images");
	    }

	    CompletableFuture.allOf(
	            resumeFuture != null ? resumeFuture : CompletableFuture.completedFuture(""),
	            videoFuture != null ? videoFuture : CompletableFuture.completedFuture(""),
	            imageFuture != null ? imageFuture : CompletableFuture.completedFuture("")
	    ).join(); 
	    
		if (personalInfoDto != null) {

			JobSeekerPersonalInfo personalInfo = jobSeeker.getPersonalInfo();

			 if (personalInfo == null) {
			        // ✅ Try to fetch existing personal info from DB (if previously saved)
			        Optional<JobSeekerPersonalInfo> optionalPersonalInfo = personalInfoRepo.findByJobSeekerId(jobSeeker.getId());

			        if (optionalPersonalInfo.isPresent()) {
			            personalInfo = optionalPersonalInfo.get(); // ✅ use DB record
			           
			        } else {
			            personalInfo = new JobSeekerPersonalInfo(); // ✅ new record if not found
			            personalInfo.setJobSeeker(jobSeeker);
			            jobSeeker.setPersonalInfo(personalInfo);
			        }
			    }

			if (personalInfoDto.getCity() != null && !personalInfoDto.getCity().isEmpty())
				personalInfo.setCity(personalInfoDto.getCity());
			if (personalInfoDto.getState() != null && !personalInfoDto.getState().isEmpty())
				personalInfo.setState(personalInfoDto.getState());
			if (personalInfoDto.getCountry() != null && !personalInfoDto.getCountry().isEmpty())
				personalInfo.setCountry(personalInfoDto.getCountry());
			try {
				if (resumeFuture != null) {
		            try {
						personalInfo.setResumeUrl(resumeFuture.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        if (videoFuture != null) {
		            try {
						personalInfo.setIntroVideoUrl(videoFuture.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        if (imageFuture != null) {
		            try {
						personalInfo.setProfileImageUrl(imageFuture.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("File upload failed: " + e.getMessage());
			}

			personalInfo.setJobSeeker(jobSeeker);
			jobSeeker.setPersonalInfo(personalInfo);

		}

		// update education

		// seperate the educationdto from profile dto
		// List<JobSeekerEducationDto> educationDtoList = dto.getEducationList();

		if (dto.getEducationList() != null) {

			// store the new education object
			List<Education> updateEducations = new ArrayList<>();

			for (JobSeekerEducationDto num : dto.getEducationList()) {

				Education education;
				
				 if (num.getId() != 0) {
			            // Try to fetch existing education record
			            Optional<Education> existingEdu = educationRepository.findById(num.getId());

			            if (existingEdu.isPresent()) {
			                education = existingEdu.get();
			            } else {
			                // If not found, create new (optional fallback)
			                education = new Education();
			            }
			        } else {
			            // If no ID is passed, create new
			            education = new Education();
			        }

				// create object or save each object of education
				if (num.getDegree() != null && !num.getDegree().isEmpty())
					education.setDegree(num.getDegree());
				if (num.getFieldOfStudy() != null && !num.getFieldOfStudy().isEmpty())
					education.setFieldOfStudy(num.getFieldOfStudy());
				if (num.getInstitution() != null && !num.getInstitution().isEmpty())
					education.setInstitution(num.getInstitution());
				if (num.getPassingYear() != null && num.getPassingYear() >= 1950 && num.getPassingYear() != 0) {
					education.setPassingYear(num.getPassingYear());
				}
				education.setJobSeeker(jobSeeker);

				// add education object in array list

				updateEducations.add(education);
			}

			jobSeeker.setEducationList(updateEducations);

		}

		// update experience

		// List<JobSeekerExperienceDto> experienceDtoList = dto.getExperienceList();

		if (dto.getExperienceList() != null) {

			// store the new experience object
			List<Experience> updateExperienceList = new ArrayList<>();

			for (JobSeekerExperienceDto num : dto.getExperienceList()) {

				// create object of experience to access the data
				 Experience experience;

			        if (num.getId() != 0) {
			            // Try to fetch existing experience record
			            Optional<Experience> existingExp = experienceRepository.findById(num.getId());

			            if (existingExp.isPresent()) {
			                experience = existingExp.get();
			            } else {
			                // If not found, create new (optional fallback)
			                experience = new Experience();
			            }
			        } else {
			            // If no ID is passed, create new
			            experience = new Experience();
			        }

				if (num.getJobTitle() != null && !num.getJobTitle().isEmpty())
					experience.setJobTitle(num.getJobTitle());
				if (num.getCompanyName() != null && !num.getCompanyName().isEmpty())
					experience.setCompanyName(num.getCompanyName());
				if (num.getStartDate() != null )
					experience.setStartDate(num.getStartDate());
				if (num.getEndDate() != null) {
					experience.setEndDate(num.getEndDate());
				} else {
					experience.setEndDate(LocalDate.now());
				}
				if (num.getKeyResponsibilities() != null && !num.getKeyResponsibilities().isEmpty())
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

			if (socialProfileDto.getLinkedinUrl() != null && !socialProfileDto.getLinkedinUrl().isEmpty() )
				socialProfile.setLinkedinUrl(socialProfileDto.getLinkedinUrl());
			if (socialProfileDto.getGithubUrl() != null && !socialProfileDto.getGithubUrl().isEmpty())
				socialProfile.setGithubUrl(socialProfileDto.getGithubUrl());
			if (socialProfileDto.getPortfolioWebsite() != null && !socialProfileDto.getPortfolioWebsite().isEmpty())
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

			if (preferencesDto.getDesiredJobTitle() != null && !preferencesDto.getDesiredJobTitle().isEmpty()) {
				
				List<String> existingDesiredJobTitle = preferences.getDesiredJobTitle();
				
				if(existingDesiredJobTitle == null) {
					existingDesiredJobTitle = new ArrayList<>();
				}
				
				for (String newType : preferencesDto.getDesiredJobTitle()) {
			        if (newType != null && !existingDesiredJobTitle.contains(newType)) {
			        	existingDesiredJobTitle.add(newType); // ✅ add only if not already present
			        }
			    }
				preferences.setDesiredJobTitle(existingDesiredJobTitle);
			}
				
			
			
			if (preferencesDto.getJobTypes() != null && !preferencesDto.getJobTypes().isEmpty()) {
				 List<String> existingJobTypes = preferences.getJobTypes();
		    
		    if (existingJobTypes == null) {
		        existingJobTypes = new ArrayList<>();
		    }

		    for (String newType : preferencesDto.getJobTypes()) {
		        if (newType != null && !existingJobTypes.contains(newType)) {
		            existingJobTypes.add(newType); // ✅ add only if not already present
		        }
		    }

		    preferences.setJobTypes(existingJobTypes);
			}
			
			
			if (preferencesDto.getExpectedSalary() != 0 )
				preferences.setExpectedSalary(preferencesDto.getExpectedSalary());
			if (preferencesDto.getPreferredLocation() != null && !preferencesDto.getPreferredLocation().isEmpty())
				preferences.setPreferredLocation(preferencesDto.getPreferredLocation());
			preferences.setJobSeeker(jobSeeker);

			jobSeeker.setJobPrefeences(preferences);
		}

		repo.save(jobSeeker);

		return ResponseEntity.ok("Profile updated successfully");
	}

	// track profile
	public Map<String, Object> getProfileCompletionStatus(JobSeeker jobSeeker) {
		int totalFields = 9;
		int filledFields = 0;
		List<String> emptyFields = new ArrayList<>();

		if (jobSeeker.getFullName() != null && !jobSeeker.getFullName().isBlank())
			filledFields++;
		else
			emptyFields.add("Full Name");

		if (jobSeeker.getMobileNumber() != null && !jobSeeker.getMobileNumber().isBlank())
			filledFields++;
		else
			emptyFields.add("Mobile Number");

		if (jobSeeker.getPersonalInfo() != null && jobSeeker.getPersonalInfo().getCity() != null
				&& !jobSeeker.getPersonalInfo().getCity().isBlank())
			filledFields++;
		else
			emptyFields.add("Personal Info");

		if (jobSeeker.getEducationList() != null && !jobSeeker.getEducationList().isEmpty())
			filledFields++;
		else
			emptyFields.add("Education");

		if (jobSeeker.getExperienceList() != null && !jobSeeker.getExperienceList().isEmpty())
			filledFields++;
		else
			emptyFields.add("Experience");

		if (jobSeeker.getSkills() != null && !jobSeeker.getSkills().isEmpty())
			filledFields++;
		else
			emptyFields.add("Skills");

		if (jobSeeker.getSocialProfile() != null && jobSeeker.getSocialProfile().getLinkedinUrl() != null
				&& !jobSeeker.getSocialProfile().getLinkedinUrl().isBlank())
			filledFields++;
		else
			emptyFields.add("Social Profile");

		if (jobSeeker.getJobPrefeences() != null && jobSeeker.getJobPrefeences().getDesiredJobTitle() != null
				&& !jobSeeker.getJobPrefeences().getDesiredJobTitle().isEmpty())
			filledFields++;
		else
			emptyFields.add("Job Preferences");

		if (jobSeeker.isVerified())
			filledFields++;
		else
			emptyFields.add("Email Verification");

		int percentage = (filledFields * 100) / totalFields;

		Map<String, Object> response = new HashMap<>();
		response.put("profileCompletion", percentage);
		response.put("missingFields", emptyFields);

		return response;
	}
	// Forgate password
	// Validate Otp and reset password

	public boolean validateOtpAndResetPassword(String email, String inputOtp, String newPassword) {
		JobSeeker seeker = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		if (seeker.getOtpGeneratedTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
			return false;
		}
		if (seeker.getOtp().equals(inputOtp)) {
			seeker.setPassword(passwordEncoder.encode(newPassword));
			seeker.setOtp(null);
			seeker.setOtpGeneratedTime(null);
			repo.save(seeker);
			return true;
		}
		return false;

	}

	// jobseeker get by id personal info
	public ResponseEntity<?> getJobSeekerImageAndName(int id) {
		Optional<JobSeeker> optionalJobSeeker = repo.findById(id);

		if (optionalJobSeeker.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Seeker not found with ID: " + id);
		}

		JobSeeker jobSeeker = optionalJobSeeker.get();

		// Get Personal Info
		if (jobSeeker.getPersonalInfo() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personal Info not found for Job Seeker ID: " + id);
		}

		JobSeekerPersonalInfoDto infoDto = new JobSeekerPersonalInfoDto();
		infoDto.setFullName(jobSeeker.getFullName());
		infoDto.setProfileImageUrl(jobSeeker.getPersonalInfo().getProfileImageUrl());

	    return ResponseEntity.ok(infoDto);
	}
	
	
	
	    public JobSeekerProfileDto getJobSeekerProfile(int id) {
	        JobSeeker jobSeeker = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("JobSeeker not found with id: " + id));

	        JobSeekerProfileDto dto = new JobSeekerProfileDto();
	        dto.setId(jobSeeker.getId());
	        dto.setFullName(jobSeeker.getFullName());
	        dto.setMobileNumber(jobSeeker.getMobileNumber());
	        dto.setSkills(jobSeeker.getSkills());

	        // Personal Info
	        JobSeekerPersonalInfo personalInfo = jobSeeker.getPersonalInfo();
	        if (personalInfo != null) {
	            JobSeekerPersonalInfoDto personalInfoDto = new JobSeekerPersonalInfoDto();
	            personalInfoDto.setFullName(jobSeeker.getFullName());
	            personalInfoDto.setCity(personalInfo.getCity());
	            personalInfoDto.setState(personalInfo.getState());
	            personalInfoDto.setCountry(personalInfo.getCountry());
	            personalInfoDto.setResumeUrl(personalInfo.getResumeUrl());
	            personalInfoDto.setIntroVideoUrl(personalInfo.getIntroVideoUrl());
	            personalInfoDto.setProfileImageUrl(personalInfo.getProfileImageUrl());
	            dto.setPersonalInfo(personalInfoDto);
	        }

	        // Education
	        if (jobSeeker.getEducationList() != null) {
	            List<JobSeekerEducationDto> educationDtos = jobSeeker.getEducationList().stream()
	                .map(edu -> {
	                    JobSeekerEducationDto edto = new JobSeekerEducationDto();
	                    edto.setId(edu.getId());
	                    edto.setDegree(edu.getDegree());
	                    edto.setFieldOfStudy(edu.getFieldOfStudy());
	                    edto.setInstitution(edu.getInstitution());
	                    edto.setPassingYear(edu.getPassingYear());
	                    return edto;
	                }).collect(Collectors.toList());
	            dto.setEducationList(educationDtos);
	        }

	        // Experience
	        if (jobSeeker.getExperienceList() != null) {
	            List<JobSeekerExperienceDto> experienceDtos = jobSeeker.getExperienceList().stream()
	                .map(exp -> {
	                    JobSeekerExperienceDto exdto = new JobSeekerExperienceDto();
	                    exdto.setId(exp.getId());
	                    exdto.setJobTitle(exp.getJobTitle());
	                    exdto.setCompanyName(exp.getCompanyName());
	                    exdto.setStartDate(exp.getStartDate());
	                    exdto.setEndDate(exp.getEndDate());
	                    exdto.setKeyResponsibilities(exp.getKeyResponsibilities());
	                    return exdto;
	                }).collect(Collectors.toList());
	            dto.setExperienceList(experienceDtos);
	        }

	        // Social Profile
	        SocialProfile social = jobSeeker.getSocialProfile();
	        if (social != null) {
	            JobSeekerSocialProfileDto socialDto = new JobSeekerSocialProfileDto();
	            socialDto.setLinkedinUrl(social.getLinkedinUrl());
	            socialDto.setGithubUrl(social.getGithubUrl());
	            socialDto.setPortfolioWebsite(social.getPortfolioWebsite());
	            dto.setScoicalProfile(socialDto);
	        }

	        // Job Preferences
	        JobPreferences prefs = jobSeeker.getJobPrefeences();
	        if (prefs != null) {
	            JobSeekerJonPreferencesDto prefDto = new JobSeekerJonPreferencesDto();
	            prefDto.setDesiredJobTitle(prefs.getDesiredJobTitle());
	            prefDto.setExpectedSalary(prefs.getExpectedSalary());
	            prefDto.setJobTypes(prefs.getJobTypes());
	            prefDto.setPreferredLocation(prefs.getPreferredLocation());
	            dto.setJobPreferences(prefDto);
	        }

	        return dto;
	    }
	//
	public List<JobSeekerProfileDto> getRecentJobSeekerSummaries() {
		 LocalDateTime startDate = LocalDateTime.now().minusDays(30);
		 List<JobSeeker> jobSeekers = repo.findJobSeekersRegisteredInLast30Days(startDate);

		 List<JobSeekerProfileDto> result = new ArrayList<>();

		 for (JobSeeker js : jobSeekers) {
		 JobSeekerProfileDto dto = new JobSeekerProfileDto();
		 dto.setFullName(js.getFullName());
		 dto.setCreatedAt(js.getCreatedAt());
		 dto.setMobileNumber(js.getMobileNumber());

		 JobSeekerJonPreferencesDto preferencesDto = new JobSeekerJonPreferencesDto();
		 if (js.getJobPrefeences() != null) {
		 preferencesDto.setDesiredJobTitle(js.getJobPrefeences().getDesiredJobTitle());

		 }
		 dto.setJobPreferences(preferencesDto);
		
		 result.add(dto);
		 } return result;
		}

	public long countJobSeekersFromLast30Days() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(30);
		return repo.countJobSeekersRegisteredInLast30Days(startDate);
	}

}
