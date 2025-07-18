package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.example.enums.Status;
import com.example.repository.AdminRepository;
import com.example.repository.JobSeekerRepository;
import com.example.repository.RecruiterRepository;

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

	 
	 

	    // Register admin
	    public String register(AdminRegisterDto request) {
	        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
	            return "Email already registered";
	        }
	        Admin admin = new Admin(request.getEmail(), request.getPassword());
	        adminRepository.save(admin);
	        return "Admin registered successfully";
	    }

	    // Login admin
	    public boolean login(AdminLogin request) {
	        return adminRepository.findByEmail(request.getEmail())
	                .map(admin -> admin.getPassword().equals(request.getPassword()))
	                .orElse(false);
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
								edu.getFieldOfStudy(), edu.getInstitution(), edu.getPassingYear()))
						.collect(Collectors.toList());
				dto.setEducationList(educationDtos);
			}

			// Experience List
			if (jobSeeker.getExperienceList() != null) {
				List<JobSeekerExperienceDto> experienceDtos = jobSeeker.getExperienceList().stream()
						.map(exp -> new JobSeekerExperienceDto(exp.getJobTitle(), exp.getCompanyName(),
								exp.getStartDate(), exp.getEndDate(), exp.getKeyResponsibilities()))
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
				dto.setJobPreferences(new JobSeekerJonPreferencesDto(pref.getDesiredJobTitle(), pref.getJobType(),
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
}
