package com.design_shinbi.searchinghome.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
	public static String generateSalt() {
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String hashPasswordWithSaltPepper(String password, String salt) 
			throws NoSuchAlgorithmException{
		String pepper = "SuperSecrretPepper12345!?";
		String combined = password + salt + pepper;
		
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] hashedBytes = sha256.digest(combined.getBytes());
		return String.format("%040x", new BigInteger(1, hashedBytes));
	}
}
