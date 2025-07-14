package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ApplicantExperience {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String company;
	    private String jobTitle;
	    private LocalDate startDate;
		private LocalDate endDate;
		
		
		@Column(length = 1000)
		private String keyResponsibilities;
		
	    @ManyToOne
	    @JoinColumn(name = "applicant_id")
	    private Applicant applicant;
	    
	    

		public ApplicantExperience(int id, String company, String jobTitle, LocalDate startDate, LocalDate endDate,
				String keyResponsibilities, Applicant applicant) {
			super();
			this.id = id;
			this.company = company;
			this.jobTitle = jobTitle;
			this.startDate = startDate;
			this.endDate = endDate;
			this.keyResponsibilities = keyResponsibilities;
			this.applicant = applicant;
		}

		
		public ApplicantExperience() {
			super();
			// TODO Auto-generated constructor stub
		}


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getJobTitle() {
			return jobTitle;
		}

		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public String getKeyResponsibilities() {
			return keyResponsibilities;
		}

		public void setKeyResponsibilities(String keyResponsibilities) {
			this.keyResponsibilities = keyResponsibilities;
		}

		public Applicant getApplicant() {
			return applicant;
		}

		public void setApplicant(Applicant applicant) {
			this.applicant = applicant;
		}

}
