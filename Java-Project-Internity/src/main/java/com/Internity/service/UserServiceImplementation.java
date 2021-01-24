package com.Internity.service;

import java.util.Optional;

import com.Internity.model.User;
import com.Internity.repository.UserRepository;

public class UserServiceImplementation implements UserService{

	
	private UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {
		
		Optional<User> checkExistUser = userRepository.findById(user.getMobile());
		
		if(checkExistUser.isPresent()) {
			System.out.println("User already registered");
		}
		return userRepository.save(user);
	}

	@Override
	public User loginUser(User user) {
		Optional<User> fetchUser = userRepository.findById(user.getMobile());
		
		if(!fetchUser.isPresent()) {
			System.out.println("user not registered");
		}
		
		return fetchUser.get();
	}

	
}
