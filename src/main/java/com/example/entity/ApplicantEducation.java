package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ApplicantEducation {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	private String degree;
    private String institute;
    private Long passingYear;
    private String fieldOfStudy;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

	public ApplicantEducation(int id, String degree, String institute, Long passingYear, String fieldOfStudy,
			Applicant applicant) {
		super();
		this.id = id;
		this.degree = degree;
		this.institute = institute;
		this.passingYear = passingYear;
		this.fieldOfStudy = fieldOfStudy;
		this.applicant = applicant;
	}

	public ApplicantEducation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public long getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(long passingYear) {
		this.passingYear = passingYear;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
	

}
