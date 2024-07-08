package com.kundan.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtlis {
	private PwdUtlis() {
		// TODO Auto-generated constructor stub
	}
	
	public  static String generateRandomPsw() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(6, characters );
		
	}

}
