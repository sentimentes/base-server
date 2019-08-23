package com.wwls.common.test;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.model.Filters;
import com.wwls.common.mapper.JsonMapper;
import com.wwls.common.persistence.CrudMongoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class JDCategoryTest{

	 @Resource(name="crudMongoDao")
	 private  CrudMongoDao crudMongoDao;
	 @org.junit.Test
	 public  void save(){
		 System.out.println("开始存储");
	  
	   Bson filter  =Filters.and(Filters.eq("catClass", "0"));
	   List<Document> docList =   crudMongoDao.find("Getcategory",filter);
	  
		 System.out.println("结束存储");
	   
	   
	   
	   
   }
	 	

}
