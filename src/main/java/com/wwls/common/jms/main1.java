package com.wwls.common.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class main1 {
	public static void main(String[] args) {
		JMSReciverListener js=new JMSReciverListener();
		MessageConsumer consumer=js.JMSReciver("test");
		try {
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage=(TextMessage) message;
					try {
						System.out.println("发送消息"+textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		} catch (JMSException e) {
			System.out.println(e.getErrorCode());
 		}
	}
}
