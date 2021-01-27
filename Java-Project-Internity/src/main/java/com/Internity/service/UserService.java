package com.Internity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internity.model.User;
import com.Internity.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User registerUser(User user) {
		return userRepository.save(user);
		
	}
	
	public User findUserByMobile(long mobile) {
		return userRepository.findById(mobile).get();
		
	}
	
	public User loginUser(long mobile,String password) {
		return userRepository.findByMobileAndPassword(mobile, password);
	}
	
	public User findByMobile(long mobile) {
		return userRepository.findById(mobile).get();
	}
	
	public User resetPassword(User user,long mobile) {
		return null;
	}
}
