package com.example.dto;
import java.time.LocalDate;
import java.util.List;

import com.example.enums.JobPostStatus;

public class JobPostDto {

    private int id;

    private String title;
    private String companyName;

    private String description;

    private String location;

    private String employmentType;

    private String minExperience;
    private String maxExperience;

    private LocalDate lastDateToApply;

    private LocalDate postedDate;

    private int recruiterId; // Only ID to avoid recursive object nesting

    private int numberOfOpenings;

    private List<String> skills;

    private double minSalary;

    private double maxSalary;

    private List<String> benefits;

    private JobPostStatus status;

    private boolean prefillRequest;

    private Integer prefillFromJobId;
    private String companyImageUrl;
    
    private String companyIndustry;
    
    private String companyWebsite;
    private String CompanyHr;
    private String CompanyMail;
    
    private String hrName;
    
    private String companyAbout;
    
    private boolean bookmarked;
    

    // Constructors
    public JobPostDto() {
    }

    public JobPostDto(int id, String title, String description, String location, String employmentType,
                      String minExperience, String maxExperience, LocalDate lastDateToApply,
                      LocalDate postedDate, int recruiterId, int numberOfOpenings, List<String> skills,
                      double minSalary, double maxSalary, List<String> benefits,
                      JobPostStatus status, boolean prefillRequest, Integer prefillFromJobId
                      ,boolean bookmarked ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
        this.lastDateToApply = lastDateToApply;
        this.postedDate = postedDate;
        this.recruiterId = recruiterId;
        this.numberOfOpenings = numberOfOpenings;
        this.skills = skills;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.benefits = benefits;
        this.status = status;
        this.prefillRequest = prefillRequest;
        this.prefillFromJobId = prefillFromJobId;
        this.bookmarked = bookmarked;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(String minExperience) {
        this.minExperience = minExperience;
    }

    public String getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(String maxExperience) {
        this.maxExperience = maxExperience;
    }

    public LocalDate getLastDateToApply() {
        return lastDateToApply;
    }

    public void setLastDateToApply(LocalDate lastDateToApply) {
        this.lastDateToApply = lastDateToApply;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public int getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(int numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public JobPostStatus getStatus() {
        return status;
    }

    public void setStatus(JobPostStatus status) {
        this.status = status;
    }

    public boolean isPrefillRequest() {
        return prefillRequest;
    }

    public void setPrefillRequest(boolean prefillRequest) {
        this.prefillRequest = prefillRequest;
    }

    public Integer getPrefillFromJobId() {
        return prefillFromJobId;
    }

    public void setPrefillFromJobId(Integer prefillFromJobId) {
        this.prefillFromJobId = prefillFromJobId;
    }

	public String getCompanyImageUrl() {
		return companyImageUrl;
	}

	public void setCompanyImageUrl(String companyImageUrl) {
		this.companyImageUrl = companyImageUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyIndustry() {
		return companyIndustry;
	}

	public void setCompanyIndustry(String companyIndustry) {
		this.companyIndustry = companyIndustry;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getCompanyHr() {
		return CompanyHr;
	}

	public void setCompanyHr(String companyHr) {
		CompanyHr = companyHr;
	}

	public String getCompanyMail() {
		return CompanyMail;
	}

	public void setCompanyMail(String companyMail) {
		CompanyMail = companyMail;
	}

	public String getHrName() {
		return hrName;
	}

	public void setHrName(String hrName) {
		this.hrName = hrName;
	}

	public String getCompanyAbout() {
		return companyAbout;
	}

	public void setCompanyAbout(String companyAbout) {
		this.companyAbout = companyAbout;
	}
	

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}
	
	
	
	
	
    
}
