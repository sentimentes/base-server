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
import com.wwls.modules.shoppingmall.entity.goods.GsType;
import com.wwls.modules.shoppingmall.service.goods.GsTypeService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商品分类管理Controller
 * @author leixiaoming
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsType")
public class GsTypeController extends BaseController {

	@Autowired
	private GsTypeService gsTypeService;
	
	@ModelAttribute
	public GsType get(@RequestParam(required=false) String id) {
		GsType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsTypeService.get(id);
		}
		if (entity == null){
			entity = new GsType();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsType:view")
	@RequestMapping(value = {"list", ""})
	public String list(String vaId,GsType gsType, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsType.setOffice(UserUtils.getUser().getOffice());
		Page<GsType> page = gsTypeService.findPage(new Page<GsType>(request, response), gsType); 
		model.addAttribute("page", page);
		model.addAttribute("vaId", vaId);
		return "modules/shoppingmall/goods/gsTypeList";
	}

	@RequiresPermissions("shoppingmall:goods:gsType:view")
	@RequestMapping(value = "form")
	public String form(GsType gsType, Model model) {
		model.addAttribute("gsType", gsType);
		return "modules/shoppingmall/goods/gsTypeForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsType:edit")
	@RequestMapping(value = "save")
	public String save(GsType gsType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsType)){
			return form(gsType, model);
		}
		if (gsType.getIsNewRecord()) {
			gsType.setOffice(gsType.getCurrentUser().getOffice());
		}
		gsTypeService.save(gsType);
		addMessage(redirectAttributes, "保存商品分类管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsType/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsType:edit")
	@RequestMapping(value = "delete")
	public String delete(String vaId,GsType gsType, RedirectAttributes redirectAttributes) {
		gsTypeService.delete(gsType);
		addMessage(redirectAttributes, "删除商品分类管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsType/?repage&vaId="+vaId;
	}

}