package com.wwls.common.utils;

import java.security.MessageDigest;
import java.util.UUID;

import com.wwls.common.utils.IdGen;

public class OrderNumberUtils {
	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
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
	
 /***
  * 订单号生成器
  **/
  public static String generateOrderNumber(){
	  return MD5(IdGen.uuid());
  }	

  public static String getOrderIdByUUId(int machineId) {
		//最大支持1-9个集群机器部署
	int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
		}
		// 0 代表前面补充0     
		// 4 代表长度为4     
		// d 代表参数为正数型
		return machineId+String.format("%015d", hashCodeV);
	}
  
  
  
  
  
  

}
