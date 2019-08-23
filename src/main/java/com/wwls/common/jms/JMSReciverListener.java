package com.wwls.common.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wwls.common.config.Global;

public class JMSReciverListener extends Thread implements MessageListener, ExceptionListener{

  private Logger logger = LoggerFactory.getLogger(JMSReciverListener.class);
  private	Connection connection = null;// Connection JMS 客户端到JMS Provider 的连接
  private   Session session;// Session一个发送或接收消息的线程
  private   Destination destination;// Destination ：消息的目的地
  private   MessageConsumer consumer;// 消息接收者
  private	ConnectionFactory connectionFactory;// ConnectionFactory ：连接工厂，JMS 用它创建连接
  private    JMSHandler jMSHandler;//具体的任务处理
  
	public MessageConsumer JMSReciver(String queueName) {
		 connectionFactory = new ActiveMQConnectionFactory( Global.getConfig("activity.MQ.userName"),
					Global.getConfig("activity.MQ.password"), Global.getConfig("activity.MQ.tcp"));
		// 构造从工厂得到连接对象
		try {
			connection = connectionFactory.createConnection();
			connection.setExceptionListener(this);
			// 异常处理
			connection.start();// 连接启动//如果为true，则队列里面的消息没有被取走，继续存在
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			// 获取session，
			destination = session.createQueue(queueName);
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			logger.error(e.getErrorCode());
			 
		}
		return consumer;
	}
	
	
	public void run() {
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
			logger.error(e.getErrorCode());
 		}
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {

				TextMessage txtMsg = (TextMessage) message;

				String msg = txtMsg.getText();
				jMSHandler.execute(msg);
				logger.debug("Received: " + msg);
			}

		} catch (JMSException e) {

			logger.error(e.getErrorCode());		}

	}
	// 异步消息异常处理

	public void onException(JMSException e) {

		logger.error(e.getErrorCode());

	}

	// 测试程序

public static void main(String[] args) { 
	//JMSReciverListener jrl = new JMSReciverListener("firstQueue"); 
         //             jrl.start(); 

}
}
