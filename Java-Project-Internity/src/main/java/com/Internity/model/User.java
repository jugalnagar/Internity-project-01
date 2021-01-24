package com.Internity.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
public class User {
	
	@Id
	private long mobile;
	@NotBlank(message = "Name must enter")
	@Size(min = 2,max = 30,message = "Number of character must >2 and <30 ")
	private String name;
	@NotBlank(message="Gmail must required")
	private String gmail;
	@Size(min = 8,message = "Length of Password must greater than 8")
	@NotBlank(message = "Password must required")
	private String password;
	
	
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [mobile=" + mobile + ", name=" + name + ", gmail=" + gmail + ", password=" + password + "]";
	}
	
}
