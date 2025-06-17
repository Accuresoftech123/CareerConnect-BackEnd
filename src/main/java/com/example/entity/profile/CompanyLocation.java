package com.example.entity.profile;

import com.example.entity.Recruiter;

import jakarta.persistence.*;

@Entity
public class CompanyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private boolean isHeadquarters;
    
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

	public CompanyLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyLocation(int id, String address, String city, String state, String country, String postalCode,
			boolean isHeadquarters, Recruiter recruiter) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.isHeadquarters = isHeadquarters;
		this.recruiter = recruiter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
    
    
}