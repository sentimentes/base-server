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
import com.wwls.modules.sys.entity.SysRoleMenu;
import com.wwls.modules.sys.service.SysRoleMenuService;

/**
 * 角色菜单管理Controller
 * @author hugang
 * @version 2017-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysRoleMenu")
public class SysRoleMenuController extends BaseController {

	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@ModelAttribute
	public SysRoleMenu get(@RequestParam(required=false) String id) {
		SysRoleMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysRoleMenuService.get(id);
		}
		if (entity == null){
			entity = new SysRoleMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysRoleMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysRoleMenu sysRoleMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysRoleMenu> page = sysRoleMenuService.findPage(new Page<SysRoleMenu>(request, response), sysRoleMenu); 
		model.addAttribute("page", page);
		return "modules/sys/sysRoleMenuList";
	}

	@RequiresPermissions("sys:sysRoleMenu:view")
	@RequestMapping(value = "form")
	public String form(SysRoleMenu sysRoleMenu, Model model) {
		model.addAttribute("sysRoleMenu", sysRoleMenu);
		return "modules/sys/sysRoleMenuForm";
	}

	@RequiresPermissions("sys:sysRoleMenu:edit")
	@RequestMapping(value = "save")
	public String save(SysRoleMenu sysRoleMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysRoleMenu)){
			return form(sysRoleMenu, model);
		}
		sysRoleMenuService.save(sysRoleMenu);
		addMessage(redirectAttributes, "保存角色菜单管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysRoleMenu/?repage";
	}
	
	@RequiresPermissions("sys:sysRoleMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(SysRoleMenu sysRoleMenu, RedirectAttributes redirectAttributes) {
		sysRoleMenuService.delete(sysRoleMenu);
		addMessage(redirectAttributes, "删除角色菜单管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysRoleMenu/?repage";
	}

}