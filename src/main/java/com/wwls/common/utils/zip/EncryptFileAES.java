package com.wwls.common.utils.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptFileAES {
	final static String cKey = "1234567890abcDEF";

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 */
		String cKey = "1234567890abcDEF";
		// 加密
		File sourcefile = new File("/Users/xudongdong/Documents/test/aaaa.xhtml");
		// System.out.println("加密前源文件 :" + sourcefile.toString());
		File encryptfile = new File("/Users/xudongdong/Documents/test/bbbb.xhtml");
	     EncryptFileAES.encryptFile(sourcefile, encryptfile, cKey);
		// System.out.println("加密后的文件 :" + encryptfile.toString());

		//File decryptFlie = new File("/Users/Evan/Desktop/10400114_encrypt_.epub");
		//EncryptFileAES.decryptFile(encryptfile, decryptFlie, cKey);
		//System.out.println("解密后的文件 :" + decryptFlie.toString());
	}

    /*****
     * 解密文件
     * *****/
	public static InputStream decryptInputStream(InputStream inputStream) {
		Cipher cipher = initAESCipherSample(cKey, Cipher.DECRYPT_MODE);
		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
		return cipherInputStream;
	}

	
	
	public static Cipher initAESCipherSample(String sKey, int cipherMode) {

		Cipher cipher = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "AES");
			cipher = Cipher.getInstance("AES");
			// 初始化
			cipher.init(cipherMode, skeySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); 
									
		} catch (NoSuchPaddingException e) {
			e.printStackTrace(); 
									
		} catch (InvalidKeyException e) {
			e.printStackTrace(); 
									
		}
		return cipher;
	}

	public static Cipher initAESCipher(String sKey, int cipherMode) {
		// 创建Key gen
		KeyGenerator keyGenerator = null;
		Cipher cipher = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(sKey.getBytes()));
			SecretKey secretKey = keyGenerator.generateKey();

			byte[] codeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");
			cipher = Cipher.getInstance("AES");
			// 初始化
			cipher.init(cipherMode, key);
		} catch (NoSuchAlgorithmException e) {
			error(e.getMessage());  
									 

		} catch (NoSuchPaddingException e) {
			error(e.getMessage());  
									 

		} catch (InvalidKeyException e) {
			error(e.getMessage());  					 
		}
		return cipher;
	}

	static void error(String msg) {
		System.out.println(msg);
	}

	/**
	 * 对文件进行AES加密
	 * 
	 * @param sourceFile
	 * @param fileType
	 * @param sKey
	 * @return
	 */
	public static boolean encryptFile(File sourceFile, File destFile, String sKey) {
		// 新建临时加密文件
		File encrypfile = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			encrypfile = destFile;
			outputStream = new FileOutputStream(encrypfile);
			Cipher cipher = initAESCipherSample(sKey, Cipher.ENCRYPT_MODE);
			// 以加密流写入文件
			CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
			byte[] cache = new byte[1024];
			int nRead = 0;
			while ((nRead = cipherInputStream.read(cache)) != -1) {
				outputStream.write(cache, 0, nRead);
				outputStream.flush();
			}
			cipherInputStream.close();
		} catch (FileNotFoundException e) {
			error(e.getMessage());  
								 
			return false;
		} catch (IOException e) {
			error(e.getMessage()); 
									 
			return false;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				error(e.getMessage());  
									 
										 
				return false;
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				error(e.getMessage());  
									 
				return false;
			}
		}
		return true;
	}

	
	
	/**   
     * 加密解密流   
     * @param in        加密解密前的流   
     * @param out       加密解密后的流   
     * @param cipher    加密解密   
     * @throws IOException   
     * @throws GeneralSecurityException   
     */    
    public static boolean encrypt(InputStream inputStream, OutputStream outputStream, String sKey) throws IOException, GeneralSecurityException {     
         
    	
    	try {	Cipher cipher = initAESCipherSample(sKey, Cipher.ENCRYPT_MODE);
		// 以加密流写入文件
		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
		byte[] cache = new byte[1024];
		int nRead = 0;
		while ((nRead = cipherInputStream.read(cache)) != -1) {
			outputStream.write(cache, 0, nRead);
			outputStream.flush();
		}
		cipherInputStream.close();
	} catch (FileNotFoundException e) {
		error(e.getMessage());  
							 
		return false;
	} catch (IOException e) {
		error(e.getMessage()); 
								 
		return false;
	} finally {
		try {
			inputStream.close();
		} catch (IOException e) {
			error(e.getMessage());  					 
			return false;
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			error(e.getMessage());  
								 
			return false;
		}
	}
	return true;
    	
    }     
 
	/**
	 * AES方式解密文件
	 * 
	 * @param sourceFile
	 * @return
	 */
	public static boolean decryptFile(File sourceFile, File destFile, String sKey) {
		File decryptFile = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			decryptFile = destFile;
			Cipher cipher = initAESCipherSample(sKey, Cipher.DECRYPT_MODE);
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(decryptFile);
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = inputStream.read(buffer)) >= 0) {
				cipherOutputStream.write(buffer, 0, r);
			}
			cipherOutputStream.close();
		} catch (IOException e) {
			error(e.getMessage());  				 
			return false;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				error(e.getMessage());  
				return false;
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				error(e.getMessage());  
				return false;
			}
		}
		return true;
	}

	/**   
     * 加密解密流   
     * @param in        加密解密前的流   
     * @param out       加密解密后的流   
     * @param cipher    加密解密   
     * @throws IOException   
     * @throws GeneralSecurityException   
     */    
  /*  public static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {     
        int blockSize = cipher.getBlockSize() * 1000;     
        int outputSize = cipher.getOutputSize(blockSize);     
    
        byte[] inBytes = new byte[blockSize];     
        byte[] outBytes = new byte[outputSize];     
    
        int inLength = 0;     
        boolean more = true;     
        while (more) {     
            inLength = in.read(inBytes);     
            if (inLength == blockSize) {     
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);     
                out.write(outBytes, 0, outLength);     
            } else {     
                more = false;     
            }     
        }     
        if (inLength > 0)  {   
            outBytes = cipher.doFinal(inBytes, 0, inLength);  }   
        else {   
            outBytes = cipher.doFinal();   }  
        out.write(outBytes);     
    }     
 
	*/
	
	
	
	
	
	
	
	
}
