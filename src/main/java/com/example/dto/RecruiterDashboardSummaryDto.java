package com.example.dto;

public class RecruiterDashboardSummaryDto {
	
	private long newApplications;
    private long shortlisted;
    private int savedProfiles;
    private int totalViews;
    
    
	public RecruiterDashboardSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecruiterDashboardSummaryDto(long newApplications, int shortlisted, int savedProfiles, int totalViews) {
		super();
		this.newApplications = newApplications;
		this.shortlisted = shortlisted;
		this.savedProfiles = savedProfiles;
		this.totalViews = totalViews;
	}
	public long getNewApplications() {
		return newApplications;
	}
	public void setNewApplications(long newApplications) {
		this.newApplications = newApplications;
	}
	public long getShortlisted() {
		return shortlisted;
	}
	public void setShortlisted(long shortlisted) {
		this.shortlisted = shortlisted;
	}
	public int getSavedProfiles() {
		return savedProfiles;
	}
	public void setSavedProfiles(int savedProfiles) {
		this.savedProfiles = savedProfiles;
	}
	public int getTotalViews() {
		return totalViews;
	}
	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

}
