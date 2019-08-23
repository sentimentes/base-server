//package com.wwls.common.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool;
//import com.wwls.common.config.Global;
//
///**
// * @author xudongdong
// * @version 2015-12-14
// * @ Memcached工具类，进行memcached初始化，以及获取数据等
// * 
// * ****/
//public class MemcachedUtils {
//	
//	
//	private static MemCachedClient mcc = new MemCachedClient();
//
//	static {
//		String[] servers = { Global.getConfig("memcached.servers.ip") };
//		// 创建一个连接池
//		SockIOPool pool = SockIOPool.getInstance();
//		// 设置缓存服务器
//		pool.setServers(servers);
//		// 设置初始化连接数，最小连接数，最大连接数以及最大处理时间
//		pool.setInitConn(Integer.parseInt(Global.getConfig("memcached.servers.init.conn")));
//		pool.setMinConn(Integer.parseInt(Global.getConfig("memcached.servers.min.conn")));
//		pool.setMaxConn(Integer.parseInt(Global.getConfig("memcached.servers.max.conn")));
//		pool.setMaxIdle(Integer.parseInt(Global.getConfig("memcached.servers.max.idle")));
//		// 设置主线程睡眠时间，每30秒苏醒一次，维持连接池大小
//		pool.setMaintSleep(Integer.parseInt(Global.getConfig("memcached.servers.maint.sleep")));
//		// 关闭套接字缓存
//		pool.setNagle(false);
//		// 连接建立后的超时时间
//		pool.setSocketTO(Integer.parseInt(Global.getConfig("memcached.servers.socket.to")));
//		// 连接建立时的超时时间
//		pool.setSocketConnectTO(Integer.parseInt(Global.getConfig("memcached.servers.socket.conn")));
//		// 初始化连接池
//		pool.initialize();
//	}
//
//	protected MemcachedUtils() {
//
//	}
//
//	public static MemCachedClient getInstance() {
//		return mcc;
//	}
//	
//	
///*	public static void main(String[] args) {
//		System.out.println("开始连接");
//		 MemCachedClient mcc =  getInstance();
//		 // mcc.delete("APP_AUTHOR_INIT_STATUS");
//		 
//		 
// Hashtable table =	 (java.util.Hashtable) mcc.get("APP_AUTHOR_TABLE");
//	 List<CommonMenu> list = (List<CommonMenu>) table.get("1234567890");
//	 System.out.println(list.size());
//	 for(CommonMenu cm :list){
//		 System.out.println(cm.getHref());	 
//	 }
//	
//	 
//	}
//	*/
//	
//	public static void main(String[] args) {
//		System.out.println("开始连接");
//		 MemCachedClient mcc =  getInstance();
//	 
//		 List<String> list = new ArrayList<String>();
//		 for(int i=0;i<100;i++){
//			 
//			 list.add(""+i);
//		 }
//		mcc.set("list", list) ;
//	        System.out.print("get value : " +mcc.get("list")); 
//	        
//	}
//}
