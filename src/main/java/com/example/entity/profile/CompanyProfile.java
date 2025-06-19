package com.example.entity.profile;


import com.example.entity.Recruiter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String companyName;
    private String website;
    private String about;
    private int foundingYear;
    private String companySize;
    
    public CompanyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyProfile(int id, String companyName, String website, String about, int foundingYear,
			String companySize, Recruiter recruiter) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.website = website;
		this.about = about;
		this.foundingYear = foundingYear;
		this.companySize = companySize;
		this.recruiter = recruiter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getFoundingYear() {
		return foundingYear;
	}

	public void setFoundingYear(int foundingYear) {
		this.foundingYear = foundingYear;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    @JsonManagedReference
    private Recruiter recruiter;

    
    
}