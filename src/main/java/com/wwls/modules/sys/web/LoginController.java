package com.wwls.modules.sys.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Maps;
import com.wwls.common.config.Global;
import com.wwls.common.mail.MailSendUtils;
import com.wwls.common.security.shiro.session.SessionDAO;
import com.wwls.common.servlet.ValidateCodeServlet;
import com.wwls.common.utils.CacheUtils;
import com.wwls.common.utils.CookieUtils;
import com.wwls.common.utils.DateUtils;
import com.wwls.common.utils.IdGen;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.common.web.Servlets;
import com.wwls.modules.sys.dao.SysOnlineUserDao;
import com.wwls.modules.sys.dao.SysTokenDao;
import com.wwls.modules.sys.dao.SysUserTokenDao;
import com.wwls.modules.sys.dao.UserDao;
import com.wwls.modules.sys.entity.SysOnlineUser;
import com.wwls.modules.sys.entity.SysToken;
import com.wwls.modules.sys.entity.SysUserAttribute;
import com.wwls.modules.sys.entity.SysUserToken;
import com.wwls.modules.sys.entity.SystemConfig;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.security.FormAuthenticationFilter;
import com.wwls.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.wwls.modules.sys.service.SysUserAttributeService;
import com.wwls.modules.sys.service.SystemConfigService;
import com.wwls.modules.sys.utils.LogUtils;
import com.wwls.modules.sys.utils.PwdUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SysUserAttributeService sysUserAttributeService;

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SysUserTokenDao sysUserTokenDao;
	
	@Autowired
	private SysOnlineUserDao sysOnlineUserDao;
	
	@Autowired
	private SysTokenDao sysTokenDao;
	private static UserDao userDao1 = SpringContextHolder.getBean(UserDao.class);
	private static SysUserAttributeService sysUserAttributeService1 = SpringContextHolder.getBean(SysUserAttributeService.class);
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
		
		
//		if(principal != null && !principal.isMobileLogin()){
//			return "redirect:" + adminPath;
//		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "redirect:" + adminPath;
		}
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		//解密加密的用户名
		String pwd1 = PwdUtils.decryptPsw(username);//整密码串
		String pwdMing1 = pwd1.substring(pwd1.indexOf(",")+1);//明文密码
		
		User user = new User();
		user.setLoginName(pwdMing1);
		User users = userDao.getByLoginName(user);
		
		if(users!=null){
			// 记录登录日志
			LogUtils.saveLogNotUser(Servlets.getRequest(),null,null,"登录失败，用户或密码错误", "1",users.getId());
		}
		
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
			message = "用户或密码错误或被锁定, 请重试.";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()) {
			logger.debug("login fail, active session size: {}, message: {}, exception: {}",sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)) {
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));

			/**
			 * 每次发生异常，异常次数都+1；登录成功后异常次数更新为零即可 根据用户名称id更新数据
			 */
			
			if (users != null) {
				SysUserAttribute sysUserAttribute = new SysUserAttribute();
				sysUserAttribute.setUserId(users.getId());
				SysUserAttribute sy = sysUserAttributeService.get(users.getId());
				if (sy != null) {
					if (sy.getTimess() != null && sy.getAbnormalTimes() != null) {
						sysUserAttribute.setTimess(sy.getTimess() + 1);
					} else {
						sysUserAttribute.setTimess(1);
					}
					sysUserAttributeService.updateTimes(sysUserAttribute);

					/**
					 * 当达到锁定的次数后更新锁定时间
					 */
					if (sy.getLockTime() != null) {
						if (sysUserAttribute.getTimess() == sy.getAbnormalTimes()) {
							// 更新锁定账户的时间
							SysUserAttribute sysUserAttribute2 = new SysUserAttribute();
							sysUserAttribute2.setLockStartTime(DateUtils.formatDateTime(new Date()));
							sysUserAttribute2.setUserId(users.getId());
							sysUserAttributeService.updateStatus(sysUserAttribute2);

							//当用户被锁定之后，同时也更新用户的锁定状态
							User u2 = new User();
							u2.setLoginFlag("2");//锁定状态
							u2.setId(users.getId());
							userDao.updateLoginFlag(u2);
							// 若有邮件，则发送邮件提示用户且跳转页面
							SystemConfig config = systemConfigService.get("1");
							if (config != null) {
								// 邮箱不空的时候才会给发邮件
								if (StringUtils.isNotBlank(users.getEmail())) {
									
									boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), ""+config.getPort(),config.getMailName(), config.getMailPassword(), users.getEmail(), "登录失败","您的账号"+users.getLoginName()+"因连续登录失败被锁定", "1");
									if (isSuccess) {
										// 记录登录日志
										LogUtils.saveLog(Servlets.getRequest(),"邮件发送成功");
									} else {
										LogUtils.saveLog(Servlets.getRequest(),"邮件发送失败");
									}
								}
							}
							UserUtils.clearCache(users);
							//UserUtils.clearCache(u);
							//UserUtils.clearCache();
							UserUtils.getSubject().logout();
							//return "redirect:" + adminPath+"/login";
							//return "redirect:" + adminPath + "/loginIndex";
							//return "modules/sys/sysLogin";
							// 记录登录日志
							LogUtils.saveLogNotUser(Servlets.getRequest(),"登录失败，账号被锁定", "1",users.getId());
							model.addAttribute("message", "您的账号已被锁定，请稍后再试");
							return "modules/sys/sysLogin";
						}
					}
				}
			}
		}
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile) {
			return renderString(response, model);
		}
		return "modules/sys/sysLogin";
	}
	
	
	/**
	 * 退出用户
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws IOException {
		Principal principal = UserUtils.getPrincipal();
		LogUtils.saveLog(Servlets.getRequest(), "退出登录");
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			//用户注销的时候将登陆的初始登录ip更新为空
			User user = new User();
			user.setId(principal.getId());
			User u = userDao.get(user);
			
			User users = new User();
			users.setStartLoginIp("");
			users.setLoginName(u.getLoginName());
			userDao.updateStartLoginIp(users);
			
			//在用户退出时，清空掉用户的登录痕迹
			SysUserToken sysUserToken = new SysUserToken();
			sysUserToken.setTokenName(u.getLoginName());
			sysUserTokenDao.delete(sysUserToken);
			
			//用户退出时，同时也清除在线用户表中的在线用户
			SysOnlineUser onlineUser = new SysOnlineUser();
			onlineUser.setUserId(u.getId());
			sysOnlineUserDao.delete(onlineUser);
			
			UserUtils.clearCache();
			UserUtils.getSubject().logout();
		}
	   // 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if(	ajax!=null){
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		 return "redirect:" + adminPath+"/login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		
		CookieUtils.setCookie(response, "jeesite.session.id", IdGen.uuid(),"/ec-power-servers");
	
		/**
		 * 第一次登陆成功后，才记录密码、用户名、登成功后检索下，若有，继续登陆、若没有，退出重新登陆
		 * 这个值只有在登陆成功成功后记录，退出时删除
		 */
		Principal principal = UserUtils.getPrincipal();
		
		User user = new User();
		user.setLoginName(principal.getLoginName());
		User u = userDao.getByLoginName(user);
		

		/**
		 * 登陆成功后先判断用户是否是第一次登陆或者口令期限有没有过期，
		 * 1如果过期，强制修改口令
		 * 2如果不过期是第一次登陆，还是强制修改口令
		 */
		
		java.util.Date now = null;
		java.util.Date date = null;
		if (u != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//判断有没有超时
			SysToken sys = new SysToken();
			sys.setUserName(u.getLoginName());
			sys.setPassword(u.getPassword());
			List<SysToken> sList = sysTokenDao.findList(sys);
			if(sList!=null && sList.size()>0){
				for(SysToken s:sList){
					
					String d44 = DateUtils.formatDateTime(s.getAddTime());//访问开始时间
					long times = 0;
					
					try {
						times = System.currentTimeMillis() - df.parse(d44).getTime();
						logger.info("现在时间=="+System.currentTimeMillis());
						logger.info("创建时间=="+df.parse(d44).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					logger.info("时间差=="+times);
					
					if(times<=1000000){
						sysTokenDao.delete(s);
					}else {
						sysTokenDao.delete(s);
						UserUtils.getSubject().logout();
						UserUtils.clearCache();
						model.addAttribute("message", "登陆超时，请稍后再试");
						return "modules/sys/sysLogin";
					}
				}
			}
//			else{
//				UserUtils.getSubject().logout();
//				model.addAttribute("message", "访问不合法，请稍后再试");
//				return "modules/sys/sysLogin";
//			}
				
			
			//0、一旦登录成功，将异常锁定次数更新为0即可
			SysUserAttribute sysUserAttribute1 = new SysUserAttribute();
			sysUserAttribute1.setUserId(u.getId());
			sysUserAttribute1.setTimess(0);
			sysUserAttributeService.updateTimes(sysUserAttribute1);
			
			// 1、判断用户是否是第一次登陆
			if (u.getIsLogin().equals("0")) {
				model.addAttribute("user", u);
				return "modules/sys/updatePwd";
			}

			// 2、再次判断用户的口令时间是否过期
			try {
				now = df.parse(DateUtils.formatDateTime(new Date()));
				date = df.parse(DateUtils.formatDateTime(u.getUpdatePwdDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);

			// 根据用户的ID获取到用户的配置属性
			SysUserAttribute sysUserAttribute = new SysUserAttribute();
			sysUserAttribute.setUserId(u.getId());
			SysUserAttribute sysUser = sysUserAttributeService.get(sysUserAttribute);
			if (sysUser != null) {

				// 口令期限也过期
				if(sysUser.getExpirationDate()!=null){
					if (day > sysUser.getExpirationDate()) {
						model.addAttribute("user", u);
						return "modules/sys/updatePwd";
					}
				}
				

				// 3、判断现在的系统访问时间是否在限制的时间段中
				if(sysUser.getVisitStart()!=null && sysUser.getVisitEnd()!=null){
					String d3 = DateUtils.formatDateTime(new Date());//当前时间
					String d4 = DateUtils.formatDateTime(sysUser.getVisitStart());//访问开始时间
					String d5 = DateUtils.formatDateTime(sysUser.getVisitEnd());//访问结束时间
					long m1 = 0;
					long m2 = 0;
					/* 先转成毫秒并求差 */
					try {
						m1 = (df.parse(d3).getTime() - df.parse(d4).getTime())/(1000*60);
						m2 = (df.parse(d5).getTime() - df.parse(d3).getTime())/(1000*60);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (m1 > 0 && m2 >0) {
						model.addAttribute("message", "您访问受限，该时间段不能访问");
						// 记录登录日志
						LogUtils.saveLogNotUser(Servlets.getRequest(),"您访问受限，该时间段不能访问","1",u.getId());
						UserUtils.clearCache();
						//LogUtils.saveLog(Servlets.getRequest(), "登录失败，访问受限");
						return "modules/sys/sysLogin";
						//return "redirect:" + adminPath + "/loginIndex";
					}
				}
				

				// 4、获取到访问的ip地址，若存在数据库，不让访问
				if(StringUtils.isNotBlank(sysUser.getBoundedIp())){
					String url = request.getRemoteAddr();
					logger.info("获取到的地址=="+StringUtils.getRemoteAddr(Servlets.getRequest()));
					if (url.equals(sysUser.getBoundedIp())) {
						// 若有邮件，则发送邮件提示用户且跳转页面
						SystemConfig config = systemConfigService.get("1");
						if (config != null) {
							// 邮箱不空的时候才会给发邮件
							if (StringUtils.isNotBlank(u.getEmail())) {
								
								boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), ""+config.getPort(),config.getMailName(), config.getMailPassword(), u.getEmail(), "登录失败","您的账号"+u.getLoginName()+"该ip地址不能访问", "1");
								if (isSuccess) {
									// 记录登录日志
									LogUtils.saveLog(Servlets.getRequest(),"邮件发送成功");
								} else {
									LogUtils.saveLog(Servlets.getRequest(),"邮件发送失败");
								}
							}
						}
						addMessage(redirectAttributes, "您访问受限，该ip地址不能访问");
						//model.addAttribute("message", "您访问受限，该ip地址不能访问");
						// 记录登录日志
						LogUtils.saveLogNotUser(Servlets.getRequest(),"登录失败，访问受限","1",u.getId());
						UserUtils.clearCache();
						//LogUtils.saveLog(Servlets.getRequest(), "登录失败，访问受限");
						//return "modules/sys/sysLogin";
						return "redirect:" + adminPath + "/loginIndex";
					}
				}
				
				// 6、获取当前时间，减去锁定时间是否小于锁定时间
				if (StringUtils.isNotBlank(sysUser.getLockStartTime()) && sysUser.getLockTime()!=null) {
					String d1 = sysUser.getLockStartTime();
					String d2 = DateUtils.formatDateTime(new Date());
					long m = 0;
					/* 先转成毫秒并求差 */
					try {
						m = (df.parse(d2).getTime() - df.parse(d1).getTime())/(1000*60);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (m <= sysUser.getLockTime()) {
						//throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
						//addMessage(redirectAttributes, "您的账号已被锁定，请稍后再试");
						model.addAttribute("message",  "您的账号已被锁定，请稍后再试");
						// 记录登录日志
						LogUtils.saveLogNotUser(Servlets.getRequest(),"登录失败，账号被锁定","1",u.getId());
						// 若有邮件，则发送邮件提示用户且跳转页面
						SystemConfig config = systemConfigService.get("1");
						if (config != null) {
							// 邮箱不空的时候才会给发邮件
							if (StringUtils.isNotBlank(u.getEmail())) {
								
								boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), ""+config.getPort(),config.getMailName(), config.getMailPassword(), u.getEmail(), "登录失败","您的账号"+u.getLoginName()+"因连续登录失败被锁定", "1");
								if (isSuccess) {
									// 记录登录日志
									LogUtils.saveLog(Servlets.getRequest(),"邮件发送成功");
								} else {
									LogUtils.saveLog(Servlets.getRequest(),"邮件发送失败");
								}
							}
						}
						UserUtils.clearCache(u);
						//UserUtils.clearCache();
						UserUtils.getSubject().logout();
						//return "redirect:" + adminPath+"/login";
						//return "redirect:" + adminPath + "/loginIndex";
						return "modules/sys/sysLogin";
					}else{
						//当用户达到解锁条件之后，更新用户的锁定状态
						User u2 = new User();
						u2.setLoginFlag("1");//正常状态
						u2.setId(u.getId());
						userDao.updateLoginFlag(u2);
					}
				}
			}
		}
		
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);

		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次刷新功能界面或者是浏览器的界面时，跳转到登录页面重新登录
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		/**
		 * 登录成功后，先检索数据库是否有值，若有值，则只更新登录时间
		 * 若没有值，再增加一条数据
		 * 若两次进来的数据不一样，那就先删除早期的数据，再增加数据
		 */
		SysUserToken sysT = new SysUserToken();
		sysT.setTokenName(u.getLoginName());
		SysUserToken sysT1 = sysUserTokenDao.get(sysT);
		if(sysT1!=null){
			//只使用用户名检索时，若是有值，可以再次加上sessionId检索，
			//若有值，直接更新时间，若没有，直接删除旧数据再次增加数据
			UserUtils.getSession();
			sysT.setSessionId(UserUtils.getSession().getId().toString());
			SysUserToken sysT2 = sysUserTokenDao.get(sysT);
			if(sysT2!=null){
				
				//更新数据
				sysT.setAddTime(new Date());
				sysT.preUpdate();
				sysUserTokenDao.update(sysT);
				
			}else{
				sysUserTokenDao.delete(sysT);
				//新增一条数据
				sysT.setAddTime(new Date());
				sysT.preInsert();
				sysUserTokenDao.insert(sysT);
			}
		}else{
			SysUserToken sysUserToken1 = new SysUserToken();
			sysUserToken1.setTokenName(u.getLoginName());
			sysUserToken1.setSessionId(UserUtils.getSession().getId().toString());
			sysUserToken1.setAddTime(new Date());
			sysUserToken1.preInsert();
			sysUserTokenDao.insert(sysUserToken1);
		}
		
		//如果用户登录成功，则说明该用户没有重复登录，验证用户开始登陆的ip地址
		user.setLoginName(u.getLoginName());
		user.setStartLoginIp(StringUtils.getRemoteAddr(request));
		userDao.updateStartLoginIp(user);
		
		SysOnlineUser onlineUserss = sysOnlineUserDao.get(u.getId());
		if(onlineUserss!=null){
			SysOnlineUser onlineUsersss = new SysOnlineUser();
			onlineUsersss.setUserId(u.getId());
			sysOnlineUserDao.delete(onlineUsersss);
			
			//每次登陆成功的用户，都将数据添加到在线用户管理表当中，当用户退出时，直接删除信息即可，异常退出的定时删除
			SysOnlineUser onlineUser = new SysOnlineUser();
			onlineUser.setUserId(u.getId());
			onlineUser.preInsert();
			sysOnlineUserDao.insert(onlineUser);
			
		}else{
			//每次登陆成功的用户，都将数据添加到在线用户管理表当中，当用户退出时，直接删除信息即可，异常退出的定时删除
			SysOnlineUser onlineUser = new SysOnlineUser();
			onlineUser.setUserId(u.getId());
			onlineUser.preInsert();
			sysOnlineUserDao.insert(onlineUser);
		}
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum = 0;
			//loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	
	
	/**
	 * 验证是否连续登录出错
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isLoginFail(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> isLoginFailMap = (Map<String, Integer>)CacheUtils.get("isLoginFailMap");
		if (isLoginFailMap==null){
			isLoginFailMap = Maps.newHashMap();
			CacheUtils.put("isLoginFailMap", isLoginFailMap);
		}
		Integer loginFailNum = isLoginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum = 0;
			//loginFailNum++;
			isLoginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			isLoginFailMap.remove(useruame);
		}
		
		//根据用户名获取到用户的用户的配置属性，锁定次数
		User user = new User();
		user.setLoginName(useruame);
		User u = userDao1.getByLoginName(user);
		Integer errTimes =0;
		if(u!=null){
			 //根据用户的ID获取到用户的配置属性
			 SysUserAttribute sysUserAttribute = new SysUserAttribute();
			
			 sysUserAttribute.setOffice(UserUtils.getUser().getOffice());
			 sysUserAttribute.setUserId(u.getId());
			 SysUserAttribute sysUser = sysUserAttributeService1.get(sysUserAttribute);
			 if(sysUser!=null){
				 errTimes=sysUser.getAbnormalTimes();
			 }
		}
		//当loginFailNum == errTimes时，创建锁定日期
		//根据用户ID 创建时间
		if(loginFailNum == errTimes){
			 SysUserAttribute sysUserAttribute1 = new SysUserAttribute();
			 sysUserAttribute1.setUserId(u.getId());
			 sysUserAttribute1.setLockStartTime(DateUtils.formatDateTime(new Date()));
			 sysUserAttributeService1.updateStatus(sysUserAttribute1);
		}
		return loginFailNum == errTimes;
	}
	
	
	
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/loginIndex")
	public String loginIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		UserUtils.getSubject().logout();
		return "modules/sys/sysLogin";
	}
	
//	/**
//	 * test
//	 */
//	@RequestMapping(value = "${adminPath}/loginIndex1")
//	public String loginIndex1(HttpServletRequest request, HttpServletResponse response, Model model,User user) {
//		String key="abcdefgabcdefg12";
//		System.out.println("333--"+user.getLoginName());
//		String decrypt=null;
//		try {
//			decrypt = RSACoder.aesDecrypt(user.getLoginName(),key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//	          
//		
//		
//		return "modules/shopping/sha1";
//	}
	
	
	/**
	 * 注销所有的用户
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/logouts", method = RequestMethod.GET)
	public String logouts(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws IOException {
		Principal principal = UserUtils.getPrincipal();
		LogUtils.saveLog(Servlets.getRequest(), "退出登录");
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			//用户注销的时候将登陆的初始登录ip更新为空
			User user = new User();
			user.setId(principal.getId());
			User u = userDao.get(user);
			
			User users = new User();
			users.setStartLoginIp("");
			users.setLoginName(u.getLoginName());
			userDao.updateStartLoginIp(users);
			
			//在用户退出时，清空掉用户的登录痕迹
			SysUserToken sysUserToken = new SysUserToken();
			sysUserToken.setTokenName(u.getLoginName());
			sysUserTokenDao.delete(sysUserToken);
			UserUtils.clearCache();
			UserUtils.getSubject().logout();
		}
	   // 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if(	ajax!=null){
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		 return "";
	}
}
