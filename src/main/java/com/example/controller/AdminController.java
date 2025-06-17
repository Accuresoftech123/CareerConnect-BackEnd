package com.example.controller;

import com.example.enums.Status;
import com.example.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for admin operations such as user and recruiter reporting and
 * counting.
 */
@RestController
@RequestMapping("/admin") // Group all admin-related endpoints under /admin
public class AdminController {

	@Autowired
	private AdminService adminService;

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

}
