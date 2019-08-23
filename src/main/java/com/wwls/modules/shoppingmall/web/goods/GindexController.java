package com.wwls.modules.shoppingmall.web.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.web.BaseController;
import com.wwls.modules.shoppingmall.entity.goods.GsActivity;
import com.wwls.modules.shoppingmall.entity.goods.GsAddress;
import com.wwls.modules.shoppingmall.entity.goods.GsDj;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuValue;
import com.wwls.modules.shoppingmall.entity.goods.GsNavigation;
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;
import com.wwls.modules.shoppingmall.entity.goods.GsShoppingCart;
import com.wwls.modules.shoppingmall.entity.goods.GsTjgs;
import com.wwls.modules.shoppingmall.entity.goods.GsType;
import com.wwls.modules.shoppingmall.entity.goods.GsUser;
import com.wwls.modules.shoppingmall.entity.goods.TjgsType;
import com.wwls.modules.shoppingmall.service.goods.GsActivityService;
import com.wwls.modules.shoppingmall.service.goods.GsAddressService;
import com.wwls.modules.shoppingmall.service.goods.GsDjService;
import com.wwls.modules.shoppingmall.service.goods.GsGoodsService;
import com.wwls.modules.shoppingmall.service.goods.GsLogisticsService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuSmallService;
import com.wwls.modules.shoppingmall.service.goods.GsMenuValueService;
import com.wwls.modules.shoppingmall.service.goods.GsNavigationService;
import com.wwls.modules.shoppingmall.service.goods.GsOrderService;
import com.wwls.modules.shoppingmall.service.goods.GsShoppingCartService;
import com.wwls.modules.shoppingmall.service.goods.GsTjgsService;
import com.wwls.modules.shoppingmall.service.goods.GsTypeService;
import com.wwls.modules.shoppingmall.service.goods.GsUserService;
import com.wwls.modules.shoppingmall.service.goods.TjgsTypeService;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.service.OfficeService;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 商场活动管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/gindex")
public class GindexController extends BaseController {
	
	@Autowired
	private GsAddressService gsAddressService;
	
	@Autowired
	private GsActivityService gsActivityService;
	
	@Autowired
	private GsOrderService gsOrderService;
	
	@Autowired
	private GsUserService gsUserService;
	
	@Autowired
	private GsMenuService gsMenuService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private TjgsTypeService tjgsTypeService;
	
	@Autowired
	private GsTjgsService gsTjgsService;
	
	@Autowired
	private GsGoodsService gsGoodsService;
	
	@Autowired
	private GsMenuValueService gsMenuValueService;
	
	@Autowired
	private GsLogisticsService gsLogisticsService;
	
	
	Map<String ,String> map=new HashMap<String,String>();
	
	@Autowired
	private GsNavigationService gsNavigationService;
	
	@Autowired
	private GsMenuSmallService gsMenuSmallService;
	
	@Autowired
	private GsDjService gsDjService;
	
	@Autowired
	private GsTypeService gsTypeService;
	
	@Autowired
	private GsShoppingCartService gsShoppingCartService;
	
	@RequestMapping(value = {"list", ""})
	public String list(String ss,GsNavigation gsNavigation,HttpServletRequest request, HttpServletResponse response, Model model) {
		GsMenu gsMenu=new GsMenu();
		HttpSession session =request.getSession();
		GsMenuValue gsMenuValue=new GsMenuValue();
		GsGoods gsGoods=new GsGoods();
		GsUser gsUser=new GsUser();
		GsDj gsDj=new GsDj();
		GsActivity gsActivity=new GsActivity();
		TjgsType tjgsType=new TjgsType();
		String gsOfficeId=gsNavigation.getGsofficeId();
		String gsOfficeId2=(String) request.getSession().getAttribute("gsOfficeId");
		Office office=new Office();
		Office office1=new Office();
		GsOrder gsOrder=new GsOrder();
		if("2".equals(ss)){
			session.setAttribute("gsOfficeId",Global.getConfig("office_id"));
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/?repage";
		}
		if(!"".equals(gsOfficeId) && gsOfficeId != null ){
			session.setAttribute("gsOfficeId",gsOfficeId);
			office.setId(gsOfficeId);
			gsMenu.setOffice(office);
			gsNavigation.setOffice(office);
			tjgsType.setOffice(office);
			if(!Global.getConfig("office_id").equals(gsOfficeId)){
			gsGoods.setOffice(office);
			}
			gsDj.setOffice(office);
			gsMenuValue.setOffice(office);
			gsOrder.setOffice(office);
			map.put(gsOfficeId,gsOfficeId);
		}
		if(!"".equals(gsOfficeId2) && gsOfficeId2 != null ){
			if(gsOfficeId == null){
			office.setId(gsOfficeId2);
			gsMenu.setOffice(office);
			gsNavigation.setOffice(office);
			tjgsType.setOffice(office);
			if(!Global.getConfig("office_id").equals(gsOfficeId2)){
			gsGoods.setOffice(office);
			}
			gsDj.setOffice(office);
			gsMenuValue.setOffice(office);
			gsOrder.setOffice(office);
			}
		}else{
			session.setAttribute("gsOfficeId",Global.getConfig("office_id"));
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/?repage";
		}
		String id=(String) request.getSession().getAttribute("id");
		if(!"".equals(id)&& id !=null ){
			gsOrder.setPepole(id);
			Integer sss=gsOrderService.getjf(gsOrder);
			session.setAttribute("jifei",sss);
			if(!"".equals(sss)&& sss !=null ){
				gsDj.setStart(sss);
				List<GsDj> gsDjList=gsDjService.findList(gsDj);
				gsDj=gsDjList.get(0);
				session.setAttribute("zk",gsDj.getZk());
			}
		}
		
		List<GsMenu> gsMenuList=gsMenuService.findAllList(gsMenu);
		List<GsMenuValue> gsMenuValueList=gsMenuValueService.findAllList(gsMenuValue);
		List<GsGoods> gsGoodsList=gsGoodsService.findAllList(gsGoods);
		List<TjgsType> tjgsTypeList=tjgsTypeService.findAllList(tjgsType);
		List<Office> officeList=officeService.findOfficeList(office1);
		
		List<GsActivity> gsActivityList=gsActivityService.findAllList(gsActivity);
		List<GsNavigation> gsNavigationList=gsNavigationService.findAllList(gsNavigation);
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
		}else{
			gsUser.setId(ids);
			gsUser=gsUserService.get(gsUser);
		}
		model.addAttribute("gsGoods", gsGoods);
		model.addAttribute("gsDj", gsDj);
		model.addAttribute("gsUser", gsUser);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("officeList", officeList);
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		model.addAttribute("tjgsTypeList", tjgsTypeList);
		model.addAttribute("gsGoodsList", gsGoodsList);
		model.addAttribute("gsMenuValueList", gsMenuValueList);
		model.addAttribute("gsActivityList", gsActivityList);
		model.addAttribute("gsNavigationList", gsNavigationList);
		return "modules/shoppingmall/home/home";
	}
	/*
	 * 活动详情页面
	 */
	@RequestMapping(value = "activity")
	public String activity(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		GsActivity gsActivity=new GsActivity();
		GsGoods gsGoods=new GsGoods();
		gsActivity.setId(id);
		gsActivity=gsActivityService.get(gsActivity);
		model.addAttribute("gsGoods", gsGoods);
		model.addAttribute("gsActivity", gsActivity);
		HttpSession session =request.getSession();
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		return "modules/shoppingmall/home/activity";
	}
	/*
	 * 导航栏详情页面
	 */
	@RequestMapping(value = "navigation")
	public String navigation(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		GsNavigation gsNavigation=new GsNavigation();
		GsGoods gsGoods=new GsGoods();
		gsNavigation.setId(id);
		gsNavigation=gsNavigationService.get(gsNavigation);
		model.addAttribute("gsNavigation", gsNavigation);
		model.addAttribute("gsGoods", gsGoods);
		HttpSession session =request.getSession();
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		return "modules/shoppingmall/home/activity";
	}
	
	/*
	 * 特价商品页面
	 */
	
	@RequestMapping(value = "tjgsTypelist")
	public String tjgsTypelist(String id,String gsMenuId,HttpServletRequest request, HttpServletResponse response, Model model) {
		GsTjgs gsTjgs=new GsTjgs();
		GsMenu gsMenu=new GsMenu();
		GsGoods gsGoods=new GsGoods();
		Office office=new Office();
		if(!"".equals(gsMenuId)){
			gsTjgs.setGsMenuId(gsMenuId);
		}
		String gsOfficeId=(String) request.getSession().getAttribute("gsOfficeId");
		if(!"".equals(gsOfficeId) && gsOfficeId != null){
			office.setId(gsOfficeId);
			gsTjgs.setOffice(office);
			gsMenu.setOffice(office);
		}
		List<GsMenu> gsMenuList=gsMenuService.findAllList(gsMenu);
		gsTjgs.setTjgsId(id);
		List<GsTjgs> gsTjgsList=gsTjgsService.getgslist(gsTjgs);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsTjgsList", gsTjgsList);
		model.addAttribute("TjgsId", id);
		model.addAttribute("gsMenuId", gsMenuId);
		model.addAttribute("gsGoods", gsGoods);
		HttpSession session =request.getSession();
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		return "modules/shoppingmall/home/search";
	}
	/* 
	 * leixiaoming
	 * 商品详情
	 */
	@RequestMapping(value = "goodsone")
	public String goodsone(GsGoods gsGoods,String gsMenuId,HttpServletRequest request, HttpServletResponse response, Model model) {
		gsGoods=gsGoodsService.get(gsGoods);
		
		String menuId= gsGoods.getMenuId();
		GsMenu gsMenu=new GsMenu();
		gsMenu.setId(menuId);
		gsMenu=gsMenuService.get(gsMenu);
		String name=gsMenu.getSpType1();
		GsType gsType=new GsType();
		gsType.setOffice(gsGoods.getOffice());
		GsMenuSmall gsMenuSmall=new GsMenuSmall();
		gsMenuSmall.setOffice(gsGoods.getOffice());
		gsMenuSmall.setGsMenuName(name);
		GsShoppingCart gsShoppingCart=new GsShoppingCart();
		gsType.setGsId(gsGoods.getId());
		List<GsType> gsTypeList=gsTypeService.findAllList(gsType);
		List<GsMenuSmall> gsMenuSmallList=gsMenuSmallService.findAllList(gsMenuSmall);
		model.addAttribute("gsGoods", gsGoods);
		model.addAttribute("gsTypeList", gsTypeList);
		model.addAttribute("gsMenuSmallList", gsMenuSmallList);
		model.addAttribute("gsShoppingCart", gsShoppingCart);
		model.addAttribute("office",gsGoods.getOffice());
		return "modules/shoppingmall/home/goods";
	}
	/**
	 * 添加到购物车
	 * @param gsShoppingCart
	 * @param request
	 * @param response
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "savecard")
	public String savecard(GsShoppingCart gsShoppingCart,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		model.addAttribute("gsShoppingCart", gsShoppingCart);
		String ids=(String) request.getSession().getAttribute("id");
		GsGoods gsGoods=new GsGoods();
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			gsShoppingCart.setCreateBys(ids);
			model.addAttribute("gsGoods", gsGoods);
			gsShoppingCartService.save(gsShoppingCart);
			addMessage(redirectAttributes, "加入购物车成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/goodsone/?repage&id="+gsShoppingCart.getGsId();
		}
	}
	
		/**
		 * 进入到购物车
		 * @param gsShoppingCart
		 * @param request
		 * @param response
		 * @param model
		 * @param redirectAttributes
		 * @return
		 */
		@RequestMapping(value = "shopcart")
		public String shopcart(GsShoppingCart gsShoppingCart,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
			GsGoods gsGoods=new GsGoods();
			String ids=(String) request.getSession().getAttribute("id");
			if("".equals(ids) || ids == null){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
			}else{
			gsShoppingCart.setCreateBys(ids);
			List<GsShoppingCart> gsShoppingCartList=gsShoppingCartService.findList(gsShoppingCart);
			model.addAttribute("gsShoppingCartList", gsShoppingCartList);
			model.addAttribute("gsGoods", gsGoods);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/home/shopcart";
			}
		}
		
		/*
		 * 添加地址
		 */
		@RequestMapping(value = "addressSave")
		public String addressSave(String ss,String number,String parameter,GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
			List<String> idList=gsAddress.getIdList();
			StringBuffer idlist=new StringBuffer();
			if(idList.size()>0){
				for(String id:idList){
					idlist.append(id+",");
				}
				if(!"1".equals(ss)){
					idlist.delete(idlist.length()-2, idlist.length());	
					idlist.delete(0, 1);
				}else{
					idlist.delete(idlist.length()-1, idlist.length());
				}
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
			if(!"1".equals(ss)){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/jiesuan/?repage&idList="+idlist;
			}else{
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/jiesuans/?repage&gsId="+idlist+"&number="+number+"&parameter="+parameter;
			}
				
			}
		}
		
		/*
		 * 添加地址
		 */
		@RequestMapping(value = "addressSaves")
		public String addressSaves(GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		
			
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
			
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/address/?repage";
			}
					
		}
		/*
		 * 删除地址
		 */
		@RequestMapping(value = "addtrssDel")
		public String addtrssDel(GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
			String ids=(String) request.getSession().getAttribute("id");
			if("".equals(ids) || ids == null){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
			}else{
			List<String> idList=gsAddress.getIdList();
			StringBuffer idlist=new StringBuffer();
			for(String id:idList){
				idlist.append(id+",");
			}
			idlist.delete(idlist.length()-2, idlist.length());
			idlist.delete(0, 1);
			gsAddressService.delete(gsAddress);
			addMessage(redirectAttributes, "删除地址成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/jiesuan/?repage&idList="+idlist;
			}
		} 
		
		/*
		 * 地址页面删除地址
		 */
		@RequestMapping(value = "addtrssDels")
		public String addtrssDels(GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
			String ids=(String) request.getSession().getAttribute("id");
			if("".equals(ids) || ids == null){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
			}else{
			gsAddressService.delete(gsAddress);
			addMessage(redirectAttributes, "删除地址成功");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/address/?repage";
			}
		} 
		
		/*
		 * 进入结算页面
		 */
		@RequestMapping(value = "jiesuan")
		public String jiesuan(GsShoppingCart gsShoppingCart,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
			String ids=(String) request.getSession().getAttribute("id");
			if("".equals(ids) || ids == null){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
			}else{
			GsGoods gsGoods=new GsGoods();
			GsAddress gsAddress=new GsAddress();
			GsOrder gsOrder=new GsOrder();
			GsLogistics gsLogistics=new GsLogistics();
			gsAddress.setPepole(ids);
			Office office=new Office();
			office.setId((String) request.getSession().getAttribute("gsOfficeId"));
			gsLogistics.setOffice(office);
			List<GsLogistics> gsLogisticsList= gsLogisticsService.findAllList(gsLogistics);
			List<GsAddress> gsAddressList= gsAddressService.findList(gsAddress);
			List<String> idlist=gsShoppingCart.getIdList();
			List<GsShoppingCart> gsShoppingCartLists=gsShoppingCartService.findList(gsShoppingCart);
			List<GsShoppingCart> gsShoppingCartList=new ArrayList<GsShoppingCart>();
			if (idlist != null && idlist.size() > 0){
			for(String id:idlist){
				for(GsShoppingCart gsShoppingCart2:gsShoppingCartLists){
					if(id.equals(gsShoppingCart2.getId())){
						gsShoppingCartList.add(gsShoppingCart2);
					}
				}
			}
			model.addAttribute("gsLogisticsList", gsLogisticsList);
			model.addAttribute("gsShoppingCartList", gsShoppingCartList);
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsOrder", gsOrder);
			model.addAttribute("gsAddress", gsAddress);
			model.addAttribute("idlist", idlist);
			model.addAttribute("gsAddressList", gsAddressList);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/home/pay";
			}else{
				addMessage(redirectAttributes, "请选择你要购买的物品");
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/shopcart/?repage";
			}
			}
		}
		/*
		 * 立即购买
		 */
		@RequestMapping(value = "jiesuans")
		public String jiesuans(GsShoppingCart gsShoppingCart,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
			String ids=(String) request.getSession().getAttribute("id");
			if("".equals(ids) || ids == null){
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
			}else{
			GsGoods gsGoods=new GsGoods();
			GsAddress gsAddress=new GsAddress();
			gsAddress.setPepole(ids);
			GsOrder gsOrder=new GsOrder();
			GsLogistics gsLogistics=new GsLogistics();
			String gsid=gsShoppingCart.getGsId();
			GsGoods gsGoods2= gsGoodsService.get(gsid);
			Office office=new Office();
			office.setId((String) request.getSession().getAttribute("gsOfficeId"));
			gsLogistics.setOffice(office);
			List<GsLogistics> gsLogisticsList= gsLogisticsService.findAllList(gsLogistics);
			List<GsAddress> gsAddressList= gsAddressService.findList(gsAddress);
			List<GsShoppingCart> gsShoppingCartList=new ArrayList<GsShoppingCart>();
			gsShoppingCartList.add(gsShoppingCart);	
			gsShoppingCart.setGsName(gsGoods2.getName());
			gsShoppingCart.setImage(gsGoods2.getImage());
			gsShoppingCart.setPrice(gsGoods2.getPrice());
			gsShoppingCart.setSalePrice(gsGoods2.getSalePrice());
			model.addAttribute("gsLogisticsList", gsLogisticsList);
			model.addAttribute("gsShoppingCartList", gsShoppingCartList);
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsOrder", gsOrder);
			model.addAttribute("idlist", gsShoppingCart.getGsId());
			model.addAttribute("number", gsShoppingCart.getNumber());
			model.addAttribute("parameter", gsShoppingCart.getParameter());
			model.addAttribute("gsAddress", gsAddress);
			model.addAttribute("ss", "1");
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			model.addAttribute("gsAddressList", gsAddressList);
			return "modules/shoppingmall/home/pay";
			
			}
		}
		
		
	/* 
	 * leixiaoming
	 * 查询
	 */
	@RequestMapping(value = "goodsSame")
	public String goodoSame(GsGoods gsGoods,HttpServletRequest request, HttpServletResponse response, Model model) {
		String ids=(String) request.getSession().getAttribute("gsOfficeId");
		GsMenu gsMenu=new GsMenu();
		Page<GsGoods> page=new Page<GsGoods>(request, response);
		gsGoods.setPage(page);
		Office office=new Office();
		office.setId(ids);
		gsMenu.setOffice(office);
		if(!Global.getConfig("office_id").equals(ids)){
			gsGoods.setOffice(office);
		}
		List<GsGoods> gsGoodsList=gsGoodsService.findAllList(gsGoods);
		List<GsMenu> gsMenuList=gsMenuService.findAllList(gsMenu);
		page.setList(gsGoodsList);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("page", page);
		HttpSession session =request.getSession();
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		return "modules/shoppingmall/home/search";
	}
	/*
	 * 菜单栏进入的商品详情
	 */
	@RequestMapping(value = "gsMenuList")
	public String gsMenuList(GsMenu gsMenu,String id,String name,HttpServletRequest request, HttpServletResponse response, Model model) {
		GsGoods gsGoods=new GsGoods();
		gsGoods.setMenuId(id);
		Office office=new Office();
		String gsOfficeId=(String) request.getSession().getAttribute("gsOfficeId");
		if(!"".equals(gsOfficeId) && gsOfficeId != null){
			office.setId(gsOfficeId);
			if(Global.getConfig("office_id").equals(gsOfficeId)){
				gsGoods.setMenuId("");
				gsGoods.setName(name);
				gsMenu.setOffice(office);
			}else{
				gsGoods.setOffice(office);
				gsMenu.setOffice(office);
			}	
		}
		Page<GsGoods> page=new Page<GsGoods>(request, response);
		gsGoods.setPage(page);
		List<GsMenu> gsMenuList=gsMenuService.findAllList(gsMenu);
		List<GsGoods> gsGoodsList=gsGoodsService.findAllList(gsGoods);
		page.setList(gsGoodsList);
		model.addAttribute("page", page);
		model.addAttribute("gsMenu", gsMenu);
		model.addAttribute("gsMenuList", gsMenuList);
		model.addAttribute("gsGoods", gsGoods);
		model.addAttribute("id", id);
		HttpSession session =request.getSession();
		model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
		return "modules/shoppingmall/home/search";
	}
}