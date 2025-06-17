package com.example.dto;





public class CompanyProfileDTO {
    private Integer id;
    
    
    private String companyName;
    
    
    private String website;
    
    
    private String about;
    
    private Integer foundingYear;
    private String companySize;
	public CompanyProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CompanyProfileDTO(Integer id, String companyName, String website, String about, Integer foundingYear,
			String companySize) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.website = website;
		this.about = about;
		this.foundingYear = foundingYear;
		this.companySize = companySize;
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
}