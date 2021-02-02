package com.Internity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Internity.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByMobileAndPassword(long mobile,String password);
	
	public User findByMobile(long mobile);
}
