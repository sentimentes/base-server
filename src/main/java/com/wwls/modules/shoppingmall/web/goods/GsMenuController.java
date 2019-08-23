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
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商品菜单管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsMenu")
public class GsMenuController extends BaseController {

	@Autowired
	private GsMenuService gsMenuService;
	
	@ModelAttribute
	public GsMenu get(@RequestParam(required=false) String id) {
		GsMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsMenuService.get(id);
		}
		if (entity == null){
			entity = new GsMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsMenu gsMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		Page<GsMenu> page = gsMenuService.findPage(new Page<GsMenu>(request, response), gsMenu); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsMenuList";
	}

	@RequiresPermissions("shoppingmall:goods:gsMenu:view")
	@RequestMapping(value = "form")
	public String form(GsMenu gsMenu, Model model) {
		model.addAttribute("gsMenu", gsMenu);
		return "modules/shoppingmall/goods/gsMenuForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsMenu:edit")
	@RequestMapping(value = "save")
	public String save(GsMenu gsMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsMenu)){
			return form(gsMenu, model);
		}
		if (gsMenu.getIsNewRecord()) {
			gsMenu.setOffice(gsMenu.getCurrentUser().getOffice());
		}
		if("".equals(gsMenu.getUpDownShelf()) || gsMenu.getUpDownShelf() == null){
			gsMenu.setUpDownShelf("0");
		}
		gsMenuService.save(gsMenu);
		addMessage(redirectAttributes, "保存商品菜单成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenu/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(GsMenu gsMenu, RedirectAttributes redirectAttributes) {
		gsMenuService.delete(gsMenu);
		addMessage(redirectAttributes, "删除商品菜单成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenu/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsMenu:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsMenu gsMenu, RedirectAttributes redirectAttributes) {
		List<String> idList = gsMenu.getIdList();
		if (idList != null && idList.size() > 0){
			gsMenuService.upDownShelf(gsMenu);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenu/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenu/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsMenu:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsMenu gsMenu,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsMenu[] entitys = new GsMenu[len];
        	List<String> idList = gsMenu.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsMenuService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsMenuService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenu/?repage";
	}

}