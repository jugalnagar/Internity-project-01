package com.Internity.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OTP {
	
	@Id
	private long mobile;
	private String data;
	private Date time;
	private int otp;
	
	
	public OTP() {
		super();
	}
	public OTP(long mobile, String data, Date time, int otp) {
		super();
		this.mobile = mobile;
		this.data = data;
		this.time = time;
		this.otp = otp;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	

}
