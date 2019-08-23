package com.wwls.modules.sys.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.wwls.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		System.out.println("----------获取系统的编码是===--"+System.getProperty("file.encoding"));
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}

		return super.initWebApplicationContext(servletContext);
	}
}
