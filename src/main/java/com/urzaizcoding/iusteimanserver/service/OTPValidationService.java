package com.urzaizcoding.iusteimanserver.service;

public interface OTPValidationService {
	String generateOTP(int lenght, Long ownerId);
	boolean authenticateOTP(String otp, Long ownerId);
}
