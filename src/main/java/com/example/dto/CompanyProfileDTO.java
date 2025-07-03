package com.example.dto;

public class CompanyProfileDTO {
	private Integer id;
	private String img;

	private String companyName;
	private String companyEmail;
	private String website;
	private String about;
	private Integer foundingYear;
	private String companySize;
	private String companyAddress;
	private Integer numberOfEmployees;
	private String industryType;
	private String hrContactEmail;
	private String hrContactMobileNumber;

	public CompanyProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyProfileDTO(Integer id, String companyName, String website, String about, Integer foundingYear,
			String companySize, String companyAddress, Integer numberOfEmployees, String industryType, String img, String hrContactEmail, String  hrContactMobileNumber) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.website = website;
		this.about = about;
		this.foundingYear = foundingYear;
		this.companySize = companySize;
		this.companyAddress = companyAddress;
		this.numberOfEmployees = numberOfEmployees;
		this.industryType = industryType;
		this.hrContactEmail=hrContactEmail;
		this.hrContactMobileNumber=hrContactMobileNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getFoundingYear() {
		return foundingYear;
	}

	public void setFoundingYear(Integer foundingYear) {
		this.foundingYear = foundingYear;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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