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
import com.wwls.modules.sys.entity.SysUserRole;
import com.wwls.modules.sys.service.SysUserRoleService;

/**
 * 用户角色关联Controller
 * @author hugang
 * @version 2017-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserRole")
public class SysUserRoleController extends BaseController {

	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@ModelAttribute
	public SysUserRole get(@RequestParam(required=false) String id) {
		SysUserRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserRoleService.get(id);
		}
		if (entity == null){
			entity = new SysUserRole();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysUserRole:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserRole> page = sysUserRoleService.findPage(new Page<SysUserRole>(request, response), sysUserRole); 
		model.addAttribute("page", page);
		return "modules/sys/sysUserRoleList";
	}

	@RequiresPermissions("sys:sysUserRole:view")
	@RequestMapping(value = "form")
	public String form(SysUserRole sysUserRole, Model model) {
		model.addAttribute("sysUserRole", sysUserRole);
		return "modules/sys/sysUserRoleForm";
	}

	@RequiresPermissions("sys:sysUserRole:edit")
	@RequestMapping(value = "save")
	public String save(SysUserRole sysUserRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserRole)){
			return form(sysUserRole, model);
		}
		sysUserRoleService.save(sysUserRole);
		addMessage(redirectAttributes, "保存用户角色关联成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserRole/?repage";
	}
	
	@RequiresPermissions("sys:sysUserRole:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserRole sysUserRole, RedirectAttributes redirectAttributes) {
		sysUserRoleService.delete(sysUserRole);
		addMessage(redirectAttributes, "删除用户角色关联成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserRole/?repage";
	}

}