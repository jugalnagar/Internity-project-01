package com.Internity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internity.model.OTP;
import com.Internity.repository.OTPRepository;

@Service
public class OTPService {
	
	@Autowired
	private OTPRepository otpRepository;
	
	public OTP storeOTP(OTP otp) {
		return otpRepository.save(otp);
	}
	
	public OTP getOTP(long mobile) {
		return otpRepository.findByMobile(mobile);
	}

}
