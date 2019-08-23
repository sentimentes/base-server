package com.wwls.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.web.BaseController;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.sys.entity.SysUserAttribute;
import com.wwls.modules.sys.service.SysUserAttributeService;
import com.wwls.modules.sys.utils.LogUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 用户属性配置Controller
 * @author hugang
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserAttribute")
public class SysUserAttributeController extends BaseController {

	@Autowired
	private SysUserAttributeService sysUserAttributeService;
	
	@ModelAttribute
	public SysUserAttribute get(@RequestParam(required=false) String id) {
		SysUserAttribute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserAttributeService.get(id);
		}
		if (entity == null){
			entity = new SysUserAttribute();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysUserAttribute:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserAttribute sysUserAttribute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserAttribute> page = sysUserAttributeService.findPage(new Page<SysUserAttribute>(request, response), sysUserAttribute); 
		model.addAttribute("page", page);
		return "modules/sys/sysUserAttributeList";
	}

	@RequiresPermissions("sys:sysUserAttribute:view")
	@RequestMapping(value = "form")
	public String form(SysUserAttribute sysUserAttribute, Model model) {
		String userId = sysUserAttribute.getUserId();
		sysUserAttribute.setUserId(userId);
		sysUserAttribute.setOffice(UserUtils.getUser().getOffice());
		if (StringUtils.isNotBlank(userId)){
			sysUserAttribute = sysUserAttributeService.get(sysUserAttribute);
		}
		if (sysUserAttribute == null){
			sysUserAttribute = new SysUserAttribute();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("sysUserAttribute", sysUserAttribute);
		return "modules/sys/sysUserAttributeForm";
	}

	
	/**
	 * 用户属性配置
	 * @param sysUserAttribute
	 * @param model
	 * @param redirectAttributes
	 * @param userId
	 * @return
	 */
	@RequiresPermissions("sys:sysUserAttribute:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,SysUserAttribute sysUserAttribute, Model model, RedirectAttributes redirectAttributes,String userId) {
		if (!beanValidator(model, sysUserAttribute)){
			return form(sysUserAttribute, model);
		}
		sysUserAttribute.setOffice(UserUtils.getUser().getOffice());
		
		//参数校验
		//1、异常次数触发锁定次数
		if(sysUserAttribute.getAbnormalTimes()<1 || sysUserAttribute.getAbnormalTimes()>10){
			addMessage(redirectAttributes, "修改用户属性失败，锁定触发次数在1到10");
			return "redirect:"+Global.getAdminPath()+"/sys/sysUserAttribute/form?userId="+sysUserAttribute.getUserId();
		}
		//2、锁定账户的时间，不低于20分钟
		if(sysUserAttribute.getLockTime()<20){
			addMessage(redirectAttributes, "修改用户属性失败，锁定时间至少为20分钟");
			return "redirect:"+Global.getAdminPath()+"/sys/sysUserAttribute/form?userId="+sysUserAttribute.getUserId();
		}
		//3、口令有效期为1到90天
		if(sysUserAttribute.getExpirationDate()<1 || sysUserAttribute.getExpirationDate()>90){
			addMessage(redirectAttributes, "修改用户属性失败，口令有效期为1到90天");
			return "redirect:"+Global.getAdminPath()+"/sys/sysUserAttribute/form?userId="+sysUserAttribute.getUserId();
		}
		String updateBegin = "";//修改前数据
		String updateAfter = "";//修改后数据
		SysUserAttribute sysUserAttributes = sysUserAttributeService.get(userId);
		if(sysUserAttributes!=null){
			updateBegin = "[锁定触发次数]="+sysUserAttributes.getAbnormalTimes()+
					"[锁定时间]="+sysUserAttributes.getLockTime()+
					"[禁止访问开始时间]="+sysUserAttributes.getVisitStart()+
					"[禁止访问结束时间]="+sysUserAttributes.getVisitEnd()+
					"[受限ip]="+sysUserAttributes.getBoundedIp()+
					"[口令有效期]="+sysUserAttributes.getExpirationDate();
		}
		sysUserAttributeService.save(sysUserAttribute);
		updateAfter = "[锁定触发次数]="+sysUserAttribute.getAbnormalTimes()+
				"[锁定时间]="+sysUserAttribute.getLockTime()+
				"[禁止访问开始时间]="+sysUserAttribute.getVisitStart()+
				"[禁止访问结束时间]="+sysUserAttribute.getVisitEnd()+
				"[受限ip]="+sysUserAttribute.getBoundedIp()+
				"[口令有效期]="+sysUserAttribute.getExpirationDate();
		
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-属性配置-修改", updateBegin, updateAfter,"1","5");
		addMessage(redirectAttributes, "保存用户属性配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
	}
	
	@RequiresPermissions("sys:sysUserAttribute:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserAttribute sysUserAttribute, RedirectAttributes redirectAttributes) {
		sysUserAttributeService.delete(sysUserAttribute);
		addMessage(redirectAttributes, "删除用户属性配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserAttribute/?repage";
	}

}