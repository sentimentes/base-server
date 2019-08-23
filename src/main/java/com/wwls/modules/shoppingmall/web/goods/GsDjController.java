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
import com.wwls.modules.shoppingmall.entity.goods.GsDj;
import com.wwls.modules.shoppingmall.service.goods.GsDjService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 积分等级管理Controller
 * @author 雷小明
 * @version 2019-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsDj")
public class GsDjController extends BaseController {

	@Autowired
	private GsDjService gsDjService;
	
	@ModelAttribute
	public GsDj get(@RequestParam(required=false) String id) {
		GsDj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsDjService.get(id);
		}
		if (entity == null){
			entity = new GsDj();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsDj:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsDj gsDj, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsDj.setOffice(UserUtils.getUser().getOffice());
		Page<GsDj> page = gsDjService.findPage(new Page<GsDj>(request, response), gsDj); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsDjList";
	}

	@RequiresPermissions("shoppingmall:goods:gsDj:view")
	@RequestMapping(value = "form")
	public String form(GsDj gsDj, Model model) {
		model.addAttribute("gsDj", gsDj);
		return "modules/shoppingmall/goods/gsDjForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsDj:edit")
	@RequestMapping(value = "save")
	public String save(GsDj gsDj, Model model, RedirectAttributes redirectAttributes) {
		
		if (gsDj.getIsNewRecord()) {
			gsDj.setOffice(gsDj.getCurrentUser().getOffice());
		}
		gsDjService.save(gsDj);
		addMessage(redirectAttributes, "保存积分等级管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsDj/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsDj:edit")
	@RequestMapping(value = "delete")
	public String delete(GsDj gsDj, RedirectAttributes redirectAttributes) {
		gsDjService.delete(gsDj);
		addMessage(redirectAttributes, "删除积分等级管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsDj/?repage";
	}

}