package com.wwls.modules.shoppingmall.web.goods;

import java.util.Date;
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
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.service.goods.GsGoodsService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuSmallService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商品管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsGoods")
public class GsGoodsController extends BaseController {

	@Autowired
	private GsGoodsService gsGoodsService;
	
	@Autowired
	private GsMenuService gsMenuService;
	
	@Autowired
	private GsMenuSmallService gsMenuSmallService;
	
	@ModelAttribute
	public GsGoods get(@RequestParam(required=false) String id) {
		GsGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsGoodsService.get(id);
		}
		if (entity == null){
			entity = new GsGoods();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsGoods:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsGoods gsGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsGoods.setOffice(UserUtils.getUser().getOffice());
		Page<GsGoods> page = gsGoodsService.findPage(new Page<GsGoods>(request, response), gsGoods); 
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsGoodsList";
	}

	@RequiresPermissions("shoppingmall:goods:gsGoods:view")
	@RequestMapping(value = "form")
	public String form(GsGoods gsGoods, Model model) {
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsGoods", gsGoods);
		return "modules/shoppingmall/goods/gsGoodsForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsGoods:view")
	@RequestMapping(value = "form2")
	public String form2(GsGoods gsGoods, Model model) {
		model.addAttribute("gsGoods", gsGoods);
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		return "modules/shoppingmall/goods/gsGoodsForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsGoods:view")
	@RequestMapping(value = "index")
	public String index(GsGoods gsGoods, Model model) {
		model.addAttribute("gsGoods", gsGoods);
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		return "modules/shoppingmall/goods/home/home";
	}

	@RequiresPermissions("shoppingmall:goods:gsGoods:edit")
	@RequestMapping(value = "save")
	public String save(GsGoods gsGoods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsGoods)){
			return form(gsGoods, model);
		}
		if (gsGoods.getIsNewRecord()) {
			gsGoods.setOffice(gsGoods.getCurrentUser().getOffice());
		}
		if("".equals(gsGoods.getUpDownShelf()) || gsGoods.getUpDownShelf() == null){
			gsGoods.setUpDownShelf("0");
		}
		if("".equals(gsGoods.getId())){
			gsGoods.setSalesVolume("0");
		}
		gsGoodsService.save(gsGoods);
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsGoods/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsGoods:edit")
	@RequestMapping(value = "delete")
	public String delete(GsGoods gsGoods, RedirectAttributes redirectAttributes) {
		gsGoodsService.delete(gsGoods);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsGoods/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsGoods:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsGoods gsGoods, RedirectAttributes redirectAttributes) {
		List<String> idList = gsGoods.getIdList();
		if (idList != null && idList.size() > 0){
			gsGoodsService.upDownShelf(gsGoods);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsGoods/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsGoods/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsGoods:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsGoods gsGoods,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsGoods[] entitys = new GsGoods[len];
        	List<String> idList = gsGoods.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsGoodsService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsGoodsService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsGoods/?repage";
	}

}