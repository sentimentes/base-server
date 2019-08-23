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
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;
import com.wwls.modules.shoppingmall.entity.goods.GsTjgs;
import com.wwls.modules.shoppingmall.entity.goods.TjgsType;
import com.wwls.modules.shoppingmall.service.goods.GsGoodsService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuSmallService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuValueService;
import com.wwls.modules.shoppingmall.service.goods.GsTjgsService;
import com.wwls.modules.shoppingmall.service.goods.GsTypeService;
import com.wwls.modules.shoppingmall.service.goods.TjgsTypeService;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 特价商品类型管理Controller
 * @author leixiaoming
 * @version 2019-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/tjgsType")
public class TjgsTypeController extends BaseController {

	@Autowired
	private TjgsTypeService tjgsTypeService;
	
	@Autowired
	private GsTjgsService gsTjgsService;

	
	@Autowired
	private GsMenuService gsMenuService;
	
	
	@Autowired
	private GsGoodsService gsGoodsService;
	
	@ModelAttribute
	public TjgsType get(@RequestParam(required=false) String id) {
		TjgsType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tjgsTypeService.get(id);
		}
		if (entity == null){
			entity = new TjgsType();
		}
		return entity;
	}
	
	@RequiresPermissions("shoppingmall:goods:tjgsType:view")
	@RequestMapping(value = {"list", ""})
	public String list(TjgsType tjgsType, HttpServletRequest request, HttpServletResponse response, Model model) {
		tjgsType.setOffice(UserUtils.getUser().getOffice());
		List<TjgsType> tjgsTypeList = tjgsTypeService.findList(tjgsType);
		if(!UserUtils.getUser().getOffice().getId().equals(Global.getConfig("office_id"))){
			Office office=new Office();
			office.setId(Global.getConfig("office_id"));
			tjgsType.setOffice(office);
			List<TjgsType> gsOrderList1 = tjgsTypeService.findList(tjgsType);
			for(TjgsType s:gsOrderList1){
				tjgsTypeList.add(s);
			}
		}
		Page<TjgsType> page=new Page<TjgsType>(request, response);
		tjgsType.setPage(page);
		page.setList(tjgsTypeList);
		model.addAttribute("page", page);
		return "modules/shoppingmall/goods/tjgsTypeList";
	}

	@RequiresPermissions("shoppingmall:goods:tjgsType:view")
	@RequestMapping(value = "form")
	public String form(TjgsType tjgsType, Model model) {
		model.addAttribute("tjgsType", tjgsType);
		return "modules/shoppingmall/goods/tjgsTypeForm";
	}
	
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "form2")
	public String form2(String id,TjgsType tjgsType, Model model) {
		GsGoods gsGoods=new GsGoods();
		gsGoods.setOffice(UserUtils.getUser().getOffice());
		if(!"".equals(tjgsType.getGsMenuId())){
			gsGoods.setMenuId(tjgsType.getGsMenuId());
		}
		List<GsGoods> gsGoodslist=gsGoodsService.findList(gsGoods);
		GsMenu gsMenu=new GsMenu();
		gsMenu.setOffice(UserUtils.getUser().getOffice());
		List<GsMenu> gsMenuList= gsMenuService.findList(gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsGoodslist", gsGoodslist);
		model.addAttribute("tjgsType", tjgsType);
		model.addAttribute("id", id);
		return "modules/shoppingmall/goods/tjgsTypeList2";
	}
	
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "addsave")
	public String addsave(String id,TjgsType tjgsType, Model model, RedirectAttributes redirectAttributes) {
		List<String> idlist=tjgsType.getIdList();
		GsGoods gsGoods=new GsGoods();
		gsGoods.setOffice(UserUtils.getUser().getOffice());
		List<GsGoods> gsGoodslist=gsGoodsService.findList(gsGoods);
		int n=0,m=0;
		StringBuilder s=new StringBuilder();
		for(String j:idlist){
		for(GsGoods i:gsGoodslist){
				if(j.equals(i.getId())){
					GsTjgs gsTjgs=new GsTjgs();
					gsTjgs.setOffice(UserUtils.getUser().getOffice());
					gsTjgs.setTjgsId(id);
					gsTjgs.setGsId(i.getId());
					if(gsTjgsService.findList(gsTjgs).size()>0){
						n++;
						s.append(i.getName()+",");
						continue;
					}else{
						gsTjgsService.save(gsTjgs);
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
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "save")
	public String save(TjgsType tjgsType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tjgsType)){
			return form(tjgsType, model);
		}
		if (tjgsType.getIsNewRecord()) {
			tjgsType.setOffice(tjgsType.getCurrentUser().getOffice());
		}
		if("".equals(tjgsType.getUpDownShelf()) || tjgsType.getUpDownShelf() == null){
			tjgsType.setUpDownShelf("0");
		}
		tjgsTypeService.save(tjgsType);
		addMessage(redirectAttributes, "保存特价商品类型管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
	}
	
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "delete")
	public String delete(TjgsType tjgsType, RedirectAttributes redirectAttributes) {
		tjgsTypeService.delete(tjgsType);
		addMessage(redirectAttributes, "删除特价商品类型管理成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
	}
	
	/**
	 * 批量上下架
	 * @param gsActivity 
	 */
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "upDownShelf")
	public String deletes(TjgsType tjgsType, RedirectAttributes redirectAttributes) {
		List<String> idList = tjgsType.getIdList();
		if (idList != null && idList.size() > 0){
			tjgsTypeService.upDownShelf(tjgsType);
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
		}else{
			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
		}
	}
	
	/** 批量修改排序  */
	@RequiresPermissions("shoppingmall:goods:tjgsType:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(TjgsType tjgsType,HttpServletRequest request ,String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
    	if (ids != null) {
    		int len = ids.length;
    		TjgsType[] entitys = new TjgsType[len];
        	List<String> idList = tjgsType.getIdList(); 
			if (idList != null && idList.size() > 0) {
        		for (int i = 0; i < idList.size(); i++) {
        			for (int j = 0; j < len; j++) {
        				if (idList.get(i).equals(ids[j]) ) {
        					entitys[i] = tjgsTypeService.get(ids[j]);
        					entitys[i].setSort(new String(sorts[j]));
        					tjgsTypeService.updateSort(entitys[i]);
        				}
					}
            	}
        		addMessage(redirectAttributes, "修改成功");		
    		}else {
    			addMessage(redirectAttributes, "操作失败，请选择商品后再操作");
    		}
		}
    	return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/tjgsType/?repage";
	}



}