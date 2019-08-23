package com.wwls.common.web;

 import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wwls.common.constant.DataResult;
import com.wwls.common.constant.HttpConstant;
import com.wwls.common.web.BaseController;

 
@Controller
@RequestMapping(value = "${adminPath}/error")
public class ErrorPageController extends BaseController {
	
	@RequestMapping(value = {"errorPage404"})
	@ResponseBody
	public DataResult errorPage404(){
		DataResult responseBean = new DataResult(); 
		     responseBean.setMsg("您访问的地址不存在");
        	responseBean.setStatus(HttpConstant.ERROR404);		 	
		 
		return responseBean;
	}
	
	
	@RequestMapping(value = {"errorPage500"})
	@ResponseBody
	public DataResult errorPage500(){
		DataResult responseBean = new DataResult(); 
			responseBean.setMsg("系统出错了，请您联系系统管理员");
	        	responseBean.setStatus(HttpConstant.ERROR500);
		return responseBean;
	}
	
	@RequestMapping(value = {"errorPage400"})
	@ResponseBody
	public DataResult errorPage400(){
		DataResult responseBean = new DataResult(); 
			responseBean.setMsg("网络出错了，请您联系系统管理员");
	        	responseBean.setStatus(HttpConstant.ERROR400);
		return responseBean;
	}
	

}
