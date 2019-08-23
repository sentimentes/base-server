package com.wwls.modules.shoppingmall.web.goods;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.web.BaseController;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.shoppingmall.entity.goods.GsActivity;
import com.wwls.modules.shoppingmall.entity.goods.GsNavigation;
import com.wwls.modules.shoppingmall.service.goods.GsNavigationService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 导航广告管理Controller
 * @author leixiaoming
 * @version 2019-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsNavigation")
public class GsNavigationController extends BaseController {

	@Autowired
	private GsNavigationService gsNavigationService;
	
	@ModelAttribute
	public GsNavigation get(@RequestParam(required=false) String id) {
		GsNavigation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsNavigationService.get(id);
		}
		if (entity == null){
			entity = new GsNavigation();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsNavigation:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsNavigation gsNavigation, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsNavigation.setOffice(UserUtils.getUser().getOffice());
		Page<GsNavigation> page = gsNavigationService.findPage(new Page<GsNavigation>(request, response), gsNavigation); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsNavigationList";
	}

	@RequiresPermissions("shoppingmall:goods:gsNavigation:view")
	@RequestMapping(value = "form")
	public String form(GsNavigation gsNavigation, Model model) {
		model.addAttribute("gsNavigation", gsNavigation);
		return "modules/shoppingmall/goods/gsNavigationForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsNavigation:view")
	@RequestMapping(value = "forms")
	public String forms(GsNavigation gsNavigation, Model model) {
		gsNavigation = gsNavigationService.forms(gsNavigation);
		model.addAttribute("gsNavigation", gsNavigation);
		return "modules/shoppingmall/goods/gsNavigationForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsNavigation:edit")
	@RequestMapping(value = "save")
	public String save(GsNavigation gsNavigation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsNavigation)){
			return form(gsNavigation, model);
		}
		if (gsNavigation.getIsNewRecord()) {
			gsNavigation.setOffice(gsNavigation.getCurrentUser().getOffice());
		}
		if("".equals(gsNavigation.getUpDownShelf()) || gsNavigation.getUpDownShelf() == null){
			gsNavigation.setUpDownShelf("0");
		}
		gsNavigationService.save(gsNavigation);
		addMessage(redirectAttributes, "保存导航广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsNavigation/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsNavigation:edit")
	@RequestMapping(value = "delete")
	public String delete(GsNavigation gsNavigation, RedirectAttributes redirectAttributes) {
		gsNavigationService.delete(gsNavigation);
		addMessage(redirectAttributes, "删除导航广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsNavigation/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsNavigation:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsNavigation gsNavigation, RedirectAttributes redirectAttributes) {
		List<String> idList = gsNavigation.getIdList();
		if (idList != null && idList.size() > 0){
			gsNavigationService.upDownShelf(gsNavigation);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsNavigation/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsNavigation/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsNavigation:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsNavigation gsNavigation,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsNavigation[] entitys = new GsNavigation[len];
        	List<String> idList = gsNavigation.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsNavigationService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsNavigationService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsNavigation/?repage";
	}

}