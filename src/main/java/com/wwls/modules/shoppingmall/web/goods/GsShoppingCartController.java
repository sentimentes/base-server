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
import com.wwls.modules.shoppingmall.entity.goods.GsShoppingCart;
import com.wwls.modules.shoppingmall.service.goods.GsShoppingCartService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 购物车管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsShoppingCart")
public class GsShoppingCartController extends BaseController {

	@Autowired
	private GsShoppingCartService gsShoppingCartService;
	
	@ModelAttribute
	public GsShoppingCart get(@RequestParam(required=false) String id) {
		GsShoppingCart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsShoppingCartService.get(id);
		}
		if (entity == null){
			entity = new GsShoppingCart();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsShoppingCart:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsShoppingCart gsShoppingCart, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsShoppingCart.setOffice(UserUtils.getUser().getOffice());
		Page<GsShoppingCart> page = gsShoppingCartService.findPage(new Page<GsShoppingCart>(request, response), gsShoppingCart); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsShoppingCartList";
	}

	@RequiresPermissions("shoppingmall:goods:gsShoppingCart:view")
	@RequestMapping(value = "form")
	public String form(GsShoppingCart gsShoppingCart, Model model) {
		model.addAttribute("gsShoppingCart", gsShoppingCart);
		return "modules/shoppingmall/goods/gsShoppingCartForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsShoppingCart:edit")
	@RequestMapping(value = "save")
	public String save(GsShoppingCart gsShoppingCart, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsShoppingCart)){
			return form(gsShoppingCart, model);
		}
		if (gsShoppingCart.getIsNewRecord()) {
			gsShoppingCart.setOffice(gsShoppingCart.getCurrentUser().getOffice());
		}
		gsShoppingCartService.save(gsShoppingCart);
		addMessage(redirectAttributes, "保存购物车成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsShoppingCart/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsShoppingCart:edit")
	@RequestMapping(value = "delete")
	public String delete(GsShoppingCart gsShoppingCart, RedirectAttributes redirectAttributes) {
		gsShoppingCartService.delete(gsShoppingCart);
		addMessage(redirectAttributes, "删除购物车成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsShoppingCart/?repage";
	}

}