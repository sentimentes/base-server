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
import com.wwls.modules.sys.entity.SysSessionManage;
import com.wwls.modules.sys.service.SysSessionManageService;
import com.wwls.modules.sys.utils.LogUtils;

/**
 * session管理Controller
 * @author hugang
 * @version 2017-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysSessionManage")
public class SysSessionManageController extends BaseController {

	@Autowired
	private SysSessionManageService sysSessionManageService;
	
	@ModelAttribute
	public SysSessionManage get(@RequestParam(required=false) String id) {
		SysSessionManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysSessionManageService.get(id);
			entity.setTimeout(entity.getTimeout()/60000);
		}
		if (entity == null){
			entity = new SysSessionManage();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysSessionManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysSessionManage sysSessionManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysSessionManage> page = sysSessionManageService.findPage(new Page<SysSessionManage>(request, response), sysSessionManage); 
		model.addAttribute("page", page);
		return "modules/sys/sysSessionManageList";
	}

	@RequiresPermissions("sys:sysSessionManage:view")
	@RequestMapping(value = "form")
	public String form(SysSessionManage sysSessionManage, Model model) {
		model.addAttribute("sysSessionManage", sysSessionManage);
		return "modules/sys/sysSessionManageForm";
	}

	@RequiresPermissions("sys:sysSessionManage:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,SysSessionManage sysSessionManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysSessionManage)){
			return form(sysSessionManage, model);
		}
		int timeout = sysSessionManage.getTimeout()*1000*60;
		String id = "1";
		if(timeout<180000 || timeout>7200000){
			addMessage(redirectAttributes, "请输入3到120的正整数");
			return "redirect:"+Global.getAdminPath()+"/sys/sysSessionManage/form?id="+id;
		}
		
		if(sysSessionManage.getOnlineCount()<1 || sysSessionManage.getOnlineCount()>10000){
			addMessage(redirectAttributes, "请输入1到1000的正整数");
			return "redirect:"+Global.getAdminPath()+"/sys/sysSessionManage/form?id="+id;
		}
		
		sysSessionManage.setTimeout(timeout);
		String updateBegin = "";//修改前数据
		String updateAfter = "";//修改后数据
		SysSessionManage sysSessionManages = sysSessionManageService.get("1");
		if(sysSessionManages!=null){
			updateBegin = "[会话结束时间]="+sysSessionManages.getTimeout()+
					"[同时在线人数]="+sysSessionManages.getOnlineCount();
		}
		sysSessionManageService.save(sysSessionManage);
		updateAfter = "[会话结束时间]="+sysSessionManage.getTimeout()+
				"[同时在线人数]="+sysSessionManage.getOnlineCount();
		
		LogUtils.saveLogUser(request,"系统设置-系统设置-会话设置-修改", updateBegin, updateAfter,"1","5");
		addMessage(redirectAttributes, "保存session管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysSessionManage/form?id="+id;
	}
	
	@RequiresPermissions("sys:sysSessionManage:edit")
	@RequestMapping(value = "delete")
	public String delete(SysSessionManage sysSessionManage, RedirectAttributes redirectAttributes) {
		sysSessionManageService.delete(sysSessionManage);
		addMessage(redirectAttributes, "删除session管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysSessionManage/?repage";
	}

}