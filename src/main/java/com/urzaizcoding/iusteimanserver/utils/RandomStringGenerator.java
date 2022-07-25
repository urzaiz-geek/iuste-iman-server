package com.urzaizcoding.iusteimanserver.utils;

import java.util.Random;

public class RandomStringGenerator {
	
	private static final String ENTRIES = "0123456789";
	
	public static String numbersString(int length) {
		StringBuilder builder = new StringBuilder();
		
		Random random = new Random();
		
		for(int i = 0; i<length; i++) {
			
			builder.append(ENTRIES.charAt(random.nextInt(ENTRIES.length())));
		}
		
		return builder.toString();
	}
}
