package com.Internity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Internity.model.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
	
	public OTP findByMobile(long mobile);
}
