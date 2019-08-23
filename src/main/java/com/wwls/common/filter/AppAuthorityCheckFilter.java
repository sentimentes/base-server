package com.wwls.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wwls.common.config.Global;
import com.wwls.common.constant.DataResult;
import com.wwls.modules.application.utils.AppAccessAuthorUtils;

/****
 * 此类暂时不启用
 * @version 2016-09-05
 * 通过appRouterMapper来处理逻辑了
 * ***/
public class AppAuthorityCheckFilter implements Filter{
	protected Logger logger = LoggerFactory.getLogger(AppAuthorityCheckFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest hrequest = (HttpServletRequest)request;
		String href = hrequest.getRequestURI();//获取请求的地址
		logger.debug("获取的请求URL是"+href);
	     if(href.contains("FileUploadServlet")){
			logger.debug("文件上传接口调用");
			chain.doFilter(request, response);
		   }else if(href.contains("application/user/luLoginCallBack")){//登录
			   chain.doFilter(request, response);
		   }else if(href.contains("/application/user/registerUser/luRegistCallBack")){//注册
			   chain.doFilter(request, response);
		   }
	      if(href.contains(Global.getApiPath())){
	    	  logger.debug("开始进行接口鉴权，获取的参数信息是"+hrequest.getHeaderNames());
	  		DataResult bean = 	AppAccessAuthorUtils.checkSysPermssion(hrequest);
	  	   if(bean!=null){
	  			logger.debug("请求已经到达，进行APP接口鉴权，失败");
	  			request.getRequestDispatcher("/a/application/noauthor/accessdeny").forward(request, response);   //转发到new.jsp
	  			logger.debug("在Filter中");
	  		    return;
	  		}else{
	  			chain.doFilter(request, response);
	  		} 
	    	  
	      }else{
	    	  chain.doFilter(request, response);  
	      }
	     chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
		
	}
}
