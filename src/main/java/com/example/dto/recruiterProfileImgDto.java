package com.example.dto;

public class recruiterProfileImgDto {
	
	String companyName;
	String image;
	public recruiterProfileImgDto(String companyName, String image) {
		super();
		this.companyName = companyName;
		this.image = image;
	}
	public recruiterProfileImgDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
