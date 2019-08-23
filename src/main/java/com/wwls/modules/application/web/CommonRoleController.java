package com.wwls.modules.application.web;

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
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.application.entity.CommonRole;
import com.wwls.modules.application.service.CommonRoleService;

/**
 * 应用角色Controller
 * 
 * @author xudongdong
 * @version 2016-06-24
 */
 @Controller
@RequestMapping(value = "${adminPath}/application/commonRole")
public class CommonRoleController extends BaseController {

	@Autowired
	private CommonRoleService commonRoleService;

	@ModelAttribute
	public CommonRole get(@RequestParam(required = false) String id) {
		CommonRole entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = commonRoleService.get(id);
		}
		if (entity == null) {
			entity = new CommonRole();
		}
		return entity;
	}

	@RequiresPermissions("application:commonRole:view")
	@RequestMapping(value = { "list", "" })
	public String list(CommonRole commonRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		Page<CommonRole> page = commonRoleService.findPage(new Page<CommonRole>(request, response), commonRole);
		model.addAttribute("page", page);
		return "modules/application/commonRoleList";
	}

	@RequiresPermissions("application:commonRole:view")
	@RequestMapping(value = "form")
	public String form(CommonRole commonRole, Model model ) {
 		model.addAttribute("commonRole", commonRole);
		return "modules/application/commonRoleForm";
	}

	@RequiresPermissions("application:commonRole:view")
	@RequestMapping(value = "role")
	public String role(CommonRole commonRole, Model model) {
		model.addAttribute("id", commonRole.getId());
		model.addAttribute("commonRole", commonRole);
		return "modules/application/commonRoleList";
	}

	@RequiresPermissions("application:commonRole:edit")
	@RequestMapping(value = "save")
	public String save(CommonRole commonRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commonRole)) {
			return form(commonRole, model);
		}
		
		commonRoleService.save(commonRole);
		addMessage(redirectAttributes, "保存应用角色成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonRole/?repage";
	}

	@RequiresPermissions("application:commonRole:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonRole commonRole, RedirectAttributes redirectAttributes) {
		commonRoleService.delete(commonRole);
		addMessage(redirectAttributes, "删除应用角色成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonRole/?repage";
	}

}