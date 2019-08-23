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

import com.google.gson.annotations.Until;
import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.web.BaseController;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuSmallService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商品分类细分管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsMenuSmall")
public class GsMenuSmallController extends BaseController {

	@Autowired
	private GsMenuSmallService gsMenuSmallService;
	
	@Autowired
	private GsMenuService gsMenuService;
	
	@ModelAttribute
	public GsMenuSmall get(@RequestParam(required=false) String id) {
		GsMenuSmall entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsMenuSmallService.get(id);
		}
		if (entity == null){
			entity = new GsMenuSmall();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsMenuSmall gsMenuSmall, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsMenuSmall.setOffice(UserUtils.getUser().getOffice());
		Page<GsMenuSmall> page = gsMenuSmallService.findPage(new Page<GsMenuSmall>(request, response), gsMenuSmall); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsMenuSmallList";
	}

	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:view")
	@RequestMapping(value = "form")
	public String form(GsMenuSmall gsMenuSmall, Model model) {
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsMenuSmall", gsMenuSmall);
		return "modules/shoppingmall/goods/gsMenuSmallForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:edit")
	@RequestMapping(value = "save")
	public String save(GsMenuSmall gsMenuSmall, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsMenuSmall)){
			return form(gsMenuSmall, model);
		}
		if (gsMenuSmall.getIsNewRecord()) {
			gsMenuSmall.setOffice(gsMenuSmall.getCurrentUser().getOffice());
		}
		if("".equals(gsMenuSmall.getUpDownShelf()) || gsMenuSmall.getUpDownShelf() == null){
			gsMenuSmall.setUpDownShelf("0");
		}
		gsMenuSmallService.save(gsMenuSmall);
		addMessage(redirectAttributes, "保存商品分类细分成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuSmall/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:edit")
	@RequestMapping(value = "delete")
	public String delete(GsMenuSmall gsMenuSmall, RedirectAttributes redirectAttributes) {
		gsMenuSmallService.delete(gsMenuSmall);
		addMessage(redirectAttributes, "删除商品分类细分成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuSmall/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsMenuSmall gsMenuSmall, RedirectAttributes redirectAttributes) {
		List<String> idList = gsMenuSmall.getIdList();
		if (idList != null && idList.size() > 0){
			gsMenuSmallService.upDownShelf(gsMenuSmall);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuSmall/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuSmall/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsMenuSmall:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsMenuSmall gsMenuSmall,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsMenuSmall[] entitys = new GsMenuSmall[len];
        	List<String> idList = gsMenuSmall.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsMenuSmallService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsMenuSmallService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuSmall/?repage";
	}


}