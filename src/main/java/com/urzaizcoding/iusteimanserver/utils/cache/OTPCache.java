package com.urzaizcoding.iusteimanserver.utils.cache;

public class OTPCache extends GenericTimeOutCache<Long, String> {

	//time out in minits
	public OTPCache(long timeOut) {
		super(timeOut*60*1000);
	}

	
	
}
