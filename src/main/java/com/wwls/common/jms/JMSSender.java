package com.wwls.common.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.wwls.common.config.Global;

public class JMSSender {
	
  public static void sendMessage(int n,String queueName,String message){  
	  // ConnectionFactory ：连接工厂，JMS 用它创建连接  
      ConnectionFactory connectionFactory; // Connection ：JMS 客户端到JMS  
      // Provider 的连接  
      Connection connection = null; // Session： 一个发送或接收消息的线程  
      Session session; // Destination ：消息的目的地;消息发送给谁.  
      Destination destination; // MessageProducer：消息发送者  
      MessageProducer producer; // TextMessage message;  
      // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar  
      connectionFactory = new ActiveMQConnectionFactory( Global.getConfig("activity.MQ.userName"),
				Global.getConfig("activity.MQ.password"), Global.getConfig("activity.MQ.tcp"));
      try { // 构造从工厂得到连接对象  
          connection = connectionFactory.createConnection();  
          // 启动  
          connection.start();  
          // 获取操作连接  
          session = connection.createSession(Boolean.TRUE,  
                  Session.AUTO_ACKNOWLEDGE);  
          // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
          destination = session.createQueue(queueName);  
          // 得到消息生成者【发送者】  
          producer = session.createProducer(destination);  
          // 设置不持久化，此处学习，实际根据项目决定  
          producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
          // 构造消息，此处写死，项目就是参数，或者方法获取 
          for(int i=0;i<n;i++){
        	   TextMessage messages = session.createTextMessage(message+i);
               producer.send(messages); 
               System.out.println(messages.getText());
          }
          session.commit();  
      } catch (Exception e) {  
          e.printStackTrace();  
      } finally {  
          try {  
              if (null != connection)  
                  connection.close();  
          } catch (Throwable ignore) {  
          }  
      }  
  }  
}
