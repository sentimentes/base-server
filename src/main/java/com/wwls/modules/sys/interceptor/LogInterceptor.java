package com.wwls.modules.sys.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.wwls.common.service.BaseService;
import com.wwls.modules.sys.utils.LogUtils;

/**
 * 日志拦截器
 * @version 2014-8-19
 */
public class LogInterceptor extends BaseService implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
////	    获取request的cookie
//	    Cookie[] cookies = request.getCookies();
//	    if (null==cookies) {
//	      System.out.println("没有cookie==============");
//	    } else {
////	      遍历cookie如果找到登录状态则返回true执行原来controller的方法
//	      for(Cookie cookie : cookies){
//	    	  //Session session1 = UserUtils.getSession();
//	    	  System.out.println("cookie1===="+cookie.getName());
//	    	  System.out.println("cookie3===="+cookie.getValue());
//	        if(cookie.getName().equals("jeesite.session.id")){
//	          //System.out.println("cookie2"+cookie.getName());
//	        	cookie.setValue(IdGen.uuid());
//	          return true;
//	        }
//	        System.out.println("cookie4===="+cookie.getValue());
//	      }
//	    }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		logger.info("进来的链接地址2==="+request.getRequestURI());
		if (modelAndView != null){
			logger.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("进来的链接地址3==="+request.getRequestURI());
		// 保存日志
		LogUtils.saveLog(request, handler, ex,null);
		
		// 打印JVM信息。
		/*if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间  
	        logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
	        		new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024, 
					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024); 
		}*/
		
	}

}
