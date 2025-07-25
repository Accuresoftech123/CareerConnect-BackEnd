package com.example.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.dto.AdminLogin;
import com.example.dto.AdminRegisterDto;
import com.example.dto.CompanyProfileDTO;
import com.example.dto.JobSeekerEducationDto;
import com.example.dto.JobSeekerExperienceDto;
import com.example.dto.JobSeekerJonPreferencesDto;
import com.example.dto.JobSeekerPersonalInfoDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerSocialProfileDto;
import com.example.dto.RecruiterProfileDto;
import com.example.entity.Admin;
import com.example.entity.JobSeeker;
import com.example.entity.Recruiter;
import com.example.entity.profile.CompanyProfile;
import com.example.entity.profile.Education;
import com.example.entity.profile.JobPreferences;
import com.example.entity.profile.JobSeekerPersonalInfo;
import com.example.entity.profile.SocialProfile;
import com.example.enums.Role;
import com.example.enums.Status;
import com.example.exception.UserNotFoundException;
import com.example.repository.AdminRepository;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;
import com.example.security.AdminUserDetailsService;
import com.example.security.CustomUserDetails;
import com.example.security.JwtUtil;

/**
 * Service class to handle admin-level operations such as retrieving counts and
 * reports for job seekers and recruiters.
 */
@Service
public class AdminService {

	@Autowired
	private JobSeekerRepository jobSeekerRepository;

	@Autowired
	private RecruiterRepository recruiterRepository;

	
	 @Autowired
	 private AdminRepository adminRepository;
	 
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	 @Autowired
	 private AdminUserDetailsService adminUserDetailsService;

	 
	 

	// Service method to register admin
	 public String register(AdminRegisterDto request) {
	     // Check if email already exists
	     if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
	         return "Email already registered";
	     }

	     // Encode password before saving
	     String encodedPassword = passwordEncoder.encode(request.getPassword());

	     // Create Admin entity (make sure constructor exists or use setters)
	     Admin admin = new Admin();
	     admin.setEmail(request.getEmail());
	     admin.setPassword(encodedPassword);
	     admin.setRole(Role.ROLE_ADMIN);

	     // Save to repository
	     adminRepository.save(admin);

	     return "Admin registered successfully";
	 }

	 

	

	 @Autowired
	 private JwtUtil jwtUtil;

	 public ResponseEntity<?> login(AdminLogin request) {
	     // 1. Validate input
	     if (request.getEmail() == null || request.getPassword() == null ||
	         request.getEmail().isBlank() || request.getPassword().isBlank()) {
	         return ResponseEntity.badRequest().body("Email and password must be provided");
	     }

	     // 2. Find Admin by email
	     Admin admin = adminRepository.findByEmail(request.getEmail())
	             .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email"));

	     // 3. Verify password using encoded hash
	     if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	     }

	     
	     // token generate
	        // ✅ Load user details for JWT

			UserDetails userDetails = adminUserDetailsService.loadUserByUsername(request.getEmail());
			CustomUserDetails customUser = (CustomUserDetails) userDetails;

			// Generate JWT token with role
			String token = jwtUtil.generateToken(customUser.getUsername(), customUser.getRole().name());
	     

	     // 5. Build response
	     Map<String, Object> response = new HashMap<>();
	     response.put("token", token);
	     response.put("role", admin.getRole().name());
	     response.put("id", admin.getId());

	     return ResponseEntity.ok(response);
	 }

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
	 * @return a map containing: - "jobSeekerCount": total number of job seekers -
	 *         "jobSeekerList": list of job seekers with their profile details
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getJobSeekersReportWithCount() {
		List<JobSeeker> jobSeekers = jobSeekerRepository.findAll();

		List<JobSeekerProfileDto> profiles = jobSeekers.stream().map(jobSeeker -> {
			JobSeekerProfileDto dto = new JobSeekerProfileDto();

			dto.setFullName(jobSeeker.getFullName());
			dto.setId(jobSeeker.getId());
			dto.setMobileNumber(jobSeeker.getMobileNumber());
			dto.setEmail(jobSeeker.getEmail());

			// Personal Info
			if (jobSeeker.getPersonalInfo() != null) {
				JobSeekerPersonalInfo pi = jobSeeker.getPersonalInfo();
				dto.setPersonalInfo(new JobSeekerPersonalInfoDto(pi.getProfileImageUrl(), pi.getCity(), pi.getState(),
						pi.getCountry(), pi.getResumeUrl(), pi.getIntroVideoUrl()));
			}

			// Education List
			if (jobSeeker.getEducationList() != null) {
				List<JobSeekerEducationDto> educationDtos = jobSeeker
						.getEducationList().stream().map(edu -> new JobSeekerEducationDto(edu.getDegree(),
								edu.getFieldOfStudy(), edu.getInstitution(), edu.getPassingYear(),edu.getId()))
						.collect(Collectors.toList());
				dto.setEducationList(educationDtos);
			}

			// Experience List
			if (jobSeeker.getExperienceList() != null) {
				List<JobSeekerExperienceDto> experienceDtos = jobSeeker.getExperienceList().stream()
						.map(exp -> new JobSeekerExperienceDto(exp.getJobTitle(), exp.getCompanyName(),
								exp.getStartDate(), exp.getEndDate(), exp.getKeyResponsibilities(),exp.getId()))
						.collect(Collectors.toList());
				dto.setExperienceList(experienceDtos);
			}

			// Skills
			dto.setSkills(jobSeeker.getSkills());

			// Social Profile
			if (jobSeeker.getSocialProfile() != null) {
				SocialProfile social = jobSeeker.getSocialProfile();
				dto.setScoicalProfile(new JobSeekerSocialProfileDto(social.getLinkedinUrl(), social.getGithubUrl(),
						social.getPortfolioWebsite()));
			}

			// Job Preferences
			if (jobSeeker.getJobPrefeences() != null) {
				JobPreferences pref = jobSeeker.getJobPrefeences();
				dto.setJobPreferences(new JobSeekerJonPreferencesDto(pref.getDesiredJobTitle(), pref.getJobTypes(),
						pref.getExpectedSalary(), pref.getPreferredLocation()));
			}

			return dto; // 👉 you missed this line before!
		}).collect(Collectors.toList());

		// Prepare final response map
		Map<String, Object> response = new HashMap<>();
		response.put("count", profiles.size());
		response.put("profiles", profiles);

		return response;
	}

	/**
	 * Generates a detailed report of all recruiters including their count.
	 *
	 * @return a map containing: - "totalRecruiters": total number of recruiters -
	 *         "recruiters": list of recruiters with their profile details
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getRecruitersReportWithCount() {
		List<RecruiterProfileDto> recruiterProfiles = recruiterRepository.findAll().stream().map(r -> {
			RecruiterProfileDto dto = new RecruiterProfileDto();
			//dto.setFName(r.getFullName());
			dto.setMobileNumber(r.getMobileNumber());
			dto.setRecruiterEmail(r.getEmail());

			// Map company profile to DTO
			if (r.getCompanyProfile() != null) {
				CompanyProfile cp = r.getCompanyProfile();

				CompanyProfileDTO companyDTO = new CompanyProfileDTO();
			//	companyDTO.setCompanyName(cp.getCompanyName());
				companyDTO.setWebsite(cp.getWebsite());
				companyDTO.setAbout(cp.getAbout());
				companyDTO.setCompanySize(cp.getCompanySize());
				companyDTO.setIndustryType(cp.getIndustryType());
				companyDTO.setFoundingYear(cp.getFoundingYear());

			}

			// Optionally map location or other nested info here

			return dto;
		}).collect(Collectors.toList());

		// Fetch status counts
		long pendingCount = recruiterRepository.countByStatus(Status.PENDING);
		long approvedCount = recruiterRepository.countByStatus(Status.APPROVED);
		long rejectedCount = recruiterRepository.countByStatus(Status.REJECTED);

		// Prepare the response map
		Map<String, Object> response = new HashMap<>();
		response.put("totalPending", pendingCount);
		response.put("totalApproved", approvedCount);
		response.put("totalRejected", rejectedCount);
		response.put("totalRecruiters", recruiterProfiles.size());
		response.put("recruiters", recruiterProfiles);

		return response;
	}

	// Helper method to parse company size string into approximate employee count
	private Integer parseEmployeeCount(String companySize) {
		if (companySize == null || companySize.isEmpty()) {
			return null;
		}

		try {
			// Handle ranges like "100-200"
			if (companySize.contains("-")) {
				String[] parts = companySize.split("-");
				return Integer.parseInt(parts[1].trim());
			}
			// Handle "500+" cases
			else if (companySize.contains("+")) {
				return Integer.parseInt(companySize.replace("+", "").trim());
			}
			// Plain number
			else {
				return Integer.parseInt(companySize.trim());
			}
		} catch (NumberFormatException e) {
			return null; // Return null if parsing fails
		}
	}

	// Update recruter status ex.panding, approve, regected
	public String updateRecruterStatus(int id, Status status) {
		Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(id);

		if (optionalRecruiter.isPresent()) {
			Recruiter recruiter = optionalRecruiter.get();
			recruiter.setStatus(status);
			recruiterRepository.save(recruiter);
			return "Recruter status updated Successfully " + status;
		} else {
			return "Recruter not found with id: " + id;
		}
	}

	// This deletes JobSeeker and cascades delete to related entities
	public void deleteJobSeekerById(int id) {
		jobSeekerRepository.deleteById(id);
	}

	public void deleteRecruiterById(int id) {
		recruiterRepository.deleteById(id);
	}
	//Forget password
	
	public void resetAdminPassword( String email, String newPassword) {
	    Admin admin = adminRepository.findByEmail(email)
	        .orElseThrow(() -> new UserNotFoundException("Admin not registered. Please register first."));

	    admin.setPassword(passwordEncoder.encode(newPassword));
	    admin.setOtp(null); // Invalidate OTP
	    admin.setOtpGeneratedTime(null);
	    adminRepository.save(admin);
	}
}
