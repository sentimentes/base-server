package com.wwls.modules.application.utils;

import java.util.List;
import com.wwls.common.utils.JedisUtils;

/****
 * @author xudongdong
 * @version 2016-07-02
 * 
 * 缓存的主键列表
 * **/
public class CacheConfig {
     
	public static final String APP_INFO_MAP ="APP_INFO_MAP";//应用信息列表
	public static final String APP_AUTHOR_MAP ="APP_AUTHOR_MAP";//应用权限MAP
	public static final String APP_USER_SESSION_MAP="APP_USER_SESSION_MAP";//APP用户会话信息列表
	public static final String APP_PAY_INFO="APP_PAY_INFO";//APP支付信息缓存
	public static final String INTERFACE_ACCESS_PARATER="INTERFACE_ACCESS_PARATER";//接口参数列表缓存
	public static final String MENU_LIST_MAPPING = "MENU_LIST_MAPPING";//接口地址映射列表缓存
    public static final String EC_CACHE_DICT_COUNTRY_LIST="EC_CACHE_DICT_COUNTRY_LIST";//国家列表
    public static final String APP_INTEGRAL_INFO = "APP_INTEGRAL_INFO";//积分应用列表
    public static final String  GoodsCategoryCount ="goodsCategoryCount";//商品运营分类数量统计
   // private static final CommonPayManageDao commonPayManageDao = SpringContextHolder.getBean(CommonPayManageDao.class);
    private static final String productDetail = "productDetail";//商品缓存
    //加载用户信息
    public static void setAppUserSession(String key,Object object,int cacheSeconds){
    	JedisUtils.setObject(CacheConfig.APP_USER_SESSION_MAP+key,object,cacheSeconds);
    }
    
    //获取用户信息
  public static Object getAppUserSession(String key){
    	
    return 	JedisUtils.getObject(CacheConfig.APP_USER_SESSION_MAP+key);
    	
    }
    
  //直APP应用信息 接放入redis缓存中
    public static void setAppInfo(String key,Object object,int cacheSeconds){
     
    	JedisUtils.setObject(CacheConfig.APP_INFO_MAP+key, object, 0);//直APP应用信息 接放入redis缓存中	
 
    }
  //直APP应用信息 接放入redis缓存中
    public static Object getAppInfo(String key){
        
    return	JedisUtils.getObject(CacheConfig.APP_INFO_MAP+key);//直APP应用信息 接放入redis缓存中	
 
    }
    
    //初始化APP权限列表
    public static void setAppAuthorList(String key,Object object,int cacheSeconds){
     JedisUtils.setObject(CacheConfig.APP_AUTHOR_MAP+key, object, cacheSeconds);
    	 
    }
  //获取APP权限列表
   public static Object  getAppAuthorList(String key) {
	return    JedisUtils.getObject(CacheConfig.APP_AUTHOR_MAP +key);
   }
  
   //设置App支付信息缓存
   public static void setAppPayInfo(List<String> value,int cacheSeconds){
                System.out.println("设置支付信息=="+CacheConfig.APP_PAY_INFO);
                JedisUtils.setList(CacheConfig.APP_PAY_INFO, value, cacheSeconds);
                
   }
   
//   //获取APP支付信息
//   public static CommonPayManage getAppPayInfo(String type,String clientId){
//	   CommonPayManage cpm = new CommonPayManage();
//	                   cpm.setPayType(type);
//	                   cpm.setClientId(clientId);
//	 List<CommonPayManage> commpayList = commonPayManageDao.findList(cpm);
//	   if(commpayList!=null&&commpayList.size()>0){
//		   return commpayList.get(0);
//	   } 
//      return null;
//   }
   
   
   //初始化积分列表
   public static void setAppIntegral(String key,Object object,int cacheSeconds){
    JedisUtils.setObject(key, object, cacheSeconds);
   }
   
   
   //初始化积分列表
   public static Object getAppIntegral(String key){
   return  JedisUtils.getObject(key);
   	 
   }
   //将接口参数列表初始化近如缓存
   public static void setMenueParam(String key,Object object,int cacheSeconds){
	   JedisUtils.setObject(CacheConfig.INTERFACE_ACCESS_PARATER+key, object, cacheSeconds);
   }
   //获取接口参数缓存
   public static Object getMenueParamList(String key){
	   return    JedisUtils.getObject(CacheConfig.INTERFACE_ACCESS_PARATER +key);
   }
   
   
   //设置运营分类数量
   public static void setGoodsCategoryCount(String key,Object value,int cacheSeconds){
	   JedisUtils.setObject(CacheConfig.GoodsCategoryCount+key, value, cacheSeconds);//将支付信息设置入缓存
   }
   //获取商品运营分类数量
   public static Object getGoodsCategoryCount(String key){
	 return   JedisUtils.getObject(CacheConfig.GoodsCategoryCount+key);
   }
   
   //设置商品数据缓存
   
   public static void setProductDetail(String key,Object value,int cacheSeconds){
	   JedisUtils.setObject(CacheConfig.productDetail+key, value, cacheSeconds);//将支付信息设置入缓存

   }
   
   
   //设置商品数据缓存
   
   public static Object getProductDetail(String key){
	  return  JedisUtils.getObject(CacheConfig.productDetail+key);//将支付信息设置入缓存

   }
}
