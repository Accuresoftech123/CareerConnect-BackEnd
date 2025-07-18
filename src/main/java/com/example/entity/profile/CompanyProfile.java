package com.example.entity.profile;


import com.example.entity.Recruiter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(columnDefinition = "LONGTEXT")
    private String img;
    private String companyEmail;
    
	private String hrName;
    private String website;
    private String about;
    private int foundingYear;
    private String companySize;
    private String industryType;
    private String hrContactEmail;
    private String hrContactMobileNumber;


	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    @JsonManagedReference
    private Recruiter recruiter;

    
	public CompanyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyProfile(int id, String hrName, String website, String about, int foundingYear,
			String companySize, Recruiter recruiter,String img, String companyEmail, String hrContactEmail, String hrContactMobileNumber) {
		super();
		this.id = id;
		this.companyEmail=companyEmail;
		this.hrName = hrName;
		this.website = website;
		this.about = about;
		this.foundingYear = foundingYear;
		this.companySize = companySize;
		this.recruiter = recruiter;
		this.img=img;
		this.hrContactEmail=hrContactEmail;
		this.hrContactMobileNumber=hrContactMobileNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getHrName() {
		return hrName;
	}

	public void setHrName(String hrName) {
		this.hrName = hrName;
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
	

    public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}


    public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}



	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	 public String getHrContactEmail() {
			return hrContactEmail;
		}

		public void setHrContactEmail(String hrContactEmail) {
			this.hrContactEmail = hrContactEmail;
		}

		public String getHrContactMobileNumber() {
			return hrContactMobileNumber;
		}

		public void setHrContactMobileNumber(String hrContactMobileNumber) {
			this.hrContactMobileNumber = hrContactMobileNumber;
		}

	
    
    
}