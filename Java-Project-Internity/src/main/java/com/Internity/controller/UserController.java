package com.Internity.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Internity.model.User;
import com.Internity.repository.UserRepository;
import com.Internity.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/user/signup")
	public User singUp(@Valid @RequestBody User user) {
		
		return userService.registerUser(user);
	}
	
	@GetMapping("/user/login")
	public User login(@RequestBody User user) {
		
		return userService.loginUser(user);
	}

}
