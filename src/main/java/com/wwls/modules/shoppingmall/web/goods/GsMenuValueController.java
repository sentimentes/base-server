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
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuValue;
import com.wwls.modules.shoppingmall.entity.goods.GsType;
import com.wwls.modules.shoppingmall.service.goods.GsGoodsService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuSmallService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuValueService;
import com.wwls.modules.shoppingmall.service.goods.GsTypeService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商品分类细分值管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gsMenuValue")
public class GsMenuValueController extends BaseController {

	@Autowired
	private GsMenuValueService gsMenuValueService;
	
	@Autowired
	private GsMenuService gsMenuService;
	
	@Autowired
	private GsMenuSmallService gsMenuSmallService;
	
	@Autowired
	private GsGoodsService gsGoodsService;
	
	@Autowired
	private GsTypeService gsTypeService;
	
	
	@ModelAttribute
	public GsMenuValue get(@RequestParam(required=false) String id) {
		GsMenuValue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gsMenuValueService.get(id);
		}
		if (entity == null){
			entity = new GsMenuValue();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:view")
	@RequestMapping(value = {"list", ""})
	public String list(GsMenuValue gsMenuValue, HttpServletRequest request, HttpServletResponse response, Model model) {
		gsMenuValue.setOffice(UserUtils.getUser().getOffice());
		Page<GsMenuValue> page = gsMenuValueService.findPage(new Page<GsMenuValue>(request, response), gsMenuValue); 
		model.addAttribute("page", page);
		GsMenu gsMenu=new GsMenu();
		GsMenuSmall gsMenuSmall2=new GsMenuSmall();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		gsMenuSmall2.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		if(!"".equals(gsMenuValue.getGsMenuId())){
			gsMenuSmall2.setGsMenuId(gsMenuValue.getGsMenuId());
		}
		List<GsMenuSmall> gsMenuSmallList= gsMenuSmallService.findList(gsMenuSmall2);
		for(GsMenuSmall i:gsMenuSmallList){
			GsMenu s=gsMenuService.get(i.getGsMenuId());
			i.setName(s.getSpType1()+i.getName());
		}
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsMenuSmallList", gsMenuSmallList);
		return "modules/shoppingmall/goods/gsMenuValueList";
	}

	@RequiresPermissions("shoppingmall:goods:gsMenuValue:view")
	@RequestMapping(value = "form")
	public String form(GsMenuValue gsMenuValue, Model model) {
		GsMenu gsMenu=new GsMenu();
		GsMenuSmall gsMenuSmall2=new GsMenuSmall();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		gsMenuSmall2.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		if(!"".equals(gsMenuValue.getGsMenuId())){
			gsMenuSmall2.setGsMenuId(gsMenuValue.getGsMenuId());
		}
		List<GsMenuSmall> gsMenuSmallList= gsMenuSmallService.findList(gsMenuSmall2);
		for(GsMenuSmall i:gsMenuSmallList){
			GsMenu s=gsMenuService.get(i.getGsMenuId());
			i.setName(s.getSpType1()+i.getName());
		}
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsMenuValue", gsMenuValue);
		model.addAttribute("gsMenuSmallList", gsMenuSmallList);
		return "modules/shoppingmall/goods/gsMenuValueForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:view")
	@RequestMapping(value = "form2")
	public String form2(String id,GsMenuValue gsMenuValue, Model model) {
		GsGoods gsGoods=new GsGoods();
		gsGoods.setOffice(UserUtils.getUser().getOffice());
		
		if(!"".equals(gsMenuValue.getGsMenuId())){
			gsGoods.setMenuId(gsMenuValue.getGsMenuId());
		}
		List<GsGoods> gsGoodslist=gsGoodsService.findList(gsGoods);
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsGoodslist", gsGoodslist);
		model.addAttribute("gsMenuValue", gsMenuValue);
		model.addAttribute("id", id);
		return "modules/shoppingmall/goods/gsMenuValueList2";
	}
	
	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:view")
	@RequestMapping(value = "addsave")
	public String addsave(String id,GsMenuValue gsMenuValue, Model model, RedirectAttributes redirectAttributes) {
		List<String> idlist=gsMenuValue.getIdList();
		GsGoods gsGoods=new GsGoods();
		
		gsGoods.setOffice(UserUtils.getUser().getOffice());
		
		List<GsGoods> gsGoodslist=gsGoodsService.findList(gsGoods);
		int n=0,m=0;
		StringBuilder s=new StringBuilder();
		for(String j:idlist){
		for(GsGoods i:gsGoodslist){
				if(j.equals(i.getId())){
					GsType gsType=new GsType();
					gsType.setOffice(UserUtils.getUser().getOffice());
					gsType.setVaId(id);
					gsType.setGsId(j);
					if(gsTypeService.findList(gsType).size()>0){
						n++;
						s.append(i.getName()+",");
						continue;
					}else{
						gsTypeService.save(gsType);
						m++;
					}
				}
			}
		}
		if(n == 0){
			addMessage(redirectAttributes, "添加商品成功,成功了"+m+"条");
		}else if(m == 0){
			addMessage(redirectAttributes, "添加商品失败,"+s+"商品已经存在");
		}else {
			addMessage(redirectAttributes, "添加商品部分成功,成功了"+m+"条,失败了"+n+"条，其中"+s+"商品中已经存在");
		}
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:view")
	@RequestMapping(value = "forms")
	public String forms(GsMenuValue gsMenuValue, Model model) {
		String id=gsMenuValue.getId();
		gsMenuValue.setGsMenuSmallId(id);
		gsMenuValue.setId("");
		GsMenu gsMenu=new GsMenu();
		GsMenuSmall gsMenuSmall2=new GsMenuSmall();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		gsMenuSmall2.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		List<GsMenuSmall> gsMenuSmallList= gsMenuSmallService.findList(gsMenuSmall2);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsMenuValue", gsMenuValue);
		model.addAttribute("gsMenuSmallList", gsMenuSmallList);
		return "modules/shoppingmall/goods/gsMenuValueForm";
	}

	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:edit")
	@RequestMapping(value = "save")
	public String save(GsMenuValue gsMenuValue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gsMenuValue)){
			return form(gsMenuValue, model);
		}
		if (gsMenuValue.getIsNewRecord()) {
			gsMenuValue.setOffice(gsMenuValue.getCurrentUser().getOffice());
		}
		if("".equals(gsMenuValue.getUpDownShelf()) || gsMenuValue.getUpDownShelf() == null){
			gsMenuValue.setUpDownShelf("0");
		}
		gsMenuValueService.save(gsMenuValue);
		addMessage(redirectAttributes, "保存商品分类细分值成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:edit")
	@RequestMapping(value = "delete")
	public String delete(GsMenuValue gsMenuValue, RedirectAttributes redirectAttributes) {
		gsMenuValueService.delete(gsMenuValue);
		addMessage(redirectAttributes, "删除商品分类细分值成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(GsMenuValue gsMenuValue, RedirectAttributes redirectAttributes) {
		List<String> idList = gsMenuValue.getIdList();
		if (idList != null && idList.size() > 0){
			gsMenuValueService.upDownShelf(gsMenuValue);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:gsMenuValue:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(GsMenuValue gsMenuValue,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		GsMenuValue[] entitys = new GsMenuValue[len];
        	List<String> idList = gsMenuValue.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = gsMenuValueService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					gsMenuValueService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gsMenuValue/?repage";
	}


}