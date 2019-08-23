package com.wwls.common.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *解决跨域请求的问题
 */
public class CrossFileFilter implements Filter {
	protected static Logger logger = LoggerFactory.getLogger(CrossFileFilter.class);
	
	/**
     * Default constructor. 
     */
    public CrossFileFilter() {
       
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
		
		
		
//		HttpServletResponse httpResponse = (HttpServletResponse) response;  
//	    httpResponse.setHeader("Access-Control-Allow-Origin", "*");  
//	    httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");  
//	    httpResponse.setHeader("Access-Control-Allow-Methods"," GET, POST, OPTIONS");
//	    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");  
		chain.doFilter(request, response);
		 //chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

//		// 定时器管理
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			public void run() {
//				initTimer();// 加载定时器
//				logger.info("定时器已启动");
//			}
//		}, 180000, 180000);// 启动后延迟3分钟启动，每隔3分钟刷一次
		
		
	}

	
}
