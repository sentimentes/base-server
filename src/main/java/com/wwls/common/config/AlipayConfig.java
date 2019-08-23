package com.wwls.common.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
		
		public static String app_id = "2016092400584557";
		
		// 商户私钥，您的PKCS8格式RSA2私钥
	    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQq4mvPInwBznexM3gROT+qrx48aHIsUMgmecRrMSM/f9RBZMx/i+Xs91M9UVc3qt/PuW7l5s5DoO1JPT1f39Mrw/xphSks+knd86k8Y3CxFy2N+T60FOOgeE96URt2gMdaGtY891oSr5IO3pAAX4aGYQBXzaHoJMnOhkFKlfZH87wOXtgwxRw/raLiYzheidWB7y9szbmJ15O6EJhuZNMh/MEzva8OOXLuJyRQsWN5x2vdmXbzEcEsMK/Mx0dOVzqZSQE481CvdcLh4/thj1Inyksuawfo1mcKsDCb9I6PScSsHkIWbzvvs9T+WtKZwv4KxOVwBKhMn1vTEGPml+hAgMBAAECggEAOfV/Y8VNPeynMqiGEqOHWr/sYkfsGQtraVQ/TsV4uUPJVhx1rEG5r31P29UnxN0wt/nq8Az9Ew7NlXUI7pH13jMEoDt5acLqVfWZxFtCABXLTvV1t0ZfM2i1ZKhf0eLV/KIo36pTucS4Fxg9EgeHDc3LJQGQRyX9zmloPLmKGGIbGy+yFBy6N9NMEVojlYigbUaTLtvp/cyUp/Mv452/zS2T7whn7hOyzi3hjrIi85rC/no1TFYLp2ge1uVS9nFH6Fi4Bh8avJvHTK+7YaODWXkwaWRXPRluuyqq8OBxcik6RpWIeIZ9dN6csCVqIn2FN9fhRige8TiPEbjnE6rQAQKBgQDS/45G5e06T43FVpGuVkCoHJXry4/aXQJQPS4pBQx2XObluNzlhuP1GwupShX3gbPESnaiOwO0cj4KzFI5oKNfh3t5vJ91QuqOPhSuRbqcFngjQcDcnSYmC7c53ziUxdkPteCVCQdY9rFf+WuEIPOrOGWVbrS8wvRLeCi6nqbxcQKBgQCvhntWw2Nc0PfYq1BGSeWCcWGWZFc5VyjuW0ib3qY3MaTJACoBE62KfYAGMAz23UQiFF+DSo894zh3/1FtH0FL6vDWcTniGnAvSqZwilJd/P1GKmsneyaBNWkD45KihNK7Uqx8vh95s9NjCqkaaN55+B3bdgvA82LaWQfUMiQ5MQKBgDk69+xjESVvzpRoFruiyYbPuhpq7GneMHJzJcIajHbOXBKTQdc/7zTcUR1p6utnUss7J1J5j1eyZQGoyeS3nZibtvIeJ2I29EgArrtWTndn4M4vV5B/T4miQ+B+m2o+/9EzE+dYu5V11Eaj8HW2OmGGuob9sChC+OA/h0yXG9EBAoGAFkW+SNgqN5DcokW6vfCKiu7QL0E0QDZrHzO+Fuk9tis6ebbnnZNdpgEeyao3OuwAxUJIjGRdoMDAXZmsP2iD5Jo/TTa43ZP1QEr8q7MhmIONr+WaCzx5OlQIq1GpPa4U/2gSMxH89Hh5DkxAAPXlpRxyzXJBM46usH4znB1ws0ECgYBh4clzi5tUebZrpVIWr5WEyv2RJ4Oq90fAbuLdyyDeURSQzVUnw/aa3q1ubuDoI3tsrPA6LruNGtJnIL2bcMUTqjaGLXcGyOnM9R7YLGSTHFsgeldD6vb+jWG5vrZTjxCtJ3Nag9xBUIYf5BoxDaeM2X8RIANqL5WFhj9HxblKLQ==";
		
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn3xv2LVB5RSl8i51PqkajaJw8KZbkUbi78dki6iKgQBZ/cZ7v65C4RNZzbMgEwWlCA4S/GS9tDH51DK3QR4i8ho3KebZyKceR+5DOYT+zJeBDfwbtJOnjvpKwIXit/CcF8BRf33SJv1YQUOkpkn53bn5G2M39Xyi+a8LaRljBltsEMomNig/Nd7VMx20FsbZvQrgQCo6EcJXpMqOij0bM8zmjJDoJGKRpGU7k1QNqbsGpLAp1t1Jw5nNPDDyr4Jv40cwZf4OTwrpColvR8ifMXocnLFN6cJSQtKtYOiVPBk9OIZ+pVTuPeLyF0j2pVSJ1xso+ETI/On4fjRABbVWKQIDAQAB";

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://192.168.43.252:8080/base-server/a/shoppingmall/goods/user/alipayReturnNotice";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://192.168.43.252:8080/base-server/a/shoppingmall/goods/user/alipayReturnNotice";

		// 签名方式
		public static String sign_type = "RSA2";
		
		// 字符编码格式
		public static String charset = "utf-8";
		
		// 支付宝网关
		public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";  

		
		// 支付宝网关
		public static String log_path = "C:\\";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	    /** 
	     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	     * @param sWord 要写入日志里的文本内容
	     */
	    public static void logResult(String sWord) {
	        FileWriter writer = null;
	        try {
	            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
	            writer.write(sWord);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    } 
			
}

