package com.Internity.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public int sendEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		int otp = generateOTP();
		message.setTo(email);
		message.setSubject("OTP configuration");
		message.setText("Your One-Time Password(OTP) for reset/forget you password is "+otp+". Please don't share it with anyone.");
		message.setReplyTo("noreply@notification.com");
		message.setSentDate(new Date());
		message.setFrom("noreply@notification.com");
		
		javaMailSender.send(message);
		
		return otp;
	}
	
	
	public static int generateOTP()  
    {  
        int otp   = (int) (Math.random()*9000)+1000;  
        return otp;
    } 

}

