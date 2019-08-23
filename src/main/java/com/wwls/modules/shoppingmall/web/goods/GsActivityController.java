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
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.service.goods.GsActivityService;

/**
 * 导航广告管理Controller
 * @author leixiaoming
 * @version 2019-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsActivity")
public class GsActivityController extends BaseController {

	@Autowired
	private GsActivityService gsActivityService;
	
	@ModelAttribute
	public GsActivity get(@RequestParam(required=false) String id) {
		GsActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsActivityService.get(id);
		}
		if (entity == null){
			entity = new GsActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsActivity gsActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GsActivity> page = gsActivityService.findPage(new Page<GsActivity>(request, response), gsActivity); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsActivityList";
	}

	@RequiresPermissions("shoppingmall:goods:gsActivity:view")
	@RequestMapping(value = "form")
	public String form(GsActivity gsActivity, Model model) {
		model.addAttribute("gsActivity", gsActivity);
		return "modules/shoppingmall/goods/gsActivityForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsActivity:edit")
	@RequestMapping(value = "save")
	public String save(GsActivity gsActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsActivity)){
			return form(gsActivity, model);
		}
		if("".equals(gsActivity.getUpDownShelf()) || gsActivity.getUpDownShelf() == null){
			gsActivity.setUpDownShelf("0");
		}
		if (gsActivity.getIsNewRecord()) {
			gsActivity.setOffice(gsActivity.getCurrentUser().getOffice());
		}
		gsActivityService.save(gsActivity);
		addMessage(redirectAttributes, "保存导航广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsActivity/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(GsActivity gsActivity, RedirectAttributes redirectAttributes) {
		gsActivityService.delete(gsActivity);
		addMessage(redirectAttributes, "删除导航广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsActivity/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsActivity:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsActivity gsActivity, RedirectAttributes redirectAttributes) {
		List<String> idList = gsActivity.getIdList();
		if (idList != null && idList.size() > 0){
			gsActivityService.upDownShelf(gsActivity);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsActivity/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsActivity/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsActivity:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsActivity gsActivity,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsActivity[] entitys = new GsActivity[len];
        	List<String> idList = gsActivity.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsActivityService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsActivityService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsActivity/?repage";
	}


}