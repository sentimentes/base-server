package com.wwls.modules.sys.utils;

import java.security.MessageDigest;

import com.wwls.common.security.Digests;
import com.wwls.common.utils.Encodes;
import com.wwls.modules.sys.utils.rsa.RSACoder;


/**
 * 加密工具类
 * @version 2013-12-05
 */
public class PwdUtils {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	/**
	 * Md5加密方法
	 * @param id
	 * @return
	 */
	public static String Md5(String pwd) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = pwd.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);//16位的随机字符串
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);//生成经过1024编码的hash密码
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);//再次自己生成的编码，存数据库
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	
	/**
	 * 解密用户登陆传输的密文密码
	 */
	public static String decryptPsw(String pwd) {
		//解密密码
		String key="abcdefgabcdefg12";
		String decrypt=null;
		try {
			decrypt = RSACoder.aesDecrypt(pwd,key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (decrypt==null){
			decrypt = "";
			return decrypt;
		}else{
			return decrypt;
		}
	}

}
