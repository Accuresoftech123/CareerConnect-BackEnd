package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.JobSeekerEducationDto;
import com.example.dto.JobSeekerExperienceDto;
import com.example.dto.JobSeekerJonPreferencesDto;
import com.example.dto.JobSeekerPersonalInfoDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.JobSeekerSocialProfileDto;
import com.example.dto.RecruiterProfileDto;
import com.example.entity.JobSeeker;
import com.example.entity.profile.Education;
import com.example.entity.profile.JobPreferences;
import com.example.entity.profile.JobSeekerPersonalInfo;
import com.example.entity.profile.SocialProfile;
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
		// Convert each Recruiter entity to a RecruiterProfileDto
		List<RecruiterProfileDto> recruiterProfiles = recruiterRepository.findAll().stream()
				.map(r -> new RecruiterProfileDto(r.getCompanyName(), r.getCompanyAddress(), r.getCompanyDescription(),
						r.getCompanyWebsiteUrl(), r.getNumberOfEmployees(), r.getIndustryType(), r.getFirstName(),
						r.getLastName(), r.getEmail(), r.getPhoneNumber(), r.getCity()))
				.collect(Collectors.toList());

		// Prepare the response map
		Map<String, Object> response = new HashMap<>();
		response.put("totalRecruiters", recruiterProfiles.size());
		response.put("recruiters", recruiterProfiles);

		return response;
	}

	// This deletes JobSeeker and cascades delete to related entities
	public void deleteJobSeekerById(int id) {
		jobSeekerRepository.deleteById(id);
	}

}
