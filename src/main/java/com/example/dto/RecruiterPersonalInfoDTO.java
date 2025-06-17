package com.example.dto;

import java.time.LocalDate;




public class RecruiterPersonalInfoDTO {
    private Integer id;
    
   
    private String jobTitle;
    
    
    private String department;
    
    private LocalDate hireDate;

	public RecruiterPersonalInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecruiterPersonalInfoDTO(Integer id, String jobTitle, String department, LocalDate hireDate) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.department = department;
		this.hireDate = hireDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}