package com.example.dto;

public class JobSeekerEducationDto {

	private String degree;
	private String fieldOfStudy;
	private String institution;
	private Long passingYear;
	public JobSeekerEducationDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobSeekerEducationDto(String degree, String fieldOfStudy, String institution, Long passingYear) {
		super();
		this.degree = degree;
		this.fieldOfStudy = fieldOfStudy;
		this.institution = institution;
		this.passingYear = passingYear;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public Long getPassingYear() {
		return passingYear;
	}
	public void setPassingYear(Long passingYear) {
		this.passingYear = passingYear;
	}
	
	
	
}
