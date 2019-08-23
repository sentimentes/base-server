package com.wwls.common.jms;
/***
 * 具体的处理消息队列的内容的接口
 * @author xudongdong
 * @version 2016-11-01
 * 
 */
public interface JMSHandler {
   public void execute(String message);
}
