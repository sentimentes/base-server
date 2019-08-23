package com.wwls.common.jms;

public class main {
	public static void main(String[] args) {
		JMSSender.sendMessage(100,"test", "你有一条新信息");
	}
}
