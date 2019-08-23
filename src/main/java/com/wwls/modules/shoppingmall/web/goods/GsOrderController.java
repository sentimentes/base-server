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
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;
import com.wwls.modules.shoppingmall.service.goods.GsOrderService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 订单管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsOrder")
public class GsOrderController extends BaseController {

	@Autowired
	private GsOrderService gsOrderService;
	
	@ModelAttribute
	public GsOrder get(@RequestParam(required=false) String id) {
		GsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsOrderService.get(id);
		}
		if (entity == null){
			entity = new GsOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsOrder gsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsOrder.setOffice(UserUtils.getUser().getOffice());
		Page<GsOrder> page = gsOrderService.findPage(new Page<GsOrder>(request, response), gsOrder); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsOrderList";
	}

	@RequiresPermissions("shoppingmall:goods:gsOrder:view")
	@RequestMapping(value = "form")
	public String form(GsOrder gsOrder, Model model) {
		model.addAttribute("gsOrder", gsOrder);
		return "modules/shoppingmall/goods/gsOrderForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsOrder:edit")
	@RequestMapping(value = "save")
	public String save(GsOrder gsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsOrder)){
			return form(gsOrder, model);
		}
		if (gsOrder.getIsNewRecord()) {
			gsOrder.setOffice(gsOrder.getCurrentUser().getOffice());
		}
		gsOrderService.save(gsOrder);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsOrder/?repage";
	}
	/*
	 * 发货
	 */
	@RequiresPermissions("shoppingmall:goods:gsOrder:edit")
	@RequestMapping(value = "takeh")
	public String takeh(GsOrder gsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsOrder)){
			return form(gsOrder, model);
		}
		if (gsOrder.getIsNewRecord()) {
			gsOrder.setOffice(gsOrder.getCurrentUser().getOffice());
		}
		gsOrderService.takeh(gsOrder);
		addMessage(redirectAttributes, "发货成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsOrder/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(GsOrder gsOrder, RedirectAttributes redirectAttributes) {
		gsOrderService.delete(gsOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsOrder/?repage";
	}

}