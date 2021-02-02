package com.Internity.extraservice;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CustomApiError {
	
	private Date timestamp;
	private HttpStatus status;
	private String messsage;
	private List<String> errors;
	
	
	public CustomApiError() {
		super();
	}
		
	public CustomApiError(String messsage) {
		super();
		this.messsage = messsage;
	}

	public CustomApiError(HttpStatus status, String messsage) {
		super();
		this.status = status;
		this.messsage = messsage;
	}


	public CustomApiError(Date timestamp,HttpStatus status, String messsage, String errors) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.messsage = messsage;
		this.errors = Arrays.asList(errors);
	}

	public CustomApiError(Date timestamp, HttpStatus status, String messsage, List<String> errors) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.messsage = messsage;
		this.errors = errors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
