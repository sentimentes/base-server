package com.wwls.common.constant;

import java.util.Hashtable;
/**
 * 数据传输的常量标识
 */
public class HttpConstant {
	
	
	public static final Integer SUCCESS=0;//成功 
	public static final Integer FAILURE=1;//失败 失败的状态可能有很多根据具体的情况定义
	public static final Integer UNACTIVATE=0;//未激活
	public static final Integer ALACTIVATE=1;//已激活
	public static final Integer REGISTER_REPEAT=2;//账号已经注册
 	public static final Integer ERROR_PARMATER=201;//参数错误
	public static final Integer ERROR404=404;//404错误
	public static final Integer ERROR500=500;//500错误
	public static final Integer ERROR400=400;//400错误
	public static final Integer NO_AUTHOR=520;//尚未授权登录
	public static final Integer NO_INTERFACE_AUTHOR=521;//接口权限不足
	public static final String  DEVICE_TYPE="deviceType";//设备类型 0 PC：1；微信H5；2安卓；3：iOS
	public static final String  CHANNEL="channel";//渠道
	public static final String  TIMESTAMP= "timestamp";//时间戳参数
	public static final String  PASSCODE="passcode";// 校验码
	public static final String  CLIENTID="clientid";//客户端识别码
	public static final String PAY_WAY[]={"0","1","2","3","4","5","6","7","8","9"};// 支付方式1：支付宝；2：微信；3：银联；4：账户余额支付;5 ：百度钱包支付6：话费APP7:appstore充值8卡片充值9:话费H5：
    public static final Integer TRADING_SUCCESS= 2;//交易成功
    public static final Integer TRADING_TIME_OUT=506;//交易超时
	public static final String  CHECK_STATUS[]={"0","1"};//状态0未审核1已审核
	public static final String  READ_STATUS[]={"0","1"};//状态0未阅读1已阅读
	public static final String  PAYMENT_STATUS[]={"0","1"};//支付状态0支付失败1支付成功
	public static final String  PRAISE_TYPE[]={"0","1","2"};//点赞类型1商品2资讯
	public static final String  OPERA_TYPE[]={"0","1","2"};//操作类型1点赞2点差
	public static final String  COLLECTION_TYPE[]={"0","1","2"};//收藏类型1商品2资讯
	public static final String MONTH_STR[]={"-01","-02","-03","-04","-05","-06","-07","-08","-09","-10","-11","-12"};// 月份字符串，1到12月份
	
	//用户短信校验;主键是用户手机号码；值是短线验证码
		private static  Hashtable<String,String> MESSAGE_PHONE_TABLE= new Hashtable<String,String>();
		public  static Hashtable<String,String>  getMessageTable(){
	 
			return MESSAGE_PHONE_TABLE;
		}
		/**
		 * 获取验证码
		 * **/
	    public static String getValidateCode(String loginname){
	    	String validateCode = MESSAGE_PHONE_TABLE.get(loginname);
	    	if(validateCode!=null){
	    		 
	    		MESSAGE_PHONE_TABLE.remove(loginname);
	    	}
	    return validateCode;
	    	
	    }	

}
