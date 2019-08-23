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
import com.wwls.common.web.BaseController;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.application.entity.CommonMenu;
import com.wwls.modules.application.service.CommonMenuService;

/**
 * 应用菜单Controller
 * @author mengyanan
 * @version 2016-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/application/commonMenu")
public class CommonMenuController extends BaseController {

	@Autowired
	private CommonMenuService commonMenuService;
	
	@ModelAttribute
	public CommonMenu get(@RequestParam(required=false) String id) {
		CommonMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commonMenuService.get(id);
		}
		if (entity == null){
			entity = new CommonMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("application:commonMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommonMenu commonMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommonMenu> page = commonMenuService.findPage(new Page<CommonMenu>(request, response), commonMenu); 
		model.addAttribute("page", page);
		return "modules/application/commonMenuList";
	}

	@RequiresPermissions("application:commonMenu:view")
	@RequestMapping(value = "form")
	public String form(CommonMenu commonMenu, Model model) {
		model.addAttribute("commonMenu", commonMenu);
		return "modules/application/commonMenuForm";
	}

	@RequiresPermissions("application:commonMenu:edit")
	@RequestMapping(value = "save")
	public String save(CommonMenu commonMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commonMenu)){
			return form(commonMenu, model);
		}
		commonMenuService.save(commonMenu);
		addMessage(redirectAttributes, "保存应用菜单成功");
		return "redirect:"+Global.getAdminPath()+"/application/commonMenu/?repage";
	}
	
	@RequiresPermissions("application:commonMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonMenu commonMenu, RedirectAttributes redirectAttributes) {
		commonMenuService.delete(commonMenu);
		addMessage(redirectAttributes, "删除应用菜单成功");
		return "redirect:"+Global.getAdminPath()+"/application/commonMenu/?repage";
	}

}