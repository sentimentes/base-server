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
import com.wwls.modules.sys.entity.SysPwdManage;
import com.wwls.modules.sys.service.SysPwdManageService;

/**
 * 用户密码管理Controller
 * @author hugang
 * @version 2017-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysPwdManage")
public class SysPwdManageController extends BaseController {

	@Autowired
	private SysPwdManageService sysPwdManageService;
	
	@ModelAttribute
	public SysPwdManage get(@RequestParam(required=false) String id) {
		SysPwdManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysPwdManageService.get(id);
		}
		if (entity == null){
			entity = new SysPwdManage();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysPwdManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysPwdManage sysPwdManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysPwdManage> page = sysPwdManageService.findPage(new Page<SysPwdManage>(request, response), sysPwdManage); 
		model.addAttribute("page", page);
		return "modules/sys/sysPwdManageList";
	}

	@RequiresPermissions("sys:sysPwdManage:view")
	@RequestMapping(value = "form")
	public String form(SysPwdManage sysPwdManage, Model model) {
		model.addAttribute("sysPwdManage", sysPwdManage);
		return "modules/sys/sysPwdManageForm";
	}

	@RequiresPermissions("sys:sysPwdManage:edit")
	@RequestMapping(value = "save")
	public String save(SysPwdManage sysPwdManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysPwdManage)){
			return form(sysPwdManage, model);
		}
		sysPwdManageService.save(sysPwdManage);
		addMessage(redirectAttributes, "保存用户密码管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysPwdManage/?repage";
	}
	
	@RequiresPermissions("sys:sysPwdManage:edit")
	@RequestMapping(value = "delete")
	public String delete(SysPwdManage sysPwdManage, RedirectAttributes redirectAttributes) {
		sysPwdManageService.delete(sysPwdManage);
		addMessage(redirectAttributes, "删除用户密码管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysPwdManage/?repage";
	}

}