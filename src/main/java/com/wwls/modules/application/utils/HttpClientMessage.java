package com.wwls.modules.application.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.Submit;
import com.message.Client;
import com.wwls.common.constant.HttpConstant;
import com.wwls.modules.application.entity.CommonAppInfo;

/**
 * 通过httpGet方法获取在服务器上调用发信息
 * 
 * */
public class HttpClientMessage {
	
	protected static Logger logger = LoggerFactory.getLogger(HttpClientMessage.class);
	
	public static boolean getMessage(String phone,CommonAppInfo cia){
		String tag = "{\"result\":0}";
		int random = (int)((Math.random()*9+1)*100000);
		logger.debug("获取的客户端ID是"+cia.getMsgClientid());
		logger.debug("获取的模板ID是"+cia.getMsgTemplate());
	  Client client =   new Client(cia.getMsgClientid());
	  
	  Submit submit = new Submit();
		submit.setMessage(cia.getMsgFirst()+random+cia.getMsgLast());
		submit.setMobile(phone);
		submit.setTempId(cia.getMsgTemplate());
	    tag = client.sendMessage(submit) ;
		if(tag.equals(tag)){
			HttpConstant.getMessageTable().put(phone,Integer.toString(random));
			logger.info("放入消息列表的手机号是"+phone+"验证码是"+random);
			return true;
		}
		return false;
	}
	
	
	public static boolean sendMessage(String phone,CommonAppInfo cia,String activiteCode){
		String tag = "{\"result\":0}";
		
		logger.debug("获取的客户端ID是"+cia.getMsgClientid());
		logger.debug("获取的模板ID是"+cia.getMsgTemplate());
	  Client client =   new Client(cia.getMsgClientid());
	  
	  Submit submit = new Submit();
		submit.setMessage(cia.getMsgFirst()+activiteCode+cia.getMsgLast());
		submit.setMobile(phone);
		submit.setTempId(cia.getMsgTemplate());
	    tag = client.sendMessage(submit) ;
		if(tag.equals(tag)){
			return true;
		}
		return false;
	}
	
	 
	public static void main(String []args){
		CommonAppInfo app = new CommonAppInfo();
		app.setMsgClientid("85e2ae1f511f45b7900f6b29aba86f02");
		app.setMsgFirst("【大布阅读】您的验证码是：");
		app.setMsgLast("。请不要把验证码泄露给其他人。如非本人操作，可不用理会！");
		app.setMsgTemplate("182");
	 boolean tag=	getMessage("18701646864",app);
	 logger.info("返回的结果是"+tag);
	} 
	 	

}
