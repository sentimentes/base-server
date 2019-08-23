package com.wwls.modules.shoppingmall.web.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wwls.common.config.AlipayConfig;
import com.wwls.common.config.Global;
import com.wwls.common.utils.IdGen;
import com.wwls.common.web.BaseController;
import com.wwls.modules.shoppingmall.entity.goods.GsAddress;
import com.wwls.modules.shoppingmall.entity.goods.GsDj;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;
import com.wwls.modules.shoppingmall.entity.goods.GsShoppingCart;
import com.wwls.modules.shoppingmall.entity.goods.GsUser;
import com.wwls.modules.shoppingmall.service.goods.GsAddressService;
import com.wwls.modules.shoppingmall.service.goods.GsDjService;
import com.wwls.modules.shoppingmall.service.goods.GsGoodsService;
import com.wwls.modules.shoppingmall.service.goods.GsOrderService;
import com.wwls.modules.shoppingmall.service.goods.GsShoppingCartService;
import com.wwls.modules.shoppingmall.service.goods.GsUserService;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.service.OfficeService;

/**
 * 商场活动管理Controller
 * @author leixiaoming
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/shoppingmall/goods/user")
public class IoginController extends BaseController {

	@Autowired
	private GsUserService gsUserService;
	
	@Autowired
	private GsDjService gsDjService;
	
	@Autowired
	private GsAddressService gsAddressService;
		
	@Autowired
	private GsGoodsService gsGoodsService;
	
	Map<String ,String> map=new HashMap<String,String>();
	
	@Autowired
	private GsOrderService gsOrderService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private GsShoppingCartService gsShoppingCartService;
	
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		GsUser gsUser=new GsUser();
		model.addAttribute("gsUser", gsUser);
		return "modules/shoppingmall/home/login";
	}
	/*
	 * 用户登录
	 */
	@RequestMapping(value = "loginer")
	public String loginer(GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		if("".equals(gsUser.getName()) || gsUser.getName() == null){
			addMessage(redirectAttributes, "用户名不能为空");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}
		if("".equals(gsUser.getPassword()) || gsUser.getPassword() == null){
			addMessage(redirectAttributes, "密码不能为空");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}
		List<GsUser> gsUserList=gsUserService.findList(gsUser);
		String password=gsUser.getPassword();
		if(gsUserList.size()>0){
			for(GsUser u:gsUserList){
				if(u.getPassword().equals(password)){
					HttpSession session =request.getSession();
					session.setMaxInactiveInterval(3600);
					String gsOfficeId=(String) request.getSession().getAttribute("gsOfficeId");				
					session.setAttribute("name",u.getName());
					session.setAttribute("id",u.getId());
					return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/?repage";
				}else{
					addMessage(redirectAttributes, "密码错误");
					return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
				}
			}
		}else{
			addMessage(redirectAttributes, "用户不存在");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
	}
	@RequestMapping(value = "zPassword")
	public String zPassword(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		GsUser gsUser=new GsUser();
		model.addAttribute("gsUser", gsUser);
		return "modules/shoppingmall/home/password";
	}
	
	@RequestMapping(value = "zhPassword")
	public String zhPassword(String id,GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		List<GsUser> gsUserLset=gsUserService.findList(gsUser);
		if(gsUserLset.size()!=0){
			gsUser=gsUserLset.get(0);
			if(id !=null && !"".equals(id)){
				gsUser=gsUserService.get(id);
			}
			if("".equals(gsUser.getPasswordOne()) || gsUser.getPasswordOne() ==null || gsUser.getPasswordTwo() == null||"".equals(gsUser.getPasswordTwo())){
				addMessage(redirectAttributes, "你没有设置找回密码的问题，或者设置不完全，请联系管理员TEL：13387496739");
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/zPassword/?repage";
			}
			GsUser gsUser1=new GsUser();
			gsUser1.setId(gsUser.getId());
			gsUser1.setPasswordOne(gsUser.getPasswordOne());
			gsUser1.setPasswordTwo(gsUser.getPasswordTwo());
			model.addAttribute("gsUser", gsUser1);
			return "modules/shoppingmall/home/password";
		}else{
			addMessage(redirectAttributes, "你输入的账不存在");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/zPassword/?repage";
		}
	}
	
	@RequestMapping(value = "qdPassword")
	public String qdPassword(GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		GsUser gsUserOne=gsUserService.get(gsUser);
		if(gsUserOne.getAnswerOne().equals(gsUser.getAnswerOne())&&gsUserOne.getAnswerTwo().equals(gsUser.getAnswerTwo())){
			addMessage(redirectAttributes, "你的密码是:"+gsUserOne.getPassword());
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			addMessage(redirectAttributes, "你的答案不正确");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/zhPassword?repage&id="+gsUser.getId();
		}
		
	}
	
	
	@RequestMapping(value = "register")
	public String register(GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		model.addAttribute("gsUser", gsUser);
		return "modules/shoppingmall/home/register";
	}
	/*
	 * 用户添加
	 */
	@RequestMapping(value = "save")
	public String save(GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String password=gsUser.getPassword();
		String paawword2=gsUser.getBeiFei();
		String name=gsUser.getName();
		if("".equals(name)||"".equals(password)||"".equals(paawword2)){
			gsUser.setBeiFei("");
			model.addAttribute("gsUser", gsUser);
			addMessage(redirectAttributes, "账号密码不能为空！");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/register/?repage";
		}
		if(!password.equals(paawword2)){
			gsUser.setBeiFei("");
			model.addAttribute("gsUser", gsUser);
			addMessage(redirectAttributes, "两次密码不相同，请重新输入！");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/register/?repage";
		}
		List<GsUser> gsUserList=gsUserService.findList(gsUser);
		if(gsUserList.size()>0){
			addMessage(redirectAttributes, "该用户名已经存在！");
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/register/?repage";
		}else{
			gsUserService.save(gsUser);
			addMessage(redirectAttributes, "注册成功");
			model.addAttribute("gsUser", gsUser);
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}
	}
	/* 
	 * leixiaoming
	 * 商品直接购买
	 */
	@RequestMapping(value = "orderSave")
	public String orderSave(GsOrder gsOrder,HttpServletRequest request, HttpServletResponse response, Model model) {
		String ids=(String) request.getSession().getAttribute("id");
		GsDj gsDj=new GsDj();
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
		User user=new User();
		user.setId(ids);
		List<GsShoppingCart> GsShoppingCartList=gsOrder.getGsShoppingCartList();
		GsGoods gsGoods=new GsGoods();
		String addressId=gsOrder.getAddressId();
		GsAddress gsAddres=gsAddressService.get(addressId);
		gsOrder.setPepole(gsAddres.getName());
		gsOrder.setUpDownShelf(gsAddres.getUpDownShelf());
		gsOrder.setPhone(gsAddres.getPhone());
		String id=IdGen.uuid();
		HttpSession session =request.getSession();
		float sum=0;
		for(GsShoppingCart gsShoppingCart:GsShoppingCartList){
			GsOrder gsOrders=new GsOrder();
			String gsid=gsShoppingCart.getGsId();
			GsGoods gsGoods2=gsGoodsService.get(gsid);	
			String parameter=gsShoppingCart.getParameter();
			String number=gsShoppingCart.getNumber();
			String gsName=gsShoppingCart.getGsName();
			String salePrice=gsShoppingCart.getSalePrice();
			gsOrders.setAddressId(gsOrder.getAddressId());
			gsOrders.setAddressName(gsAddres.getName());
			gsOrders.setLogisticsName(gsOrders.getLogisticsName());
			gsOrders.setGsId(gsid);
			gsOrders.setCreateBy(user);
			gsOrders.setOffice(gsGoods2.getOffice());
			gsOrders.setParameter(parameter);
			gsOrders.setGsNumber(number);
			gsOrders.setGsName(gsName);
			gsOrders.setLogisticsName(gsOrder.getLogisticsName());
			gsOrders.setId(id);
			gsOrders.setUpDownShelf("0");
			gsOrders.setCreateDate(new Date());
			if(!"".equals(ids)&& ids !=null ){
				gsOrder.setPepole(ids);
				Integer sss=gsOrderService.getjf(gsOrder);
				if(!"".equals(sss)&& sss !=null ){
					gsDj.setStart(sss);
					gsDj.setOffice(gsGoods2.getOffice());
					List<GsDj> gsDjList=gsDjService.findList(gsDj);
					if(gsDjList.size()>0){
					gsDj=gsDjList.get(0);
					session.setAttribute("zk",gsDj.getZk());
					}else{
						session.setAttribute("zk","1");
					}
				}
			}
			if(!"".equals(gsDj.getZk())&& gsDj.getZk() !=null ){
				float price=Float.parseFloat(salePrice)*Float.parseFloat(gsDj.getZk());
				gsOrders.setPrice(String.valueOf(price));
			}else{
				gsOrders.setPrice(salePrice);
			}
			sum=sum+Float.parseFloat(salePrice);
			gsOrderService.add(gsOrders);
			gsShoppingCartService.delete(gsShoppingCart);
		}
		gsOrder.setPrice(Float.toString(sum));
		model.addAttribute("gsOrder", gsOrder);
		model.addAttribute("gsGoods", gsGoods);
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/goAlipay/?repage&orderId="+id+"&sum="+sum;
		}
	}
	/*
	 * 删除购物车
	 */
	@RequestMapping(value = "deleteShoppingCart")
	public String deleteShoppingCart(GsShoppingCart gsShoppingCart,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		gsShoppingCartService.delete(gsShoppingCart);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex/shopcart/?repage";
	}
	/*
	 * 退出
	 */
	@RequestMapping(value = "beark")
	public String beark(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		request.getSession().removeAttribute("name");
		request.getSession().removeAttribute("id");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/gindex?repage";
	}
	/*
	 *进入个人信息页面
	 */
	@RequestMapping(value = "information")
	public String information(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsUser  gsUser=new GsUser();
			gsUser.setId(ids);
			gsUser=gsUserService.get(gsUser);
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsUser", gsUser);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/information";
		}
		
	}
	@RequestMapping(value = "updateUser")
	public String updateUser(GsUser gsUser,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			gsUserService.save(gsUser);
			GsGoods gsGoods=new GsGoods();
			addMessage(redirectAttributes, "修改成功");
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsUser", gsUser);
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/information?repage";
		}
	}
	
	/*
	 *进入个人主页安全设置页面
	 */
	@RequestMapping(value = "anquan")
	public String anquan(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsUser  gsUser=new GsUser();
			gsUser.setId(ids);
			gsUser=gsUserService.get(gsUser);
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			HttpSession session =request.getSession();
			model.addAttribute("gsUser", gsUser);
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/safety";
		}
		
	}
	/*
	 *进入个人主页地址页面
	 */
	@RequestMapping(value = "address")
	public String address(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsAddress gsAddress=new GsAddress();
			gsAddress.setPepole(ids);
			GsGoods gsGoods=new GsGoods();
			List<GsAddress> gsAddressList= gsAddressService.findList(gsAddress);
			model.addAttribute("gsAddressList", gsAddressList);
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsAddress", gsAddress);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/address";
		}
		
	}
	
	/*
	 *地址修改
	 */
	@RequestMapping(value = "addressup")
	public String addressup(GsAddress gsAddress,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			gsAddress.setPepole(ids);
			GsGoods gsGoods=new GsGoods();
			List<GsAddress> gsAddressList= gsAddressService.findList(gsAddress);
			gsAddress=gsAddressService.get(gsAddress);
			model.addAttribute("gsAddressList", gsAddressList);
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsAddress", gsAddress);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/address";
		}
		
	}
	
	
	/*
	 *进入个人主页订单页面
	 */
	@RequestMapping(value = "dingdan")
	public String dingdan(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsOrder gsOrder=new GsOrder();
			gsOrder.setOrderId(ids);
			List<GsOrder> gsOrderLists=gsOrderService.findList(gsOrder);
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsOrderLists", gsOrderLists);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/order";
		}
		
	}
	
	
	/*
	 *进入修改密码页面
	 */
	@RequestMapping(value = "upPassword")
	public String upPassword(String ss,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsUser gsUser=new GsUser();
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsUser", gsUser);
			model.addAttribute("ss", ss);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/password";
		}
		
	}
	/*
	 *修改密码
	 */
	@RequestMapping(value = "updatepassword")
	public String updatepassword(GsUser gsUser,String ss,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			gsUser.setId(ids);
			String passwords=gsUser.getPasswords();
			String passwordes=gsUser.getPasswordes();
			if("".equals(passwords) || "".equals(passwordes)){
				model.addAttribute("gsUser", gsUser);
				addMessage(redirectAttributes, "密码不能为空");
				return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/upPassword?repage";
			}else{
				if(passwords.equals(passwordes)){
					if(gsUserService.get(ids).getPassword().equals(gsUser.getPassword())){
						gsUserService.upPassword(gsUser);
						ss="1";
						addMessage(redirectAttributes, "修改密码成功！");
						GsGoods gsGoods=new GsGoods();
						model.addAttribute("gsGoods", gsGoods);
						model.addAttribute("ss", ss);
						HttpSession session =request.getSession();
						model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
						return "modules/shoppingmall/user/password";
					}else{
						
						addMessage(redirectAttributes, "你输入的密码错误");
						return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/upPassword?repage";
					}
				}else{
					gsUser.setPasswordes("");
					model.addAttribute("gsUser", gsUser);
					addMessage(redirectAttributes, "两次密码不相同");
					return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/upPassword?repage";
				}
			}
			
		}
		
	}
	
	
	/*
	 *安全问题
	 */
	@RequestMapping(value = "question")
	public String question(String ss,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsUser gsUser=new GsUser();
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsUser", gsUser);
			model.addAttribute("ss", ss);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/question";
		}
		
	}
	/**
	 * 进入个人主页
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "people")
	public String people(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
			GsOrder gsOrder=new GsOrder();
			gsOrder.setOrderId(ids);
			GsUser  gsUser=new GsUser();
			gsUser.setId(ids);
			gsUser=gsUserService.get(gsUser);
			List<GsOrder> gsOrderLists=gsOrderService.findList(gsOrder);
			List<GsOrder> gsOrderList=new ArrayList<GsOrder>();
			if(gsOrderLists.size()>0){
				gsOrderList.add(gsOrderLists.get(0));
			}
			if(gsOrderLists.size()>1){
				gsOrderList.add(gsOrderLists.get(1));
			}
			GsGoods gsGoods=new GsGoods();
			model.addAttribute("gsGoods", gsGoods);
			model.addAttribute("gsUser", gsUser);
			model.addAttribute("gsOrderList", gsOrderList);
			HttpSession session =request.getSession();
			model.addAttribute("office",officeService.get((String)session.getAttribute("gsOfficeId")));
			return "modules/shoppingmall/user/index";
		}
		
	}
	
	/**
	 * @Description: 第三段 前往支付宝第三方网关进行支付
	 * @version V1.0
	 */
	
	@RequestMapping(value = "goAlipay", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String goAlipay(String orderId,String sum, HttpServletRequest request, HttpServletRequest response) throws Exception {
 
		GsOrder gsOrder=new GsOrder();
		gsOrder.setId(orderId);
		List<GsOrder> orderList =gsOrderService.findList(gsOrder);
		float price=0;
		StringBuilder gsNmae = new StringBuilder();
		for(GsOrder order:orderList){
			GsGoods goods = gsGoodsService.get(order.getGsId());
			price=price+Float.parseFloat(goods.getSalePrice());
			gsNmae.append(goods.getName()+",");
		}

		gsNmae.deleteCharAt(gsNmae.length()-1);
		
		//获得初始化的AlipayClient
		String zk=(String) request.getSession().getAttribute("zk");
		if(zk != null && !"".equals(zk)){
			price=Float.parseFloat(sum)*Float.parseFloat(zk);
		}
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
				//设置请求参数
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
				alipayRequest.setReturnUrl(AlipayConfig.return_url);
				alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
				
				//商户订单号，商户网站订单系统中唯一订单号，必填
				String out_trade_no = new String(orderId.getBytes("ISO-8859-1"),"UTF-8");
				//付款金额，必填
				String total_amount = new String(String.valueOf(price).getBytes("ISO-8859-1"),"UTF-8");
				//订单名称，必填
				String subject = new String(gsNmae.toString().getBytes("ISO-8859-1"),"UTF-8");
				//商品描述，可空
				String body = new String("ssss".getBytes("ISO-8859-1"),"UTF-8");
				
				alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
						+ "\"total_amount\":\""+ total_amount +"\"," 
						+ "\"subject\":\""+ subject +"\"," 
						+ "\"body\":\""+ body +"\"," 
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
				
				
				//请求
				String result = alipayClient.pageExecute(alipayRequest).getBody();
				
				//输出
				return result;
	}
	
	@RequestMapping(value = "goAlipays", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String goAlipays(String orderId,String sum, HttpServletRequest request, HttpServletRequest response) throws Exception {
 
		GsOrder gsOrder=new GsOrder();
		gsOrder.setId(orderId);
		List<GsOrder> orderList =gsOrderService.findList(gsOrder);
		float price=0;
		StringBuilder gsNmae = new StringBuilder();
		for(GsOrder order:orderList){
			GsGoods goods = gsGoodsService.get(order.getGsId());
			price=price+(Float.parseFloat(goods.getSalePrice())*Float.parseFloat(order.getGsNumber()));
			gsNmae.append(goods.getName()+",");
		}
		String zk=(String) request.getSession().getAttribute("zk");
		if(zk != null && !"".equals(zk)){
			price=price*Float.parseFloat(zk);
		}
		gsNmae.deleteCharAt(gsNmae.length()-1);
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
				//设置请求参数
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
				alipayRequest.setReturnUrl(AlipayConfig.return_url);
				alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
				
				//商户订单号，商户网站订单系统中唯一订单号，必填
				String out_trade_no = new String(orderId.getBytes("ISO-8859-1"),"UTF-8");
				//付款金额，必填
				String total_amount = new String(String.valueOf(price).getBytes("ISO-8859-1"),"UTF-8");
				//订单名称，必填
				String subject = new String(gsNmae.toString().getBytes("ISO-8859-1"),"UTF-8");
				//商品描述，可空
				String body = new String("ssss".getBytes("ISO-8859-1"),"UTF-8");
				
				alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
						+ "\"total_amount\":\""+ total_amount +"\"," 
						+ "\"subject\":\""+ subject +"\"," 
						+ "\"body\":\""+ body +"\"," 
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
				
				//请求
				String result = alipayClient.pageExecute(alipayRequest).getBody();
				
				//输出
				return result;
	}
	
	@RequestMapping(value = "alipayReturnNotice")
	public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
 
		logger.info("支付成功, 进入同步通知接口...");
 
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = false;
		try{
			
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,AlipayConfig.sign_type); //调用SDK验证签名
		}catch (Exception e) {
			System.out.println("SDK验证签名出现异常");
		}
 
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			GsOrder gsOrder=new GsOrder();
			gsOrder.setOrderId(out_trade_no);
			gsOrder.setUpDownShelf("1");
			gsOrder.setTradeNo(trade_no);
			// 修改订单状态，改为 支付成功，已付款; 同时新增支付流水
			gsOrderService.updateStatus(gsOrder);
			logger.info("********************** 支付成功(支付宝同步通知) **********************");
			logger.info("* 订单号: {}"+out_trade_no);
			logger.info("* 支付宝交易号: {}"+trade_no);
			logger.info("* 实付金额: {}"+total_amount);
		}
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/people?repage";
	}
	/*
	 * 收货
	 */
	@RequestMapping(value = "shouh")
	public String shouh(String ss,GsOrder gsOrder, Model model,HttpServletRequest request, HttpServletRequest response, RedirectAttributes redirectAttributes) {
		if (gsOrder.getIsNewRecord()) {
			gsOrder.setOffice(gsOrder.getCurrentUser().getOffice());
		}
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
		gsOrderService.shouh(gsOrder);
		addMessage(redirectAttributes, "收货成功");
		if("1".equals(ss)){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/people?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/dingdan?repage";
		}
		
		}
		
	}
	@RequestMapping(value = "answerSave")
	public String answerSave(GsUser gsUser,String ss, Model model,HttpServletRequest request, HttpServletRequest response, RedirectAttributes redirectAttributes) {
		String ids=(String) request.getSession().getAttribute("id");
		if("".equals(ids) || ids == null){
			return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/?repage";
		}else{
		ss="1";
		gsUser.setId(ids);
		gsUserService.answerSave(gsUser);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/shoppingmall/goods/user/question?repage&ss="+ss;
		}
		
	}
}