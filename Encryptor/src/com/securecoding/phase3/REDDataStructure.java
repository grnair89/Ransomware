package com.securecoding.phase3;

import javax.crypto.SecretKey;
/**
 * 
 * @author Madhusudan Hanagal
 * @author Ganesh Rajashekharan
 * @author Ajit Paul
 *
 */
public class REDDataStructure {
	
	String orignalFilePath;
	String encryptedFilePath;
	SecretKey secretKey;
	
	public String getOrignalFilePath() {
		return orignalFilePath;
	}
	public void setOrignalFilePath(String orignalFilePath) {
		this.orignalFilePath = orignalFilePath;
	}
	public String getEncryptedFilePath() {
		return encryptedFilePath;
	}
	public void setEncryptedFilePath(String encryptedFilePath) {
		this.encryptedFilePath = encryptedFilePath;
	}
	public SecretKey getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}
	
}
