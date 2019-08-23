package com.wwls.modules.tools;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	 public static void main(String[] args) throws Exception{  
	        // TODO Auto-generated method stub  
	        Properties props = new Properties();  
	        props.setProperty("mail.smtp.auth", "false");  
	        props.setProperty("mail.transport.protocol", "smtp");  
	        Session session = Session.getInstance(props);  
	        session.setDebug(true);  
	          
	        Message msg = new MimeMessage(session);  
	        msg.setText("邮件测试");  
	        msg.setFrom(new InternetAddress("1399789721@qq.com"));  
	      
	        Transport transport = session.getTransport();  
	        transport.connect("smtp.qq.com", 465, "1399789721@qq.com", "nn");  
	        transport.sendMessage(msg, new Address[]{new InternetAddress("1399789721@qq.com")});  
	        transport.close();  
	    } 
}
