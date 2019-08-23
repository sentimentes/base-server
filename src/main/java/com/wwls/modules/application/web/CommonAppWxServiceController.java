package com.wwls.modules.application.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wwls.common.web.BaseController;

/**
 * app版本管理Controller
 * @author fanbo
 * @version 2016-07-01
 */
@Controller
@RequestMapping(value = "wx")
public class CommonAppWxServiceController extends BaseController {

	@RequestMapping(value = "serviceAuth")
	public void serviceAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 Map requestParams = request.getParameterMap();
		 logger.info("获取的整个参数的列表是=requestParams==="+requestParams);
		 	String inputLine;
			String notityXml = "";
		
			try {
				while ((inputLine = request.getReader().readLine()) != null) {
					notityXml += inputLine;
				}
				request.getReader().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("接收到的报文：" + notityXml);
		    String myXml = new String(notityXml.getBytes(),"UTF-8");
		    logger.debug("获取的ＸＭＬ字符串是＝＝＝"+myXml);
		// 验证服务器的有效性
				PrintWriter out = response.getWriter();
				String signature = request.getParameter("signature");
				String timestamp = request.getParameter("timestamp");
				String nonce = request.getParameter("nonce");
				String echostr = request.getParameter("echostr");
		
				logger.info("获取的随机字符串是=echostr==="+echostr);
				logger.info("通知字符串是==nonce=="+nonce);
				logger.info("通知字符串是==echostr=="+echostr);
				logger.info("时间戳是==timestamp=="+timestamp);
				logger.info("获取的签名符串是=signature==="+signature);
			
				
				 
			/*	WXBizMsgCrypt wxcpt;
				try {
					wxcpt = new WXBizMsgCrypt(Global.getConfig("wx.token"),Global.getConfig("wx.aesKey"), Global.getConfig("wx.appId"));
					 String 	sEchoStr = wxcpt.VerifyURL(signature, timestamp,nonce, echostr);
						out.print(sEchoStr);
				} catch (AesException e1) {
					logger.error(e1.getMessage());
				}
		 */
				out.print(echostr);
	}
}