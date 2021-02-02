package com.Internity.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@ApiOperation(value = "Signup New User")
	@PostMapping("/signup")
	public ResponseEntity<Object> singUp(@Valid @RequestBody User user,BindingResult bindingResult) {
		
		User userStored = null;
		
		if(bindingResult.hasErrors()) {
			throw new ConstraintViolationException(null);
		}
		
		try{
			userStored = userService.findUserByMobile(user.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(userStored!=null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registerd");
		}
			
		userStored = userService.registerUser(user);
		
		return ResponseEntity.ok(userStored);
	}
	
	
	@ApiOperation(value = "Login Existing User")
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam("mobile") @Range(min=1000000000,max=9999999999L,message = "Mobile No should contain 10 digits") long mobile,@RequestParam("password") String password) {
		
		User fetchUser = null;
		try{
			fetchUser = userService.findByMobile(mobile);
		} catch (Exception e) {
			throw new NoSuchElementException("User not registered");
		}
		
		fetchUser = userService.loginUser(mobile,password);
		
		if(fetchUser == null) {
			throw new NoSuchElementException("Password is incorrect!!");
		}		
		return ResponseEntity.ok(fetchUser);
	}
	
	
	@ApiOperation(value = "Reset Password Of Existing User")
	@PutMapping("/reset-password/{mobile}")
	public ResponseEntity<Object> resetPassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") @Size(min=8,message = "Password should contain greater than 8 character") String newPassword,@PathVariable("mobile") long mobile,@ApiIgnore HttpSession session){
		
		User userFetch = null;
		
		try{
			userFetch = userService.findByMobile(mobile);
		} catch (Exception e) {
			throw new NoSuchElementException("User not registered");
		}
		
		userFetch = userService.loginUser(mobile,oldPassword);
		
		if(userFetch == null) {
			throw new NoSuchElementException("Old Password is incorrect!!");
		}
		
		int otp = mailService.sendEmail(userFetch.getEmail());
		
		session.setAttribute("newPassword", newPassword);
		session.setAttribute("otp", otp);
		
		
		return ResponseEntity.ok("Enter OTP");
	}
	
	
	
	@ApiOperation(value = "Confirm OTP which sent user's Email")
	@PutMapping("/confirm-otp/{mobile}")
	public ResponseEntity<Object> confirmOTP(@RequestParam("otp") int otp,@PathVariable("mobile") long mobile,@ApiIgnore HttpSession session) {
		
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
	
	
	
	@ApiOperation(value = "Forget Password API Of Existing User")
	@PutMapping("/forget-password/{mobile}")
	public ResponseEntity<Object> forgetPassword(@RequestParam("newPassword") @Size(min=8,message = "Password should contain greater than 8 character") String newPassword,@RequestParam("confirmNewPassword") @Size(min=8,message = "Password should contain greater than 8 character") String confirmNewPassword,@PathVariable("mobile") long mobile,@ApiIgnore HttpSession session) {
		
		
		User userFetch = null;
		
		try {
			userFetch = userService.findByMobile(mobile);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("User not registered");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int otp = mailService.sendEmail(userFetch.getEmail());

		session.setAttribute("newPassword", newPassword);
		session.setAttribute("otp", otp);
		
		
		return ResponseEntity.ok("Enter OTP");
	}

}
