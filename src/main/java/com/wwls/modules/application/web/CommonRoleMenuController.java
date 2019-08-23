package com.wwls.modules.application.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.application.entity.CommonMenu;
 import com.wwls.modules.application.entity.CommonRoleMenu;
import com.wwls.modules.application.service.CommonMenuService;
import com.wwls.modules.application.service.CommonRoleMenuService;
 
/**
 * 菜单角色关联Controller
 * 
 * @author mengyann
 * @version 2016-06-24
 */
@SessionAttributes("roleId")

@Controller
@RequestMapping(value = "${adminPath}/application/commonRoleMenu")
public class CommonRoleMenuController extends BaseController {

	@Autowired
	private CommonRoleMenuService commonRoleMenuService;
	@Autowired
	private CommonMenuService commonMenuService;

	@ModelAttribute
	public CommonRoleMenu get(@RequestParam(required = false) String id) {
		CommonRoleMenu entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = commonRoleMenuService.get(id);
		}
		if (entity == null) {
			entity = new CommonRoleMenu();
		}
		return entity;
	}

	// 已经分配
		@RequiresPermissions("application:commonRoleMenu:view")
		@RequestMapping(value = { "index" })
		public String index(CommonRoleMenu commonRoleMenu, HttpServletRequest request, HttpServletResponse response,
				Model model) {

			Page<CommonRoleMenu> page = commonRoleMenuService.findAlreadyPage(new Page<CommonRoleMenu>(request, response),
					commonRoleMenu);
			model.addAttribute("roleId", commonRoleMenu.getRoleId());
			model.addAttribute("page", page);
			return "modules/application/commonMenuRoleAlready";
		}
	
	// 已经分配
	@RequiresPermissions("application:commonRoleMenu:view")
	@RequestMapping(value = { "list" })
	public String list(CommonRoleMenu commonRoleMenu, HttpServletRequest request, HttpServletResponse response,
			Model model,@ModelAttribute("roleId") String roleId) {
		commonRoleMenu.setRoleId(roleId);
		Page<CommonRoleMenu> page = commonRoleMenuService.findAlreadyPage(new Page<CommonRoleMenu>(request, response),
				commonRoleMenu);
		 
		model.addAttribute("page", page);
		return "modules/application/commonMenuRoleAlready";
	}

	@RequiresPermissions("application:commonRoleMenu:view")
	@RequestMapping(value = "form")
	public String form(CommonRoleMenu commonRoleMenu, Model model) {
		model.addAttribute("commonRoleMenu", commonRoleMenu);
		return "modules/application/commonRoleMenuForm";
	}

	// 尚未分配的接口列表
	@RequiresPermissions("application:commonRoleMenu:view")
	@RequestMapping(value = { "notYeList" })
	public String notYeList(CommonMenu commonMenu,HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("roleId") String roleId) {
		CommonRoleMenu commonRoleMenu = new CommonRoleMenu();
		commonRoleMenu.setRoleId(roleId);

		/***** 知道哪些是已经分配的 菜单 ****/
		List<CommonRoleMenu> list = commonRoleMenuService.findList(commonRoleMenu);
		StringBuffer sb = new StringBuffer();
		String menuIds = null;

		if (list != null && list.size() > 0) {
			for (CommonRoleMenu commonRoleMenus : list) {
				sb.append("'");
				sb.append(commonRoleMenus.getMenuId());
				sb.append("'");
				sb.append(",");
			}
		}
		if (sb.toString().contains(",")) {
			logger.debug("获取的字符串是==="+sb.toString());
			logger.debug("截取的字符串是==="+sb.substring(0, sb.lastIndexOf(",")));
			menuIds = sb.substring(0, sb.lastIndexOf(","));
		}
		logger.debug("====参数是======"+menuIds+"===========");
		if ( menuIds==null) {
			commonMenu.setMenuIds ("'xxxxxxxxxx'");
		} else {
			commonMenu.setMenuIds(menuIds);
		}
        logger.debug("获取的过滤参数是====="+commonMenu.getMenuIds());
		Page<CommonMenu> page = commonMenuService.findNotYetPage(new Page<CommonMenu>(request, response),
				commonMenu);
		model.addAttribute("page", page);
		                             
		return "modules/application/commonMenuRoleNotYetList";
	}

	@RequiresPermissions("application:commonRoleMenu:edit")
	@RequestMapping(value = "save")
	public String save(CommonRoleMenu commonRoleMenu, Model model, RedirectAttributes redirectAttributes,
			@ModelAttribute("roleId") String roleId) {
		commonRoleMenu.setRoleId(roleId);
		if (!beanValidator(model, commonRoleMenu)) {
			return form(commonRoleMenu, model);
		}
		commonRoleMenuService.save(commonRoleMenu);
		addMessage(redirectAttributes, "保存菜单角色关联管理成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonRoleMenu/list?repage";
	}

	@RequiresPermissions("application:commonRoleMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonRoleMenu commonRoleMenu, RedirectAttributes redirectAttributes) {
		commonRoleMenuService.delete(commonRoleMenu);
		addMessage(redirectAttributes, "删除菜单角色关联管理成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonRoleMenu/list?repage";
	}

}