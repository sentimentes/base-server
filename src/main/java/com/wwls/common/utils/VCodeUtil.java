package com.wwls.common.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class VCodeUtil {
	private static Long randomNumber;
	private static Long curIndex = Long.valueOf(0L);
	static Set<Long> set=new HashSet<Long>();
	public VCodeUtil() {
	}
 
	public synchronized static Long getId() throws Exception {
		Long index = null;
		// 从0到999 curIndex*100 curIndex 100-99900
		index = (curIndex = curIndex.longValue() + 1L).longValue() % 1000L;
		if (curIndex.longValue() >= 1000L) {
			curIndex = 0L;
		}
		// 随机数 1-10
		randomNumber = Long.valueOf(new Random().nextInt(100));
		return (new Date()).getTime() * 100000L + index.longValue() * 100L
				+ randomNumber.longValue();
	}
	
	public static void main(String[] args) {
		Long l=System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			try {
				new Thread(new java.lang.Runnable() {
					
					@Override
					public void run() {
						try {
							set.add(getId());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis()-l);
		System.out.println(set.size());
	}
}
