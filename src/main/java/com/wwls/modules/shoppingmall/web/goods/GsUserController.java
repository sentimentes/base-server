package com.wwls.modules.shoppingmall.web.goods;

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
import com.wwls.modules.shoppingmall.entity.goods.GsUser;
import com.wwls.modules.shoppingmall.service.goods.GsUserService;

/**
 * 用户管理Controller
 * @author leixiaoming
 * @version 2019-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsUser")
public class GsUserController extends BaseController {

	@Autowired
	private GsUserService gsUserService;
	
	@ModelAttribute
	public GsUser get(@RequestParam(required=false) String id) {
		GsUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsUserService.get(id);
		}
		if (entity == null){
			entity = new GsUser();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsUser gsUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GsUser> page = gsUserService.findPage(new Page<GsUser>(request, response), gsUser); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsUserList";
	}

	@RequiresPermissions("shoppingmall:goods:gsUser:view")
	@RequestMapping(value = "form")
	public String form(GsUser gsUser, Model model) {
		model.addAttribute("gsUser", gsUser);
		return "modules/shoppingmall/goods/gsUserForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsUser:edit")
	@RequestMapping(value = "save")
	public String save(GsUser gsUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsUser)){
			return form(gsUser, model);
		}
		if (gsUser.getIsNewRecord()) {
			gsUser.setOffice(gsUser.getCurrentUser().getOffice());
		}
		gsUserService.save(gsUser);
		addMessage(redirectAttributes, "保存用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsUser/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsUser:edit")
	@RequestMapping(value = "delete")
	public String delete(GsUser gsUser, RedirectAttributes redirectAttributes) {
		gsUserService.delete(gsUser);
		addMessage(redirectAttributes, "删除用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsUser/?repage";
	}

}