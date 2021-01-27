package com.Internity.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Internity.model.User;
import com.Internity.service.MailService;
import com.Internity.service.UserService;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/signup")
	public ResponseEntity singUp(@Valid @RequestBody User user) {
		
		User userStored = null;
		
		try {
			userStored = userService.findUserByMobile(user.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(userStored!=null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User already registerd");
			}
			
			userStored = userService.registerUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Enter right credential");
		}
		
		return ResponseEntity.ok(userStored);
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestParam("mobile") @Size(min=10,max=10,message = "Mobile No should contain 10 digits") long mobile,@RequestParam("password") String password) {
		
		User fetchUser = userService.loginUser(mobile,password);
		
		if(fetchUser==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Check your credential");
		}		
		return ResponseEntity.ok(fetchUser);
	}
	
	
	
	@PutMapping("/reset-password/{mobile}")
	public ResponseEntity resetPassword(@RequestParam("oldPassword") @Size(min=8,message = "Password should contain greater than 8 character") String oldPassword,@RequestParam("newPassword") @Size(min=8,message = "Password should contain greater than 8 character") String newPassword,@PathVariable("mobile") long mobile,HttpSession session) {
		
		User userFetch = userService.loginUser(mobile, oldPassword);
		
		if(userFetch==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Registered");
		}
		int otp = mailService.sendEmail(userFetch.getGmail());
		//userFetch.setPassword(newPassword);
		session.setAttribute("newPassword", newPassword);
		session.setAttribute("otp", otp);
		
		
		return ResponseEntity.ok("Enter OTP");
	}
	
	
	
	
	@PutMapping("/confirm-otp/{mobile}")
	public ResponseEntity confirmOTP(@RequestParam("otp") int otp,@PathVariable("mobile") long mobile,HttpSession session) {
		
		User userFetch = userService.findByMobile(mobile);
		
		if(userFetch==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Registered");
		}
		String newPassword = (String) session.getAttribute("newPassword");
		Object storedOTP = session.getAttribute("otp");
		
		//check user provide reset password request or not
		if(newPassword==null || storedOTP==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not access this");
		}
		
		//match entered otp and which is stored in session
		if(((int)storedOTP)!=otp) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Entered OTP is wrong");
		}
		
		//set new password to user
		userFetch.setPassword(newPassword);
		
		try {
			userFetch = userService.registerUser(userFetch);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Your password not follow constraints");
		}
		
		//remove attributes from session
		session.removeAttribute("otp");
		session.removeAttribute("newPassword");
		System.out.println(new Date(session.getLastAccessedTime()));
		
		return ResponseEntity.ok(userFetch);
	}

}
