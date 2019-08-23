package com.wwls.common.utils.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.wwls.common.config.Global;


public class SendTemplateSMS {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SendTemplateSMS.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,String> initMap = new HashMap<String,String>();
		initMap.put("accountSid", Global.getConfig("message.account.id"));
		initMap.put("appId", Global.getConfig("message.app.id"));
		initMap.put("templateId", Global.getConfig("message.MsgTemplate"));
		initMap.put("accountToken", Global.getConfig("message.account.token"));
		sendMessage("18701646864",initMap,"测试信息");
	}

	public static boolean sendMessage(String phone,Map<String,String> initMap,String  message ){
		HashMap<String, Object> result = null;
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(Global.getConfig("message.server.url"), Global.getConfig("message.server.port"));// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(initMap.get("accountSid"),initMap.get("accountToken"));// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(initMap.get("appId"));// 初始化应用ID
		result = restAPI.sendTemplateSMS(phone,initMap.get("templateId") ,new String[]{message});
		logger.debug("返回的信息是==="+result);
 		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			@SuppressWarnings("unchecked")
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				logger.debug(key +" = "+object);
			}
			return true;
		}else{
			//异常返回输出错误码和错误信息
			logger.debug("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
 		 return false;
		}
		
	} 
	
	
}
