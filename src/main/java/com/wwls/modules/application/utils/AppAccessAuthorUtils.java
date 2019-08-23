package com.wwls.modules.application.utils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wwls.common.constant.DataResult;
import com.wwls.common.constant.HttpConstant;
import com.wwls.common.utils.MD5Utils;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.application.entity.CommonAppInfo;
import com.wwls.modules.application.entity.CommonMenu;
import com.wwls.modules.application.entity.user.AppMainUser;
import com.wwls.modules.application.service.CommonAppInfoService;
import com.wwls.modules.application.service.CommonMenuService;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.service.OfficeService;
/**
 * @author xudongdong
 * @version 2016-07-02
 * ***/
public class AppAccessAuthorUtils {
	protected static Logger logger = LoggerFactory.getLogger(AppAccessAuthorUtils.class);
	private  static CommonAppInfoService commonAppInfoService= SpringContextHolder.getBean(CommonAppInfoService.class);
	private static CommonMenuService commonMenuService = SpringContextHolder.getBean(CommonMenuService.class);
	private static  OfficeService officeSerice =SpringContextHolder.getBean(OfficeService.class) ;
//	private static CommonAppIntegralService commonAppIntegralService = SpringContextHolder.getBean(CommonAppIntegralService.class);
	//private static CommonIntegralService commonIntegralService = SpringContextHolder.getBean(CommonIntegralService.class);
	/***
	 * 需要加载接口端的权限相关的信息
	 * 进行初始化
	 * ****/
	public static void initAppAccessAuthor() {
		/****
		 * 加载应用信息列表 查找 菜单列表，将相关信息放入缓存中
		 ****/
		CommonAppInfo appInfo = new CommonAppInfo();
		List<CommonAppInfo> appInfoList = commonAppInfoService.findList(appInfo);// 获取应用信息列表
		// 获取应用对应的菜单列表即可
		for (CommonAppInfo cai : appInfoList) {
			logger.info("初始化接口缓存---开始");
			// 1.根据APP信息获取 APP对应的角色信息
			CommonMenu commonMenu = new CommonMenu();
			commonMenu.setAppId(cai.getId());
			List<CommonMenu> commonMenuList = commonMenuService.findAppMenuList(commonMenu);
			CacheConfig.setAppAuthorList(cai.getId(), commonMenuList, 0);
			CacheConfig.setAppInfo(cai.getId(), cai, 0);
			logger.info("初始化接口缓存---结束");
		}
	}
	/**
	 * 支付权限初始化
	 * @param request
	 * @return
	 */
 
	public static void initPay(){
		/*CommonPayManage commonpaymanage = new CommonPayManage();
		 
		List<CommonPayManage> commonpaymanageList= commonpaymanageservice.findList(commonpaymanage);
		List<String> stringList = new ArrayList<String>();
		 for(CommonPayManage cml:commonpaymanageList){
			 stringList.add(JsonMapper.toJsonString(cml));
		 }
		 
		CacheConfig.setAppPayInfo(stringList, 0);
//		System.out.println("支付信息加载完毕");*/
////		CommonPayManage cpm= CacheConfig.getAppPayInfo("1", "614712d68c6847838c5ed170e484e208");
//		System.out.println("获取放入缓存的信息是"+cpm);
		}
//	 /***
//	 * @desc:初始化积分信息缓存
//	 * @author xudongdong
//	 * @version 2016-09-06
//	 * ****/
//	public static void initIntegral(){
//		CommonAppIntegral  commonAppIntegral = new CommonAppIntegral();
//	List<CommonAppIntegral> integerList=	commonAppIntegralService.findList(commonAppIntegral);	
//	CacheConfig.setAppIntegral(CacheConfig.APP_INTEGRAL_INFO, integerList, 0);
//
//	}
  /****
   * @desc :检查接入的菜单的权限
   * @author xudongdong
   * @version 2016-09-06
   * **/
  public static DataResult	checkSysPermssion(HttpServletRequest request){
	    String timestamp =request.getHeader("timestamp");
		String clientid =request.getHeader("clientid");
		String passcode =request.getHeader("passcode");
		String href = request.getRequestURI();//获取请求的地址
		String token = request.getHeader("token");
		logger.debug("获取的用户token是====="+token);
		 if(StringUtils.isNotBlank(clientid)){
			 CommonAppInfo appInfo  = getAppInfo(clientid);  
			 if(appInfo!=null){
				 /***
				 * md5(timestamp + clientid + keyversion + key)
				 ****/
				 String key = appInfo.getSecretKey();//获取秘钥
				    String keyversion = appInfo.getKeyVersion();//获取秘钥版本
				    String md5Passcode = MD5Utils.MD5(timestamp+clientid+keyversion+key);
				    CommonMenu permisonTag =  checkMenucePermit(clientid,href,token);
				    logger.debug("获取的接口地址是"+href);
		            logger.debug("检测接口地址权限的结果是"+permisonTag);
		            logger.debug("获取的平台自带的psscode是"+md5Passcode);
		            if(passcode.equalsIgnoreCase(md5Passcode)){
//		            if(passcode.equalsIgnoreCase(md5Passcode)&&permisonTag!=null){
		          // InterfaceLogUtils.recordAccessHistory(request, permisonTag);
		    		return null;
		    	   }	
			 }
		 }
		 DataResult bean = new DataResult();
		 bean.setMsg("接口权限不足");
		 bean.setStatus(HttpConstant.NO_INTERFACE_AUTHOR);
		 logger.info("APP接口鉴权失败--timestamp"+timestamp);
		 logger.info("APP接口鉴权失败--clientid"+clientid);
		 logger.info("APP接口鉴权失败--passcode"+passcode);
	  return bean;
	  
  }
  
  /***
   * 查看菜单的接入权限
   * ***/	
  public static CommonMenu checkMenucePermit(String clientid,String href,String token){
	  @SuppressWarnings("rawtypes")
	List  list =(List)CacheConfig.getAppAuthorList(clientid);  
	  if(list!=null){
		  for(Object cms:list){
			  CommonMenu cm = (CommonMenu)cms;
			  if(href.contains(cm.getApi())){
				if("1".equals(cm.getIsShow())){
					AppMainUser user = SysUserTokenManager.getTokenTable(token);
					logger.debug("获取的用户信息是===="+user);
					if(user==null){
						return null;
					}
//					/***
//					 * 处理积分逻辑
//					 * **/
//					CommonAppIntegral integral =AppIntegralUtils.getAppIntegral(cm.getId());
//					if(integral!=null){
//						logger.debug("插入积分信息");
//						CommonIntegral commonIntegral = new CommonIntegral();
//						commonIntegral.setClientId(clientid);
//						commonIntegral.setInformation(integral.getName());
//						commonIntegral.setIntegral(integral.getIntegral());
//						commonIntegral.setUserId(user.getId());
//						commonIntegral.setOfficeId(user.getOfficeId());
//						commonIntegralService.save(commonIntegral);
//						try {
//							ApplicationsUtils.managerUserIntegralUtils(user.getId(), integral.getIntegral());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
					
					return cm;
				}else{
					 return cm;
				}  
			  }
		  }
	  }
	 return null; 
  }
  
  
  /****
   * 根据clientId 获取应用信息
   * @author xudongdong
   * @version 2016-09-06
   * ***/
  public static CommonAppInfo getAppInfo(String clientId){
	 if(StringUtils.isNotBlank(clientId)){
		 
	Object object= CacheConfig.getAppInfo(clientId);
	if(object!=null){
		CommonAppInfo   commonInfo = (CommonAppInfo) object; 
		return commonInfo;
	  }
	 }
    return null;
  }
  /**
   * @author xudongdong
   * @version 2016-10-09 
   * @ 将 接口参数放入缓存中
   * 
   * **/
  public static void initMenueParam(){
	  
	  
	  
  }
  
}
