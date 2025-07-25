package com.example.controller;

import com.example.dto.AdminLogin;
import com.example.dto.AdminRegisterDto;
import com.example.dto.JobSeekerProfileDto;
import com.example.dto.RecruiterDTO;
import com.example.entity.Admin;
import com.example.enums.Status;
import com.example.exception.UserNotFoundException;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;
import com.example.service.JobPostService;
import com.example.service.JobSeekerService;
import com.example.service.RecruiterService;
import com.example.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for admin operations such as user and recruiter reporting and
 * counting.
 */
@RestController
@RequestMapping("/api/admin") // Group all admin-related endpoints under /admin
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JobSeekerService jobSeekerService;
	
	@Autowired
	private RecruiterService recruiterService;
	
	@Autowired
    private JobPostService jobPostService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	 @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody AdminRegisterDto request) {
	        String message = adminService.register(request);
	        if (message.equals("Admin registered successfully")) {
	            return ResponseEntity.ok(message);
	        }
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	    }

	// ✅ Admin Login Endpoint
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AdminLogin request) {
	        // Delegate to service method that returns ResponseEntity
	        return adminService.login(request);
	    }
	/**
	 * Returns the total number of job seekers.
	 */
	@GetMapping("/jobseekers/count")
	public long getJobSeekerCount() {
		return adminService.countJobSeekers();
	}

	/**
	 * Returns the total number of recruiters.
	 */
	@GetMapping("/recruiters/count")
	public long getRecruiterCount() {
		return adminService.countRecruiters();
	}

	/**
	 * Returns a detailed report of all job seekers along with the total count.
	 */
	@GetMapping("/jobseekers/report")
	public ResponseEntity<Map<String, Object>> getJobSeekersReport() {
		Map<String, Object> report = adminService.getJobSeekersReportWithCount();
		return ResponseEntity.ok(report);
	}

	// update status of the recruiter
	@PutMapping("/update/{id}/status")
	public ResponseEntity<String> updateRecruterStatus(@PathVariable int id, @PathVariable Status status) {
		String result = adminService.updateRecruterStatus(id, status);
		return ResponseEntity.ok(result);
	}

	/**
	 * Returns a detailed report of all recruiters along with the total count.
	 */
	@GetMapping("/recruiters/report")
	public Map<String, Object> getRecruiterReport() {
		return adminService.getRecruitersReportWithCount();
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
		try {
			adminService.deleteJobSeekerById(id);
			return ResponseEntity.ok("JobSeeker and related data deleted successfully.");
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobSeeker with ID " + id + " not found.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting.");
		}
	}

	@DeleteMapping("/deleteRecruter/{id}")
	public ResponseEntity<String> deleteRecruiterById(@PathVariable int id) {
		try {
			adminService.deleteRecruiterById(id);
			return ResponseEntity.ok("Record deleted successfully.");
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recruter with id " + id + " not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting.");
		}

	}
	
	  // 1. Send OTP to email
	 @PostMapping("/send-otp/{email}")
	    public ResponseEntity<?> sendOtpToAdmin(@PathVariable String email) {
	        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
	        if (optionalAdmin.isEmpty()) {
	            throw new UserNotFoundException("Admin not registered. Please register first.");
	        }

	        emailService.generateAndSendOtpToAdmin(optionalAdmin.get());
	        return ResponseEntity.ok(Map.of("message", "OTP has been sent to your email."));
	    }
    // 2. Verify OTP
    @PostMapping("/verify-otp/{email}/{otp}")
    public ResponseEntity<?> verifyAdminOtp(@PathVariable String email, @PathVariable String otp) {
    	emailService.verifyAdminOtp(email, otp);
        return ResponseEntity.ok(Map.of("message", "OTP verified successfully."));
    }

    // 3. Set Password
    @PutMapping("/Set-password/{email}/{newPassword}")
    public ResponseEntity<?> SetAdminPassword(@PathVariable String email, @PathVariable String newPassword) {
        adminService.resetAdminPassword(email, newPassword);
        return ResponseEntity.ok(Map.of("message", "Password reset successfully."));
    }
	
	
	
	 //  GET list of job seekers registered in the last 30 days
    @GetMapping("/jobseeker/recent")
    public ResponseEntity<List<JobSeekerProfileDto>> getRecentJobSeekerSummaries() {
        List<JobSeekerProfileDto> recentJobSeekers = jobSeekerService.getRecentJobSeekerSummaries();
        return new ResponseEntity<>(recentJobSeekers, HttpStatus.OK);
    }

    // GET count of job seekers registered in the last 30 days
    @GetMapping("/jobseeker/recent/count")
    public ResponseEntity<Long> getRecentJobSeekerCount() {
        return ResponseEntity.ok(jobSeekerService.countJobSeekersFromLast30Days());
    }
    
 // GET count of recruiter registered in the last 30 days
    @GetMapping("/recruiter/recent/count")
    public ResponseEntity<Long> countRecentRecruiters() {
        return ResponseEntity.ok(recruiterService.countRecruitersFromLast30Days());
    }
 
    //  GET list of recruiter registered in the last 30 days
    @GetMapping("/recruiter/recent")
    public ResponseEntity<List<RecruiterDTO>> getRecentRecruiterSummaries() {
        List<RecruiterDTO> recentRecruiters = recruiterService.getRecentRecruiterSummaries();
        return new ResponseEntity<>(recentRecruiters, HttpStatus.OK);
    }
    
    // get latest count of job post by recruiter
    @GetMapping("/jobposts/recent/count")
    public ResponseEntity<Long> countRecentJobPosts() {
        return ResponseEntity.ok(jobPostService.countRecentJobPosts());
    }

}
