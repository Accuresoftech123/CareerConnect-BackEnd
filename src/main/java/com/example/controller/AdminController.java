package com.example.controller;


import com.example.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for admin operations such as user and recruiter reporting and counting.
 */
@RestController
@RequestMapping("/admin")  // Group all admin-related endpoints under /admin
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
    public Map<String, Object> getJobSeekerReport() {
        return adminService.getJobSeekersReportWithCount();
    }

    /**
     * Returns a detailed report of all recruiters along with the total count.
     */
    @GetMapping("/recruiters/report")
    public Map<String, Object> getRecruiterReport() {
        return adminService.getRecruitersReportWithCount();
    }
}
