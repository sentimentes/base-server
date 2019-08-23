package com.wwls.common.filter;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wwls.common.config.Global;
import com.wwls.common.constant.DataResult;
import com.wwls.common.utils.JedisUtils;
import com.wwls.modules.application.entity.CommonMenu;
import com.wwls.modules.application.utils.AppAccessAuthorUtils;
import com.wwls.modules.application.utils.ApplicationsUtils;

/**
 * @author hugang 
 * @version 2018-07-17 
 * @ 描述： 接口地址中间加了一层映射，这样在改变后台接口的时候不用变动对外开放的接口地址
 */
public class AppRouterMapper implements Filter {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
    public AppRouterMapper() {
        
    } 

	/**
	 * @see Filter#destroy()
	 */                                 
	public void destroy() {
	    
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String href = hrequest.getRequestURI();// 获取请求的地址
		logger.debug("获取的请求地址是====="+href);
//		if(href.contains("CallBack")){
//			logger.debug("拦截地址正常放行"+href);
//			chain.doFilter(request, response);
//			return;
//		}
		//绿色通道
//		if(href.contains("api/")){
//			logger.debug("拦截地址正常放行"+href);
//			chain.doFilter(request, response);
//			return;
//		}
	if(href.contains("api.")){
		String routerAPI=  href.substring(href.indexOf("api."));
		if(StringUtils.isNotBlank(routerAPI)){
			routerAPI = routerAPI.replace("/", "");
			/***
			 * 先从缓存里面取数据，如果取不到，则从数据库里面查询
			 * **/
 			  String destRout= JedisUtils.get(routerAPI);
 			 if(destRout == null){
				 logger.debug("路径映射的MPC为空");
				 chain.doFilter(hrequest, response);
			 }else {
					DataResult bean = 	AppAccessAuthorUtils.checkSysPermssion(hrequest);
				 if(bean!=null){
				  	  logger.debug("请求已经到达，进行APP接口鉴权，失败");
				  	 request.getRequestDispatcher("/a/application/noauthor/accessdeny").forward(request, response);   //转发到new.jsp
				  	 logger.debug("在Filter中");
				  	    return;
				  	}
				 logger.debug("从缓存中获取的的路径是===="+destRout);
				 request.getRequestDispatcher(destRout).forward(request, response);
				 logger.debug("推送成功");
				 return ;
			 } 
		    }
		}
//		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// 启动队列---获取队列数据

		logger.info("获取的系统的编码格式是====" + System.getProperty("file.encoding"));
		if ("1".equals(Global.getConfig("cached.param.init"))) {
			List<CommonMenu> menuList = ApplicationsUtils.getMenueList();
			for (CommonMenu commonMenu : menuList) {
				String api = commonMenu.getApi();
				String href = commonMenu.getHref();
				JedisUtils.set(api, href, 0);
			}
		}
	 
        if("1".equals(Global.getConfig("cached.authorty.init"))){
        	AppAccessAuthorUtils.initAppAccessAuthor();//加载权限信息
        	logger.info("加载完毕用户信息==");
	    }
        
        /*if("1".equals(Global.getConfig("cached.pay.init"))){
        	AppAccessAuthorUtils.initPay();//加载支付列表信息
         }*/
//        if("1".equals(Global.getConfig("cached.integral.init"))){
//        	AppAccessAuthorUtils.initIntegral();//加载积分信息字典
//        }
		
//		logger.info("初始化信息成功[用户信息初始化][权限信息初始化][App信息初始化][初始化接口参数完毕]"); 
//		//1.处理订单日志队列监听
//		if("1".equals(Global.getConfig("activity.MQ.MogonDBTradeOrderLog"))){
//			 JMSHandler order = new OrderJMSHandler();
//			 	JMSReciverListener jMSReciver = new JMSReciverListener(MongoTableConstant.MogonDBTradeOrderLog,order);
//			 	                   jMSReciver.start();
//			 	                  logger.info("订单日志消息队列监听启动成功");
//			
//		}
//	   if("1".equals(Global.getConfig("activity.MQ.CommonIntegral"))){
//			
//		   JMSHandler integral = new IntegralJMSHandler();
//			 JMSReciverListener jMSReciver1 = new JMSReciverListener(MongoTableConstant.CommonIntegral,integral);
//			                    jMSReciver1.start();
//			                    logger.info("积分消息队列监听启动成功");
//		}
//	   if("1".equals(Global.getConfig("activity.MQ.MQProductUpDownIdS"))){
//		
//			JMSHandler productUpDown = new ProductUpShelfHandler();
//			JMSReciverListener jMSReciver2 = new JMSReciverListener(MongoTableConstant.MQProductUpDownIdS,productUpDown);
//			                    jMSReciver2.start();
//			                    logger.info("商品刷缓存消息队列监听启动成功");
//	    }
//	   
//	   //缓存管理
//		if ("1".equals(Global.getConfig("activity.MQ.CacheManage"))) {
//			JMSHandler cacheManage = new CacheManageHandler();
//			JMSReciverListener jMSReciver3 = new JMSReciverListener(MongoTableConstant.CacheManage, cacheManage);
//			jMSReciver3.start();
//			logger.info("缓存管理消息队列监听启动成功");
//		}
		
		
		//定时器管理，处理京东没有预占库存成功的订单
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				//initTimer();// 加载定时器
				logger.info("定时器已启动");
			}
		}, 1200000, 1200000);// 启动后延迟20分钟启动，每隔20分钟刷一次
		
	}
	
	
	/**
	 * 初始化定时器
	 */
	/*public static void initTimer(){
		//加载京东未确认预占库存订单接口定时器
		JingDongUtils.timingHandle();
	}*/

}
