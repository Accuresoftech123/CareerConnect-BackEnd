package com.example.entity.jobposting;

import java.time.LocalDateTime;

import com.example.entity.JobSeeker;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "saved_jobs")
public class SavedJob {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(optional = false)
	    @JsonBackReference
	    @JoinColumn(name = "job_seeker_id")
	    private JobSeeker jobSeeker;

	    @ManyToOne(optional = false)
	    @JsonBackReference
	    @JoinColumn(name = "job_post_id")
	    private JobPost jobPost;

	    private LocalDateTime savedAt;
	    
	    @PrePersist
	    public void prePersist() {
	        this.savedAt = LocalDateTime.now();
	    }

		public SavedJob() {
			super();
			// TODO Auto-generated constructor stub
		}

		public SavedJob(Long id, JobSeeker jobSeeker, JobPost jobPost, LocalDateTime savedAt) {
			super();
			this.id = id;
			this.jobSeeker = jobSeeker;
			this.jobPost = jobPost;
			this.savedAt = savedAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public JobSeeker getJobSeeker() {
			return jobSeeker;
		}

		public void setJobSeeker(JobSeeker jobSeeker) {
			this.jobSeeker = jobSeeker;
		}

		public JobPost getJobPost() {
			return jobPost;
		}

		public void setJobPost(JobPost jobPost) {
			this.jobPost = jobPost;
		}

		public LocalDateTime getSavedAt() {
			return savedAt;
		}

		public void setSavedAt(LocalDateTime savedAt) {
			this.savedAt = savedAt;
		}
	    
	    

}
