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
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.service.goods.GsLogisticsService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 物流管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsLogistics")
public class GsLogisticsController extends BaseController {

	@Autowired
	private GsLogisticsService gsLogisticsService;
	
	@ModelAttribute
	public GsLogistics get(@RequestParam(required=false) String id) {
		GsLogistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsLogisticsService.get(id);
		}
		if (entity == null){
			entity = new GsLogistics();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsLogistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsLogistics gsLogistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsLogistics.setOffice(UserUtils.getUser().getOffice());
		Page<GsLogistics> page = gsLogisticsService.findPage(new Page<GsLogistics>(request, response), gsLogistics); 
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/gsLogisticsList";
	}

	@RequiresPermissions("shoppingmall:goods:gsLogistics:view")
	@RequestMapping(value = "form")
	public String form(GsLogistics gsLogistics, Model model) {
		model.addAttribute("gsLogistics", gsLogistics);
		return "modules/shoppingmall/goods/gsLogisticsForm";
	}

	@RequiresPermissions("shoppingmall:goods:gsLogistics:edit")
	@RequestMapping(value = "save")
	public String save(GsLogistics gsLogistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsLogistics)){
			return form(gsLogistics, model);
		}
		if (gsLogistics.getIsNewRecord()) {
			gsLogistics.setOffice(gsLogistics.getCurrentUser().getOffice());
		}
		if("".equals(gsLogistics.getUpDownShelf()) || gsLogistics.getUpDownShelf() == null){
			gsLogistics.setUpDownShelf("0");
		}
		gsLogisticsService.save(gsLogistics);
		addMessage(redirectAttributes, "保存物流成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsLogistics/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsLogistics:edit")
	@RequestMapping(value = "delete")
	public String delete(GsLogistics gsLogistics, RedirectAttributes redirectAttributes) {
		gsLogisticsService.delete(gsLogistics);
		addMessage(redirectAttributes, "删除物流成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsLogistics/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsLogistics:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsLogistics gsLogistics, RedirectAttributes redirectAttributes) {
		List<String> idList = gsLogistics.getIdList();
		if (idList != null && idList.size() > 0){
			gsLogisticsService.upDownShelf(gsLogistics);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsLogistics/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsLogistics/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsLogistics:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsLogistics gsLogistics,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsLogistics[] entitys = new GsLogistics[len];
        	List<String> idList = gsLogistics.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsLogisticsService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsLogisticsService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsLogistics/?repage";
	}

}