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

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/user/signup")
	public User singUp(@Valid @RequestBody User user) {
		
		Optional<User> checkExistUser = userRepository.findById(user.getMobile());
		
		if(checkExistUser.isPresent()) {
			System.out.println("User already registered");
		}
		
		return userRepository.save(user);
	}
	
	@GetMapping("/user/{mobile}")
	public User login(@PathVariable("mobile") long mobile) {
		
		Optional<User> fetchUser = userRepository.findById(mobile);
		
		if(!fetchUser.isPresent()) {
			System.out.println("user not registered");
		}
		
		return fetchUser.get();
	}

}
