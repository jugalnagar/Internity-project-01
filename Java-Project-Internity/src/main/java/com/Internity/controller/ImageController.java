package com.Internity.controller;

import java.util.NoSuchElementException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Internity.component.FileUploadHelper;
import com.Internity.model.User;
import com.Internity.service.UserService;

import io.swagger.annotations.ApiOperation;

@Validated
@RestController
public class ImageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@ApiOperation(value = "Update profile picture Of Existing User")
	@PutMapping("/update-profile/{mobile}")
	public ResponseEntity<Object> updateProfile(@RequestParam("profile") MultipartFile multipartfile,@PathVariable("mobile") @Min(value=1000000000,message="Mobile No must contain 10 digits") @Max(value=9999999999L,message="Mobile No must contain 10 digits") long mobile){
		
		
		if(multipartfile.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please select valid Image");
		}
		
		if(!multipartfile.getContentType().equals("image/jpeg")) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only JPEG file are allowed");
		}
		
		User userFetch = null;
		userFetch = userService.findByMobile(mobile);
		if(userFetch == null) {
			throw new NoSuchElementException(null);
		}
		
		try {
			String profile = fileUploadHelper.uploadFile(multipartfile);
			if(profile == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!!!!!!!");
			}
			userFetch.setProfile(profile);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please select valid path of profile");
		}
		
		try {
			userFetch=userService.registerUser(userFetch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(userFetch);
	}

	@ApiOperation(value = "Get profile picture of existing user")
	@GetMapping("/profile/{mobile}")
	public ResponseEntity<Object> getProfile(@PathVariable("mobile") long mobile) {
		User userFetch = null;
		
		try{
			userFetch = userService.findByMobile(mobile); 
		} catch (Exception e) {
			throw new NoSuchElementException("User Not registered");
		}
		
		return ResponseEntity.ok(userFetch.getProfile());
	}
	
	

}
