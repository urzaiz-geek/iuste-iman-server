package com.urzaizcoding.iusteimanserver.service;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.utils.RandomStringGenerator;
import com.urzaizcoding.iusteimanserver.utils.cache.OTPCache;

@Service
public class OtpValidationServiceImpl implements OTPValidationService {

	private final OTPCache otpCahe;
	
	
	
	public OtpValidationServiceImpl(OTPCache otpcahe) {
		super();
		this.otpCahe = otpcahe;
	}

	@Override
	public String generateOTP(int lenght, Long ownerId) {
		String newOtp = RandomStringGenerator.numbersString(lenght);
		otpCahe.put(ownerId, newOtp);
		
		return newOtp;
	}

	@Override
	public boolean authenticateOTP(String otp, Long ownerId) {
		
		if(otpCahe.get(ownerId).isPresent()) {
			otpCahe.remove(ownerId);
			return true;
		}
		
		return false;
	}

}
