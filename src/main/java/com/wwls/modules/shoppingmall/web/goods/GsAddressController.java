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
import com.wwls.modules.shoppingmall.entity.goods.GsAddress;
import com.wwls.modules.shoppingmall.service.goods.GsAddressService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 会员地址管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsAddress")
public class GsAddressController extends BaseController {

	@Autowired
	private GsAddressService gsAddressService;
	
	@ModelAttribute
	public GsAddress get(@RequestParam(required=false) String id) {
		GsAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsAddressService.get(id);
		}
		if (entity == null){
			entity = new GsAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsAddress gsAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsAddress.setOffice(UserUtils.getUser().getOffice());
		Page<GsAddress> page = gsAddressService.findPage(new Page<GsAddress>(request, response), gsAddress); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsAddressList";
	}

	@RequiresPermissions("shoppingmall:goods:gsAddress:view")
	@RequestMapping(value = "form")
	public String form(GsAddress gsAddress, Model model) {
		model.addAttribute("gsAddress", gsAddress);
		return "modules/shoppingmall/goods/gsAddressForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsAddress:edit")
	@RequestMapping(value = "save")
	public String save(GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsAddress)){
			return form(gsAddress, model);
		}
		if (gsAddress.getIsNewRecord()) {
			gsAddress.setOffice(gsAddress.getCurrentUser().getOffice());
		}
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
		gsAddress.setPepole(ids);
		gsAddressService.save(gsAddress);
		addMessage(redirectAttributes, "保存会员地址成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsAddress/?repage";
		}
	}
	
	
	
	@RequiresPermissions("shoppingmall:goods:gsAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(GsAddress gsAddress, RedirectAttributes redirectAttributes) {
		gsAddressService.delete(gsAddress);
		addMessage(redirectAttributes, "删除会员地址成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsAddress/?repage";
	}

}