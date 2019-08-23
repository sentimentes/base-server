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
import com.wwls.modules.application.entity.CommonAppRole;
import com.wwls.modules.application.entity.CommonRole;
import com.wwls.modules.application.service.CommonAppRoleService;
import com.wwls.modules.application.service.CommonRoleService;

/**
 * 应用角色关联管理Controller
 * 
 * @author mengyanan
 * @version 2016-06-24
 */
@SessionAttributes("appId")
@Controller
@RequestMapping(value = "${adminPath}/application/commonAppRole")
public class CommonAppRoleController extends BaseController {

	@Autowired
	private CommonAppRoleService commonAppRoleService;

	@Autowired
	private CommonRoleService commonRoleService;
	@ModelAttribute
	public CommonAppRole get(@RequestParam(required = false) String id) {
		CommonAppRole entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = commonAppRoleService.get(id);
		}
		if (entity == null) {
			entity = new CommonAppRole();
		}
		return entity;
	}
	
	@RequiresPermissions("application:commonAppRole:view")
	@RequestMapping(value = { "index" })
	public String index(CommonAppRole commonAppRole, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		Page<CommonAppRole> page = commonAppRoleService.findAlreadyPage(new Page<CommonAppRole>(request, response),
				commonAppRole);
		model.addAttribute("appId", commonAppRole.getAppId());
		model.addAttribute("page", page);
		return "modules/application/commonAppRoleAlreadyAllocatedList";
	}

	@RequiresPermissions("application:commonAppRole:view")
	@RequestMapping(value = { "list" })
	public String list(CommonAppRole commonAppRole, HttpServletRequest request, HttpServletResponse response,
			Model model,@ModelAttribute("appId")String appId) {
		commonAppRole.setAppId(appId);
		Page<CommonAppRole> page = commonAppRoleService.findAlreadyPage(new Page<CommonAppRole>(request, response),
				commonAppRole);
		 
		model.addAttribute("page", page);
		return "modules/application/commonAppRoleAlreadyAllocatedList";
	}

	@RequiresPermissions("application:commonAppRole:view")
	@RequestMapping(value = "form")
	public String form(CommonAppRole commonAppRole, Model model) {
		model.addAttribute("commonAppRole", commonAppRole);
		return "modules/application/commonAppRoleForm";
	}

	@RequiresPermissions("application:commonAppRole:edit")
	@RequestMapping(value = "save")
	public String save(CommonAppRole commonAppRole, Model model, RedirectAttributes redirectAttributes,@ModelAttribute("appId")String appId) {
		commonAppRole.setAppId(appId);
		commonAppRoleService.save(commonAppRole);
		addMessage(redirectAttributes, "保存应用角色关联管理成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonAppRole/list?repage";
	}

	@RequiresPermissions("application:commonAppRole:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonAppRole commonAppRole, RedirectAttributes redirectAttributes) {
		commonAppRoleService.delete(commonAppRole);
		addMessage(redirectAttributes, "删除应用角色关联管理成功");
		return "redirect:" + Global.getAdminPath() + "/application/commonAppRole/?repage";
	}

	
	 

	
	// 尚未分配的接口列表
	@RequiresPermissions("application:commonRole:view")
	@RequestMapping(value = { "notYeList" })
	public String notYeList(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute("appId") String appId) {
		CommonAppRole commonapprole = new CommonAppRole();
		commonapprole.setAppId(appId);
		
		/*****知道哪些是已经分配的****/
	    List<CommonAppRole> list =	commonAppRoleService.findList(commonapprole);
	    StringBuffer sb = new StringBuffer();
	    String roleIds=null;
	    CommonRole commonRole = new CommonRole();
	    if(list!=null && list.size()>0){
	    	for( CommonAppRole approle:list){
	    		sb.append("'");
	    		sb.append(approle.getRoleId());
	    		sb.append("'");
	    		sb.append(",");
		    }
	    	if(sb.toString().contains(",")){
	    		roleIds = sb.substring(0,sb.lastIndexOf(","));
	    	}
	    }
	   if( StringUtils.isEmpty(roleIds)){
		   commonRole.setRoleIds("'xxxxxxxxxx'");
	   }else{
		   commonRole.setRoleIds(roleIds);
	   }
		Page<CommonRole> page = commonRoleService.findNotYetPage(new Page<CommonRole>(request, response),
				commonRole);
		model.addAttribute("page", page);
		return "modules/application/commonAppRoleNotYetList";
	}

}