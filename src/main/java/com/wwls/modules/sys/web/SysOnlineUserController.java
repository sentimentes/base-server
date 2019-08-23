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
import com.wwls.modules.sys.entity.SysOnlineUser;
import com.wwls.modules.sys.service.SysOnlineUserService;

/**
 * 在线用户管理Controller
 * @author hugang
 * @version 2017-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOnlineUser")
public class SysOnlineUserController extends BaseController {

	@Autowired
	private SysOnlineUserService sysOnlineUserService;
	
	@ModelAttribute
	public SysOnlineUser get(@RequestParam(required=false) String id) {
		SysOnlineUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOnlineUserService.get(id);
		}
		if (entity == null){
			entity = new SysOnlineUser();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysOnlineUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOnlineUser sysOnlineUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOnlineUser> page = sysOnlineUserService.findPage(new Page<SysOnlineUser>(request, response), sysOnlineUser); 
		model.addAttribute("page", page);
		return "modules/sys/sysOnlineUserList";
	}

	@RequiresPermissions("sys:sysOnlineUser:view")
	@RequestMapping(value = "form")
	public String form(SysOnlineUser sysOnlineUser, Model model) {
		model.addAttribute("sysOnlineUser", sysOnlineUser);
		return "modules/sys/sysOnlineUserForm";
	}

	@RequiresPermissions("sys:sysOnlineUser:edit")
	@RequestMapping(value = "save")
	public String save(SysOnlineUser sysOnlineUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOnlineUser)){
			return form(sysOnlineUser, model);
		}
		sysOnlineUserService.save(sysOnlineUser);
		addMessage(redirectAttributes, "保存在线用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOnlineUser/?repage";
	}
	
	@RequiresPermissions("sys:sysOnlineUser:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOnlineUser sysOnlineUser, RedirectAttributes redirectAttributes) {
		sysOnlineUserService.delete(sysOnlineUser);
		addMessage(redirectAttributes, "删除在线用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOnlineUser/?repage";
	}

}