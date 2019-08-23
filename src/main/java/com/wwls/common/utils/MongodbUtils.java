package com.wwls.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wwls.common.config.Global;
/***
 * mongodb数据库连接工具类
 * @author xudongdong 
 * @version 2016-03-30 PM 16:44
 * ****/

public class MongodbUtils {

	private static   MongoDatabase db  ;
	private static   MongoClient mongoClient;
	
	/***
	 * 初始化需要加载的信息
	 * */
	public static void initMongodb(){
		//与数据最大连接数50 
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
		build.connectionsPerHost(Integer.parseInt(Global.getConfig("mongodb.servers.init.conn")));  
		//如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待  
		build.threadsAllowedToBlockForConnectionMultiplier(50);  
		build.connectTimeout(Integer.parseInt(Global.getConfig("mongodb.servers.max.idle")));  //最常连接时间
		build.maxWaitTime(Integer.parseInt(Global.getConfig("mongodb.servers.maint.sleep")));  //最大等待时间
		MongoClientOptions options = build.build();  
		 
		  ServerAddress serverAddress = new ServerAddress(Global.getConfig("mongodb.servers.ip"),Integer.parseInt(Global.getConfig("mongodb.servers.port")));
		  List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		  seeds.add(serverAddress);
		  MongoCredential credentials = MongoCredential.createScramSha1Credential(Global.getConfig("mongodb.servers.username"), Global.getConfig("mongodb.servers.source"), Global.getConfig("mongodb.servers.password").toCharArray());
		  List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		  credentialsList.add(credentials);
		
		  mongoClient = new MongoClient(seeds, credentialsList,options);
		  
 		    db = mongoClient.getDatabase(Global.getConfig("mongodb.servers.database"));
		    
	}
	
   public static MongoClient getMongoClient(){
	   
	   return mongoClient;
   }
	/**
	 * 获取数据库
	 * **/
	public static MongoDatabase getMongodb(){
		if(db==null){
			initMongodb();
		}
		return db;
	}


	public static void main(String []args){
	     //从Mongodb中获得名为yourColleection的数据集合，如果该数据集合不存在，Mongodb会为其新建立
				  MongoCollection<Document> collection = getMongodb().getCollection("yourCollection");
				  System.out.println("获取集合成功过");
	     // 使用BasicDBObject对象创建一个mongodb的document,并给予赋值。
	        Document document = new Document();
	        document.put("id", 1001);
	        document.put("msg", "hello world mongoDB in Java");
	        //将新建立的document保存到collection中去
	        collection.insertOne(document);
	        // 创建要查询的document        
	        Document doc = new Document("name", "MongoDB")  
	        .append("type", "database").append("count", 1)
	        .append("info", new Document("x", 203).append("y", 102)); 
	        collection.insertOne(doc);  
	        System.out.println("Done"); 
	        // // 创建一个包含多个文档的列表  
	        List<Document> documents = new ArrayList<Document>();  
	        for (int i = 0; i < 100; i++) {  
	       documents.add(new Document("i", i));  
	         }  
	        // // 向文档中插入列表  
	        collection.insertMany(documents);  
         }
	
}
