package com.wwls.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wwls.common.beanvalidator.BeanValidators;
import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.utils.DateUtils;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.utils.excel.ExportExcel;
import com.wwls.common.utils.excel.ImportExcel;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.dao.UserDao;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.entity.Role;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.service.SystemService;
import com.wwls.modules.sys.utils.FilterString;
import com.wwls.modules.sys.utils.LogUtils;
import com.wwls.modules.sys.utils.PwdUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 用户Controller
 * @author 
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	/**
	 * 用户信息修改表单
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}
	
	/**
	 * 用户信息新增表单
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "addForm")
	public String addForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userAddForm";
	}
	
	/**
	 * 用户权限配置表单
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "peForm")
	public String peForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userPeForm";
	}

	/**
	 * 用户信息修改
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "updateInfo")
	public String updateInfo(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		logger.info("修改用户名=="+user.getName());
		if(user.getDelFlag().equals("1")){
			addMessage(model, "保存失败，已注销用户不允许修改");
			return form(user, model);
		}
		
		if(user.getLoginFlag().equals("3")){
			user.setDelFlag("1");
		}
		String filterStr = FilterString.FilterStr(user.getEmail());
		user.setEmail(filterStr);
		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd = PwdUtils.decryptPsw(user.getNewPassword());//整密码串
		String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
		//String pwdStr = pwd.substring(0,pwd.indexOf(","));//密文密码
		
		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd1 = PwdUtils.decryptPsw(user.getLoginName());//整密码串
		String pwdMing1 = pwd1.substring(pwd1.indexOf(",")+1);//明文密码
		
		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd2 = PwdUtils.decryptPsw(user.getOldLoginName());//整密码串
		String pwdMing2 = pwd2.substring(pwd2.indexOf(",")+1);//明文密码
		
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(pwdMing)) {
			user.setPassword(SystemService.entryptPassword(pwdMing));
		}
		if (StringUtils.isNotBlank(pwdMing)) {
			//如果密码低于8位数，消息提醒
			if(pwdMing.length()<8 || pwdMing.length()>20){
				addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录密码不能低于8位,高于20位");
				return form(user, model);
			}
			
			//判断用户的密码中是否含有下划线，数字、字母
		    // 编译正则表达式matches("[0-9A-Za-z_]*")
		    Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{7,21}$");
		    Matcher matcher = pattern.matcher(pwdMing);
		    // 字符串是否与正则表达式相匹配
		    boolean rs = matcher.matches();
		    if(rs==false){
		    	addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录密码中需要包含下划线、字母和数字，长度8到20位");
				return form(user, model);
		    }else{
		    	  Pattern pattern1 = Pattern.compile("(.)\\1+");
				  Matcher matcher1 = pattern1.matcher(pwdMing);
				  while (matcher1.find()) {  
			            String u= pwdMing.substring(matcher1.start(), matcher1.end());
			            if(u.length()>3){
			            	addMessage(model, "保存用户'" + pwdMing1 + "'失败，密码中同一字符不能连续出现三次");
							return form(user, model);
			            }
			        }
		    }
		    //判断用户名是否与密码相同
		    if(pwdMing.equals(pwdMing1)){
		    	addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录密码和用户名禁止相同");
				return form(user, model);
		    }
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(pwdMing2, pwdMing1))){
			addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		if(roleIdList!=null && roleIdList.size()>0){
			for (Role r : systemService.findAllRole()){
				if (roleIdList.contains(r.getId())){
					roleList.add(r);
				}
			}
			
			user.setRoleList(roleList);
		}
		String updateBegin = "";//修改前数据
		String updateAfter = "";//修改后数据
		User users = systemService.getUser(user.getId());
		if(users!=null){
			updateBegin = "[归属公司]="+users.getCompany().getName()+		
			"[归属部门]="+users.getOffice().getName()+
			"[登录名]="+pwdMing1+
			"[姓名]="+users.getName()+
			"[邮箱]="+users.getEmail();
		}
		user.setLoginName(pwdMing1);
		// 保存用户信息
		systemService.saveOrUpdate(user,user.getNewPassword());
		//System.out.println("00001=="+user.getPassword());
		updateAfter = "[归属公司]="+user.getCompany().getName()+		
				"[归属部门]="+user.getOffice().getName()+
				"[登录名]="+pwdMing1+
				"[姓名]="+user.getName()+
				"[邮箱]="+user.getEmail();
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-修改", updateBegin, updateAfter,"1","5");
		// 清除当前用户缓存
		if (pwdMing1.equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + pwdMing1 + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	
	/**
	 * 用户权限配置
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:pz")
	@RequestMapping(value = "peSave")
	public String peSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		//获取当前用户的ID，若是相同，提示不能自己给自己赋值权限
		
		if(UserUtils.getUser().getId().equals(user.getId())){
			UserUtils.clearCache();
			addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'失败，禁止自己给自己赋值权限");
			return "redirect:" + adminPath + "/sys/user/peForm?id"+user.getId();
			
		}
		String roleName = "";
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		if(roleIdList!=null && roleIdList.size()>0){
			for (Role r : systemService.findAllRole()){
				if (roleIdList.contains(r.getId())){
					roleList.add(r);
					roleName=r.getName();
				}
			}
			user.setRoleList(roleList);
		}else{
			UserUtils.clearCache();
			addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'失败，请选择权限");
			return "redirect:" + adminPath + "/sys/user/peForm?id"+user.getId();
		}
		
		String updateBegin = "";//修改前数据
		String updateAfter = "";//修改后数据
		User users = systemService.getUser(user.getId());
		if(users!=null){
			updateBegin = "[登录名]="+users.getLoginName()+"[配置]="+roleName+"[权限]";
			
		}
		// 保存用户信息
		systemService.pzSave(user);
		updateAfter = "[为登录名]="+user.getLoginName()+"[配置]="+roleName+"[权限]";
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-权限配置", updateBegin, updateAfter,"1","5");
		
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 用户信息添加
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "addSave")
	public String addSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if(user.getLoginFlag().equals("3")){
			user.setDelFlag("1");
		}
		//request.setCharacterEncoding("utf-8");//设置参数的编码格式
		logger.info("添加用户名=="+user.getName());
		String filterStr = FilterString.FilterStr(user.getEmail());
		user.setEmail(filterStr);
		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd = PwdUtils.decryptPsw(user.getNewPassword());//整密码串
	
		String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
		//String pwdStr = pwd.substring(0,pwd.indexOf(","));//密文密码
		String pwd1 = PwdUtils.decryptPsw(user.getLoginName());//整密码串
		String pwdMing1 = pwd1.substring(pwd1.indexOf(",")+1);//明文密码
		String pwd2 = PwdUtils.decryptPsw(user.getOldLoginName());//整密码串
		String pwdMing2 = pwd2.substring(pwd2.indexOf(",")+1);//明文密码
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(pwdMing)) {
			
			user.setPassword(SystemService.entryptPassword(pwdMing));
		}
		if (StringUtils.isNotBlank(pwdMing)) {
			//如果密码低于8位数，消息提醒
			if(pwdMing.length()<8 || pwdMing.length()>20){
				addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录密码不能低于8位,高于20位");
				return addForm(user, model);
			}
			
			//判断用户的密码中是否含有下划线，数字、字母
		    //编译正则表达式
		    Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{7,21}$");
		    Matcher matcher = pattern.matcher(pwdMing);
		    // 字符串是否与正则表达式相匹配
		    boolean rs = matcher.matches();
		    if(rs==false){
		    	addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录密码中需要包含下划线、字母和数字，长度8到20位");
				return addForm(user, model);
		    }else{
		    	  Pattern pattern1 = Pattern.compile("(.)\\1+");
				  Matcher matcher1 = pattern1.matcher(pwdMing);
				  while (matcher1.find()) {  
			            String u= pwdMing.substring(matcher1.start(), matcher1.end());
			            if(u.length()>3){
			            	addMessage(model, "保存用户'" + pwdMing1 + "'失败，密码中同一字符不能连续出现三次");
							return form(user, model);
			            }
			        }
		    }
		    //判断用户名是否与密码相同
		    if(pwdMing.equals(pwdMing1)){
		    	addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录密码和用户名禁止相同");
				return addForm(user, model);
		    }
		}
		
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(pwdMing2, pwdMing1))){
			addMessage(model, "保存用户'" + pwdMing1 + "'失败，登录名已存在");
			return addForm(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		if(roleIdList!=null && roleIdList.size()>0){
			for (Role r : systemService.findAllRole()){
				if (roleIdList.contains(r.getId())){
					roleList.add(r);
				}
			}
			user.setRoleList(roleList);
		}
		user.setLoginName(pwdMing1);
		// 保存用户信息
		systemService.saveOrUpdate(user,user.getNewPassword());
		//System.out.println("00002=="+user.getPassword());
		String updateBegin = "[归属公司]="+user.getCompany().getName()+		
				"[归属部门]="+user.getOffice().getName()+
				"[登录名]="+pwdMing1+
				"[姓名]="+user.getName()+
				"[邮箱]="+user.getEmail();
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-新增", updateBegin, null,"1","5");
		// 清除当前用户缓存
		if (pwdMing1.equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + pwdMing1 + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 用户信息删除
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request,User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		user.setLoginFlag("3");
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		String updateBegin = "[删除登录名]="+user.getLoginName()+"[的数据]";
				
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-删除", updateBegin, null,"1","5");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("user")
	@RequiresPermissions("sys:users:view")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	
	
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("user")
	@RequiresPermissions("sys:users:edit")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(HttpServletRequest request,String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
			String pwd = PwdUtils.decryptPsw(newPassword);//整密码串
			String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
			//String pwdStr = pwd.substring(0,pwd.indexOf(","));//密文密码
			
			String pwdD = PwdUtils.decryptPsw(oldPassword);//整密码串
			String pwdMingD = pwdD.substring(pwdD.indexOf(",")+1);//明文密码
			//1、如果密码低于8位数，消息提醒
			if(pwdMing.length()<8 || pwdMing.length()>20){
				model.addAttribute("message", "修改密码失败，登录密码不能低于8位,高于20位");
				model.addAttribute("user", user);
				return "modules/sys/userModifyPwd";
			}
			
			//2、判断用户的密码中是否含有下划线，数字、字母
		    // 编译正则表达式
		    Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{7,21}$");
		    Matcher matcher = pattern.matcher(pwdMing);
		    // 3、字符串是否与正则表达式相匹配
		    boolean rs = matcher.matches();
		    if(rs==false){
		    	model.addAttribute("message", "修改密码失败，登录密码中需要包含下划线、字母和数字，长度8到20位");
		    	model.addAttribute("user", user);
				return "modules/sys/userModifyPwd";
		    }else{
		    	  Pattern pattern1 = Pattern.compile("(.)\\1+");
				  Matcher matcher1 = pattern1.matcher(pwdMing);
				  while (matcher1.find()) {  
			            String u= pwdMing.substring(matcher1.start(), matcher1.end());
			            if(u.length()>3){
			            	model.addAttribute("message","保存用户'" + user.getLoginName() + "'失败，密码中同一字符不能连续出现三次");
			            	return "modules/sys/userModifyPwd";
			            }
			        }
		    }
		    
		    //判断用户名是否与密码相同
		    if(pwdMing.equals(user.getLoginName())){
		    	model.addAttribute("message", "修改密码失败，登录密码和用户名禁止相同");
		    	model.addAttribute("user", user);
				return "modules/sys/userModifyPwd";
		    }
			if (SystemService.validatePassword(pwdMingD, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword,pwdMing);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		LogUtils.saveLogUser(request,"我的面板-个人信息-密码管理-修改", null, null,"1","5");
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	
	/**
	 * 密码查看表单
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:users:view")
	@RequestMapping(value = "lookForm")
	public String lookForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userModifyPwd";
	}
	
	/**
	 * 新用户或者是过期用户登录强制请求修改密码口令
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("sys:user:edit")
	@RequiresPermissions("user")
	@RequestMapping(value = "updatePwd")
	public String updatePwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/sysIndex";
			}
			//解密后截取的md5秘闻传给sha-1生成密文存数据库
			String pwd = PwdUtils.decryptPsw(newPassword);//整密码串
			String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
			//String pwdStr = pwd.substring(0,pwd.indexOf(","));//密文密码
			
			//解密后截取的md5秘闻传给sha-1生成密文存数据库
			String pwdD = PwdUtils.decryptPsw(oldPassword);//整密码串
			String pwdMingD = pwdD.substring(pwdD.indexOf(",")+1);//明文密码
			//String pwdStrD = pwdD.substring(0,pwdD.indexOf(","));//密文密码
				//1、如果密码低于8位数，消息提醒
				if(pwdMing.length()<8 || pwdMing.length()>20){
					model.addAttribute("message", "修改密码失败，登录密码不能低于8位,高于20位");
					model.addAttribute("user", user);
					return "modules/sys/updatePwd";
				}
				
				//2、判断用户的密码中是否含有下划线，数字、字母
			    // 编译正则表达式
			    Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{7,21}$");
			    Matcher matcher = pattern.matcher(pwdMing);
			    // 3、字符串是否与正则表达式相匹配
			    boolean rs = matcher.matches();
			    if(rs==false){
			    	model.addAttribute("message", "修改密码失败，登录密码中需要包含下划线、字母和数字，长度8到20位");
			    	model.addAttribute("user", user);
					return "modules/sys/updatePwd";
			    }else{
			    	  Pattern pattern1 = Pattern.compile("(.)\\1+");
					  Matcher matcher1 = pattern1.matcher(pwdMing);
					  while (matcher1.find()) {  
				            String u= pwdMing.substring(matcher1.start(), matcher1.end());
				            if(u.length()>3){
				            	model.addAttribute("message","保存用户'" + user.getLoginName() + "'失败，密码中同一字符不能连续出现三次");
				            	return "modules/sys/updatePwd";
				            }
				        }
			    }
			    
			    //判断用户名是否与密码相同
			    if(pwdMing.equals(user.getLoginName())){
			    	model.addAttribute("message", "修改密码失败，登录密码和用户名禁止相同");
			    	model.addAttribute("user", user);
					return "modules/sys/updatePwd";
			    }
			
			if (SystemService.validatePassword(pwdMingD, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(),newPassword,pwdMing);
				model.addAttribute("message", "修改密码成功");
				
				//修改用户的登录状态，表示用户已经登录过
				User us = new User();
				us.setId(user.getId());
				us.setIsLogin("1");
				us.setUpdatePwdDate(new Date());
				userDao.updateStatus(us);
				UserUtils.clearCache(us);
				UserUtils.getSubject().logout();
				return "modules/sys/sysLogin";
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
				model.addAttribute("user", user);
				return "modules/sys/updatePwd";
			}
		}
		model.addAttribute("message", "密码不能为空");
		model.addAttribute("user", user);
		return "modules/sys/updatePwd";
	}
	
	/**
	 * 监控在线人数
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:jk")
	@RequestMapping(value = "jkList")
	public String jkList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser1(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/jkUserList";
	}
	
	
	/**
	 * 用户信息已注销用户删除
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "deleteUser")
	public String deleteUser(HttpServletRequest request,User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		user.setLoginFlag("3");
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除失败，已注销用户不允许删除");
		}
		String updateBegin = "[删除登录名]="+user.getLoginName()+"[的数据]";
				
		LogUtils.saveLogUser(request,"系统设置-机构用户-用户管理-删除", updateBegin, null,"1","5");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
}
