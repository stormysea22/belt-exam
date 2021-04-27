package com.theismann.beltExam.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	  
	  	@NotEmpty(message="First name required")
	  	private String firstName;
	  	
	  	@NotEmpty(message="Last name required")
	  	private String lastName;
	  
	  	@NotEmpty(message="Email required")
	  	@Email(message="Email must be valid")
	    private String email;
	    
	  	@NotEmpty(message="Password required")
	  	@Size(min=5, message="Password Must be greater that 5 characters")
	    private String password;
	  	
	    @Transient
	    @NotEmpty
	    private String passwordConfirmation;
	    @Column(updatable=false)
	    private Date createdAt;
	    private Date updatedAt;
	    
	    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
	    private List<Idea> ideas;
	    
	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "ideas_likes", 
	        joinColumns = @JoinColumn(name = "user_id"), 
	        inverseJoinColumns = @JoinColumn(name = "idea_id"))
	    private List<Idea> likes;
	    
	    public User() {
	    }
	    
	    
	      
	    
	    //getters and setters
	        
	    
	    public User(String firstName, String lastName, String email, String password, String passwordConfirmation, List<Idea> ideas, List<Idea> likes) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			this.passwordConfirmation = passwordConfirmation;
			this.ideas = ideas;
			this.likes = likes;
		}


		public Long getId() {
			return id;
		}




		public void setId(Long id) {
			this.id = id;
		}




		public String getFirstName() {
			return firstName;
		}




		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}




		public String getLastName() {
			return lastName;
		}




		public void setLastName(String lastName) {
			this.lastName = lastName;
		}




		public String getEmail() {
			return email;
		}




		public void setEmail(String email) {
			this.email = email;
		}




		public String getPassword() {
			return password;
		}




		public void setPassword(String password) {
			this.password = password;
		}




		public String getPasswordConfirmation() {
			return passwordConfirmation;
		}




		public void setPasswordConfirmation(String passwordConfirmation) {
			this.passwordConfirmation = passwordConfirmation;
		}




		public Date getCreatedAt() {
			return createdAt;
		}




		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}




		public Date getUpdatedAt() {
			return updatedAt;
		}




		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}




		public List<Idea> getIdeas() {
			return ideas;
		}




		public void setIdeas(List<Idea> ideas) {
			this.ideas = ideas;
		}




		public List<Idea> getLikes() {
			return likes;
		}




		public void setLikes(List<Idea> likes) {
			this.likes = likes;
		}




		@PrePersist
		protected void onCreate(){
			this.createdAt = new Date();
		}
		
		@PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
	}

