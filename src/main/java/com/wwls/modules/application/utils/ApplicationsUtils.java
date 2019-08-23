package com.wwls.modules.application.utils;

import java.util.List;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.modules.application.dao.CommonAppInfoDao;
import com.wwls.modules.application.dao.CommonMenuDao;
import com.wwls.modules.application.entity.CommonAppInfo;
import com.wwls.modules.application.entity.CommonMenu;
import com.wwls.modules.sys.utils.UserUtils;

/****
 * @author xudongdong
 * @version 2016-06-28
 * @desc 应用工具类
 * 
 * ***/
public class ApplicationsUtils {
	
	
	private static CommonMenuDao commonMenuDao  = SpringContextHolder.getBean(CommonMenuDao.class);
	private static CommonAppInfoDao commonAppInfoDao = SpringContextHolder.getBean(CommonAppInfoDao.class);
	
	//private static PbDepartmentService pbDepartmentService = SpringContextHolder.getBean(PbDepartmentService.class);
	
	/***
	 * 获取接口菜单地址列表
	 * *****/
	public static List<CommonMenu>  getMenueList(){
		
	 return 	commonMenuDao.findAllList(new CommonMenu());
		
	}
	
    

    
    public static List<CommonAppInfo> getAppInfoList(){
    	
    	CommonAppInfo commonAppInfo = new CommonAppInfo();
    	return commonAppInfoDao.findList(commonAppInfo);
    }
    
    /***
     * 获取应用信息
     * **/
   public static  String getAppInfo(String appid){
	   CommonAppInfo app=   commonAppInfoDao.get(appid);
	   if(app!=null){
		   
		   return app.getName();
	   }
     	return "无";
    }
    public static List<CommonAppInfo> getMyAppInfoList(){
    	
    	CommonAppInfo commonAppInfo = new CommonAppInfo();
    	commonAppInfo.setOfficeId(UserUtils.getUser().getOffice().getId());
    	return commonAppInfoDao.findList(commonAppInfo);
    } 
    
    
	
	
//	 /**
//     * 获取部门信息列表
//     * @return
//     */
//	public static List<PbDepartment> getDepartmentList() {
//		return pbDepartmentService.findList(new PbDepartment());
//	}
    
}
