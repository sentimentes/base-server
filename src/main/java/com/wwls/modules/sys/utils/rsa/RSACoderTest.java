//package com.wwls.modules.sys.utils.rsa;
//
//import static org.junit.Assert.*;
//
//import java.security.Key;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * 公钥mapEntity
// * @version 2017-05-15
// */
//public class RSACoderTest{
//
//	private String publicKey;
//	  private String privateKey;
//	 
//	  @Before
//	  public void setUp() throws Exception {
//	    Map<String, Key> keyMap = RSACoder.initKey();
//	    publicKey = RSACoder.getPublicKey(keyMap);
//	    privateKey = RSACoder.getPrivateKey(keyMap);
//	    System.err.println("公钥: \n\r" + publicKey);
//	    System.err.println("私钥： \n\r" + privateKey);
//	  }
//	 
//	  @Test
//	  public void test() throws Exception {
//		  //使用公钥加密    私钥解密
//		  String srrr="TCG11GnNC7U3BdWPuBeecwRZ9YYdAnNZVwykYh+UxAk3yxyu/RfhK/W5cJjiLz5rlcXAigx4XiX5c/3DAtQ9eV3oyiRmMA7vUsuQ+GoeUbC/IrMPQjsJiAVXEcBFEqla3oSJPMB7+yhN9oPlukGVFtcnsfGcMKGD7Bijk5JypsE=";
//		  byte[] siyaojiemi = RSACoder.decryptByPrivateKey(RSACoder.encryptBASE64(srrr.getBytes()),privateKey);
//		  System.out.println("3333="+siyaojiemi);
//		  
//		  
////	    System.err.println("公钥加密——私钥解密");
////	    String inputStr = "abc";
////	    String srrr="TCG11GnNC7U3BdWPuBeecwRZ9YYdAnNZVwykYh+UxAk3yxyu/RfhK/W5cJjiLz5rlcXAigx4XiX5c/3DAtQ9eV3oyiRmMA7vUsuQ+GoeUbC/IrMPQjsJiAVXEcBFEqla3oSJPMB7+yhN9oPlukGVFtcnsfGcMKGD7Bijk5JypsE=";
////	    //byte[] encodedData = RSACoder.encryptByPublicKey(inputStr, publicKey);
////	    //byte[] encodedData = new byte["TCG11GnNC7U3BdWPuBeecwRZ9YYdAnNZVwykYh+UxAk3yxyu/RfhK/W5cJjiLz5rlcXAigx4XiX5c/3DAtQ9eV3oyiRmMA7vUsuQ+GoeUbC/IrMPQjsJiAVXEcBFEqla3oSJPMB7+yhN9oPlukGVFtcnsfGcMKGD7Bijk5JypsE="];
////	    byte[] decodedData = RSACoder.decryptByPrivateKey(srrr.getBytes(),privateKey);
////	    String outputStr = new String(decodedData);
////	    //String olineStr = new String(encodedData);
////	    System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr+ "\n\r"+"加密后：");
////	    assertEquals(inputStr, outputStr);
//	  }
//	 
////	  @Test
////	  public void testSign() throws Exception {
////	    System.err.println("私钥加密——公钥解密");
////	    //String inputStr = "sign";
////	    String inputStr = "111";
////	    byte[] data = inputStr.getBytes();
////	    byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
////	    byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
////	    System.out.println("encodedData=="+new String(encodedData));
////	    System.out.println("decodedData=="+new String(decodedData));
////	    String outputStr = new String(decodedData);
////	    System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
////	    assertEquals(inputStr, outputStr);
////	    System.err.println("私钥签名——公钥验证签名");
////	    // 产生签名
////	    String sign = RSACoder.sign(encodedData, privateKey);
////	    //String sign="TCG11GnNC7U3BdWPuBeecwRZ9YYdAnNZVwykYh+UxAk3yxyu/RfhK/W5cJjiLz5rlcXAigx4XiX5c/3DAtQ9eV3oyiRmMA7vUsuQ+GoeUbC/IrMPQjsJiAVXEcBFEqla3oSJPMB7+yhN9oPlukGVFtcnsfGcMKGD7Bijk5JypsE=";
////	    System.err.println("签名:" + sign);
////	    // 验证签名
////	    boolean status = RSACoder.verify(encodedData, publicKey, sign);
////	    System.err.println("状态:" + status);
////	    assertTrue(status);
////	  }
//
//}