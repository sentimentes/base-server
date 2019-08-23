package com.wwls.modules.application.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.application.entity.user.AppMainUser;
import com.wwls.modules.application.service.user.AppMainUserService;
import com.wwls.modules.electricpower.dao.whitelist.ElWhiteListDao;
import com.wwls.modules.electricpower.entity.whitelist.ElWhiteList;
import com.wwls.modules.labourunion.dao.whitelist.LuWorkersWhitelistDao;
import com.wwls.modules.labourunion.entity.whitelist.LuWorkersWhitelist;

/**
 * 管理用户登录令牌验证用户登录口令
 * @author xudongdong
 * @version 2015-08-05
 * */
public class SysUserTokenManager {
	/**
	 * 用户token列表
	 * 用户登录后将用户信息封装到此列表中，且需要将token写入用用户表中
	 * 服务器重启以后需要
	 * */
	protected static Logger logger = LoggerFactory.getLogger(SysUserTokenManager.class);
	
	//private  static CrudMongoDao crudMongoDao = SpringContextHolder.getBean(CrudMongoDao.class);//MongoDB对象
	private  static AppMainUserService appMainUserService = SpringContextHolder.getBean(AppMainUserService.class);
	private  static LuWorkersWhitelistDao luWorkersWhitelistDao = SpringContextHolder.getBean(LuWorkersWhitelistDao.class);//用户白名单
	
//	private  static PbWhiteListDao pbWhiteListDao = SpringContextHolder.getBean(PbWhiteListDao.class);//党建用户白名单
	
	private  static ElWhiteListDao elWhiteListDao = SpringContextHolder.getBean(ElWhiteListDao.class);//电力用户白名单
	
	//用户toke列表
	public static  AppMainUser getTokenTable(String token){
        
	       if(StringUtils.isNotBlank(token)){
//	    	   Bson bson = Filters.and(Filters.eq("accesstoken", token));
//	    	 List<Document> documentList =  crudMongoDao.find(MongoTableConstant.appMainUser, bson);
	    	   AppMainUser usr=new AppMainUser();
	    	   usr.setAccesstoken(token);
	    	   List<AppMainUser> documentList = appMainUserService.findTokenList(usr);
	    	 if(documentList!=null&&documentList.size()>0){
	    		 //AppMainUser user = (AppMainUser) JsonMapper.fromJsonString(JsonMapper.toJsonString(documentList.get(0)), AppMainUser.class);	 
	    			return documentList.get(0);
	    	  }
	    	 
	    	/* else{
	    	  AppMainUser  appMainUser	= new AppMainUser();  
	    		  appMainUser.setAccesstoken(token);
	    	   List<AppMainUser> userList =	  appMainUserService.findList(appMainUser);
	    	   if(userList!=null &&!userList.isEmpty()){
	    		   AppMainUser  appMain = userList.get(0);
	    		   
	    		   //InitLoginToken.processAppMainUser(appMain);
	    		   CacheConfig.setAppUserSession(appMain.getAccesstoken(), appMain, 0);
	    		   return appMain;
	    	   }
	    	  }*/
	       }
          return null;
			  
	}
	
	
	/***
	 * 鉴定用户权限
	 * ****/
	public static boolean  checkPermssion(String token){
		
		if(StringUtils.isNotBlank(token)&&getTokenTable(token)!=null){	
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * 从登陆列表里面获取用户信息
	 * ***/
	public static AppMainUser getLoginMainUser(String token){
	if(StringUtils.isNotBlank(token)){
		AppMainUser user = 	 getTokenTable(token);
		logger.debug("从用户表里面获取用户信息"+user);
	return user;
	}else{
	  return null;
	}	
	}
	
	
	/**
	 * 根据用户手机号验证用户白名单
	 * @param phnoe
	 * @return
	 */
	public static LuWorkersWhitelist checkUserByPhone(String phone,String officeId) {
		LuWorkersWhitelist luWorkersWhitelist = null;
		LuWorkersWhitelist work  = new LuWorkersWhitelist();
		work.setPhone(phone);
		work.setOfficeId(officeId);
		  
		 
			// 根据条件查询
			luWorkersWhitelist = luWorkersWhitelistDao.getByPhone(work);
			return luWorkersWhitelist;	 
	}
	
	
//	/**
//	 * 根据用户手机号验证用户白名单
//	 * @param phnoe
//	 * @return
//	 */
//	public static PbWhiteList pbCheckUserByPhone(PbWhiteList pbWhiteList) {
//
//		if (StringUtils.isNotBlank(pbWhiteList.getPhone())) {
//			// 根据条件查询
//			pbWhiteList = pbWhiteListDao.getByPhone(pbWhiteList);
//
//			if(pbWhiteList!=null){
//				return pbWhiteList;
//			}else{
//				return null;
//			}
//		} else {
//			return null;
//		}
//	}
//	
	
	/**
	 * 根据用户手机号验证电力用户白名单
	 * @param phnoe
	 * @return
	 */
	public static ElWhiteList elCheckUserByPhone(ElWhiteList elWhiteList) {

		if (StringUtils.isNotBlank(elWhiteList.getPhone())) {
			// 根据条件查询
			elWhiteList = elWhiteListDao.getByPhone(elWhiteList);

			if(elWhiteList!=null){
				return elWhiteList;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}
}
