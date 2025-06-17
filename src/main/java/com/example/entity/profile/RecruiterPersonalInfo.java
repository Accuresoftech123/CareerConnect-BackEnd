package com.example.entity.profile;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.entity.Recruiter;

@Entity
public class RecruiterPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String jobTitle;
    private String department;
    private LocalDate hireDate;
    
    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

	public RecruiterPersonalInfo(int id, String jobTitle, String department, LocalDate hireDate, Recruiter recruiter) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.department = department;
		this.hireDate = hireDate;
		this.recruiter = recruiter;
	}

	public RecruiterPersonalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
    
    
}