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
import com.wwls.modules.shoppingmall.entity.goods.GsTjgs;
import com.wwls.modules.shoppingmall.service.goods.GsTjgsService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 特价商品绑定Controller
 * @author leixiaoming
 * @version 2019-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsTjgs")
public class GsTjgsController extends BaseController {

	@Autowired
	private GsTjgsService gsTjgsService;
	
	@ModelAttribute
	public GsTjgs get(@RequestParam(required=false) String id) {
		GsTjgs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsTjgsService.get(id);
		}
		if (entity == null){
			entity = new GsTjgs();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsTjgs:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsTjgs gsTjgs, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsTjgs.setOffice(UserUtils.getUser().getOffice());
		Page<GsTjgs> page = gsTjgsService.findPage(new Page<GsTjgs>(request, response), gsTjgs); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsTjgsList";
	}

	@RequiresPermissions("shoppingmall:goods:gsTjgs:view")
	@RequestMapping(value = "form")
	public String form(GsTjgs gsTjgs, Model model) {
		model.addAttribute("gsTjgs", gsTjgs);
		return "modules/shoppingmall/goods/gsTjgsForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsTjgs:edit")
	@RequestMapping(value = "save")
	public String save(GsTjgs gsTjgs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsTjgs)){
			return form(gsTjgs, model);
		}
		if (gsTjgs.getIsNewRecord()) {
			gsTjgs.setOffice(gsTjgs.getCurrentUser().getOffice());
		}
		gsTjgsService.save(gsTjgs);
		addMessage(redirectAttributes, "保存特价商品绑定成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsTjgs/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsTjgs:edit")
	@RequestMapping(value = "delete")
	public String delete(String tjgsId,GsTjgs gsTjgs, RedirectAttributes redirectAttributes) {
		gsTjgsService.delete(gsTjgs);
		addMessage(redirectAttributes, "删除特价商品绑定成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsTjgs/?repage&tjgsId="+tjgsId;
	}

}