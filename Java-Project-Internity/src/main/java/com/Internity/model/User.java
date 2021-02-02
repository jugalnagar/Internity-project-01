package com.Internity.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
public class User {
	
	
	@Id
	@Max(value = 9999999999L ,message = "Enter 10 Digit mobile number")
	@Min(value = 1000000000,message = "Enter 10 Digit mobile number")
	private long mobile;
	
	
	@NotBlank(message = "First Name must enter")
	@Size(min = 2,max = 30,message = "Number of character must >2 and <30 ")
	private String firstName;
	
	
	@NotBlank(message = "Last Name must enter")
	@Size(min = 2,max = 30,message = "Number of character must >2 and <30 ")
	private String lastName;
	
	@Email
	@NotBlank(message="Gmail must required")
	private String email;
	
	
	@Size(min = 8,message = "Length of Password must greater than 8")
	@NotBlank(message = "Password must required")
	private String password;
	
	@Past
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date birthday;
	

	private String profile;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;
	
	
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String gmail) {
		this.email = gmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
