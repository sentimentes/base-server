package com.wwls.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wwls.common.mapper.JsonMapper;
import com.wwls.common.utils.MongodbUtils;
@Repository
public class CrudMongoDao {
	private static MongoClient mongoClient=MongodbUtils.getMongoClient();
    private static MongoDatabase mongoDB =MongodbUtils.getMongodb(); 
    
    //根据条件判断查询的数据是否存在
	public boolean isExits(String collectionName, Map<String, Object> filterMap) {  
        if (filterMap != null) {  
            FindIterable<Document> docs = mongoDB.getCollection(collectionName).find(new Document(filterMap));  
  
            Document doc = docs.first();  
            if (doc != null) {  
                return true;  
            } else {  
                return false;  
            }  
        }  
        return false;  
    }  
	
	
	 /****
     * 插入
     * ***/
    public boolean insert(String collectionName, Object object) {  
    	if(object!=null){
     String jsonString = 	JsonMapper.toJsonString(object);
      Document document = (Document) JsonMapper.fromJsonString(jsonString, Document.class);
         
      mongoDB.getCollection(collectionName).insertOne(document);  
            return true;  
        }  
        return false;  
    }  
    
    
	
	 /****
    * 插入可以在外面设置参数
    * ***/
   public boolean insertDocument(String collectionName, Document document) {  
   	if(document!=null){
     mongoDB.getCollection(collectionName).insertOne(document);  
           return true;  
       }  
       return false;  
   } 
    //根据ID删除
    public boolean deleteById( String collectionName, String _id) {  
        
        Bson filter = Filters.eq("_id", _id);  
       
        DeleteResult deleteResult = mongoDB.getCollection(collectionName).deleteOne(filter);  
        long deletedCount = deleteResult.getDeletedCount();  
  
        return deletedCount > 0 ? true : false;  
    }  
  
    //根据众多条件删除
    public boolean delete(String collectionName, Map<String, Object> map) {  
        if (map != null) {  
            DeleteResult result = mongoDB.getCollection(collectionName).deleteMany(new Document(map));  
            long deletedCount = result.getDeletedCount();  
            return deletedCount > 0 ? true : false;  
        }  
        return false;  
    }  
  
    //更新一条
    public boolean updateOne(String collectionName, Bson bson, Document updateMap) {  
        if (bson != null  && updateMap != null) {  
            UpdateResult result = mongoDB.getCollection(collectionName).updateOne(bson, new Document("$set", new Document(updateMap)));  
            long modifiedCount = result.getModifiedCount();  
            return modifiedCount > 0 ? true : false;  
        }  
  
        return false;  
    }  
    
    
    //更新一条
    public boolean updateMany(String collectionName, Bson bson, Map<String, Object> updateMap) {  
        if (bson != null  && updateMap != null) {  
            UpdateResult result = mongoDB.getCollection(collectionName).updateMany(bson, new Document("$set", new Document(updateMap)));  
            long modifiedCount = result.getModifiedCount();  
            return modifiedCount > 0 ? true : false;  
        }  
  
        return false;  
    }  
    //根据ID更新
    public boolean updateById(String collectionName, String _id, Document updateDoc) {  
        //ObjectId objectId = new ObjectId();  
        Bson filter = Filters.eq("_id", _id);  
  
        UpdateResult result = mongoDB.getCollection(collectionName).updateOne(filter, new Document("$set", updateDoc));  
        long modifiedCount = result.getModifiedCount();  
  
        return modifiedCount > 0 ? true : false;  
    }  
    //查找
    public List<Document> find(String collectionName, Bson filter) {  
        final List<Document> resultList = new ArrayList<Document>();  
        if (filter != null) {  
            FindIterable<Document> docs = mongoDB.getCollection(collectionName).find(filter);  
            docs.forEach(new Block<Document>() {  
  
                public void apply(Document document) {  
                    resultList.add(document);  
                }  
            });  
        }  
  
        return resultList;  
    }  
    
    //查找和排序
    public List<Document> findAndSort(String collectionName, Bson filter,Bson sort) {  
    	if(sort==null){
    		sort = new BasicDBObject("_id",1);
    	}
        final List<Document> resultList = new ArrayList<Document>();  
        if (filter != null) {  
            FindIterable<Document> docs = mongoDB.getCollection(collectionName).find(filter).sort(sort);  
            docs.forEach(new Block<Document>() {  
  
                public void apply(Document document) {  
                    resultList.add(document);  
                }  
            });  
        }  
  
        return resultList;  
    }  
  //根据Id 查找
    public Document findById(String collectionName, String _id) {  
  
        Document doc = mongoDB.getCollection(collectionName).find(Filters.and(Filters.eq("_id", _id),Filters.eq("delFlag", "0"))).first();  
        return doc;  
    }  
  
  
    /** 
     * 分页查询 
     * 
     * @param dbName 
     * @param collectionName 
     * @param filter 
     * @param pageIndex      从1开始 
     * @param pageSize 
     * @return 
     */  
    public List<Document> findByPage( String collectionName, Bson filter, int pageIndex, int pageSize,Bson sort) {  
    	if(sort==null){
    		sort=new BasicDBObject("_id",-1);
    	}
      
        final List<Document> resultList = new ArrayList<Document>();  
        FindIterable<Document> docs = mongoDB.getCollection(collectionName).find(filter).sort(sort).skip((pageIndex - 1) * pageSize).limit(pageSize); 
        docs.forEach(new Block<Document>() {  
            public void apply(Document document) {  
                resultList.add(document);  
            }  
        });  
  
        return resultList;  
    }  
  
    public MongoCollection<Document> getCollection(String collectionName) {  
        return mongoDB.getCollection(collectionName);  
    }  
  
  
    
  
    public long getCount(String dbName, String collectionName,Bson bson) {
    	long count = 0;
    	if(bson ==null){
    		count = mongoDB.getCollection(collectionName).count();
    	}else{
    		count = mongoDB.getCollection(collectionName).count(bson);
    	}
        return count;  
    }  
  
    /** 
     * 查询dbName下的所有表名 
     * 
     * @param dbName 
     * @return 
     */  
    public List<String> getAllCollections(String dbName) {  
        MongoIterable<String> cols =mongoDB.listCollectionNames();  
        List<String> _list = new ArrayList<String>();  
        for (String s : cols) {  
            _list.add(s);  
        }  
        return _list;  
    }  
  
    /** 
     * 获取所有数据库名称列表 
     * 
     * @return 
     */  
  public MongoIterable<String> getAllDatabaseName() {  
        MongoIterable<String> s = mongoClient.listDatabaseNames();  
        return s;  
    }  
 
    public void close() {  
        if (mongoClient != null) {  
            mongoClient.close();  
            mongoClient = null;  
        }  
    }  

	
	
	
	
	
	
}
