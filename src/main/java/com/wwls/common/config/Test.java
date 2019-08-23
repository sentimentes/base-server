package com.wwls.common.config;



public class Test {
	
	public static void main(String[] args) {
		double sum=test();
		System.out.println(sum);
	}
	public static double test(){
		double sum=0;
		try {
			sum=1/0;
			System.out.println("1");
		} catch (Exception e) {
			System.out.println("2");
			return 1.0;
		}finally{
			System.out.println("3");
			return 2.0;
		}
	}
}
