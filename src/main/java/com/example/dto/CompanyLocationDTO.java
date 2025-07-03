package com.example.dto;

public class CompanyLocationDTO {
	private Integer id;

	private String address;

	private String city;

	private String state;

	private String country;

	private String postalCode;
	private boolean isHeadquarters;

	public CompanyLocationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyLocationDTO(Integer id, String address, String city, String state, String country, String postalCode,
			boolean isHeadquarters) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.isHeadquarters = isHeadquarters;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isHeadquarters() {
		return isHeadquarters;
	}

	public void setHeadquarters(boolean isHeadquarters) {
		this.isHeadquarters = isHeadquarters;
	}
}