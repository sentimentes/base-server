package com.wwls.modules.sys.security;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wwls.common.config.Global;
import com.wwls.common.mail.MailSendUtils;
import com.wwls.common.security.shiro.session.CacheSessionDAO;
import com.wwls.common.servlet.ValidateCodeServlet;
import com.wwls.common.utils.DateUtils;
import com.wwls.common.utils.Encodes;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.web.Servlets;
import com.wwls.modules.sys.dao.SysPwdManageDao;
import com.wwls.modules.sys.dao.SysTokenDao;
import com.wwls.modules.sys.dao.SysUserTokenDao;
import com.wwls.modules.sys.dao.UserDao;
import com.wwls.modules.sys.entity.Menu;
import com.wwls.modules.sys.entity.Role;
import com.wwls.modules.sys.entity.SysPwdManage;
import com.wwls.modules.sys.entity.SysSessionManage;
import com.wwls.modules.sys.entity.SysSingleLoginManage;
import com.wwls.modules.sys.entity.SysToken;
import com.wwls.modules.sys.entity.SysUserAttribute;
import com.wwls.modules.sys.entity.SysUserToken;
import com.wwls.modules.sys.entity.SystemConfig;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.service.SysSessionManageService;
import com.wwls.modules.sys.service.SysSingleLoginManageService;
import com.wwls.modules.sys.service.SysUserAttributeService;
import com.wwls.modules.sys.service.SystemConfigService;
import com.wwls.modules.sys.service.SystemService;
import com.wwls.modules.sys.utils.LogUtils;
import com.wwls.modules.sys.utils.PwdUtils;
import com.wwls.modules.sys.utils.UserUtils;
import com.wwls.modules.sys.web.LoginController;

/**
 * 系统安全认证实现类

 * @version 2014-7-5
 */
@Service
//@DependsOn({"userDao","roleDao","menuDao"})
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;
	
	private SysSessionManageService sysSessionManageService;
	
	private CacheSessionDAO cacheSessionDAO;
	
	private SysUserTokenDao sysUserTokenDao;
	
	private SysTokenDao sysTokenDao;
	
	private SysUserAttributeService sysUserAttributeService;
	
	private UserDao userDao;
	
	private static SysPwdManageDao sysPwdManageDao;
	
	private static SystemConfigService systemConfigService;
	@Autowired
	private SysSingleLoginManageService sysSingleLoginManageService;
	
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		logger.info("token是====="+token);
		SysSessionManage sysSessionManage = UserUtils.getDate();
		if(sysSessionManage!=null){
			Session session1 = UserUtils.getSession();
			session1.setTimeout(sysSessionManage.getTimeout());
			
			//session1.setTimeout(60000);
			//获取到在线的用户数，当达到数值后，禁止登陆
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(false, null, null);
			if(sessions.size()>=sysSessionManage.getOnlineCount()){
				UserUtils.clearCache();
				throw new AuthenticationException("msg:在线人数已达到最大数, 请稍后再试");
			}
		}
		
		int activeSessionSize = getSystemService().getSessionDao().getActiveSessions(false).size();
		if (logger.isDebugEnabled()){
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}
		// 校验用户名密码
		User user = getSystemService().getUserByLoginName(token.getUsername());
		
		// 校验登录验证码
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
			Session session = UserUtils.getSession();
			
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
				LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，验证码错误","1",user.getId());
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}
		
		
		
		if(user==null){
			LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，未知账号尝试登陆","1","");
			
		}
		logger.info("用户==="+user);
		/**
		 * 校验用户是否已登录还没有退出
		 * 如果没有退出，就先退出后再次登录
		 *//*
		SysUserToken sysUserToken1 = new SysUserToken();
		sysUserToken1.setTokenName(token.getUsername());
		SysUserToken sysUserToken = getSysUserTokenDao().get(sysUserToken1);
		if (sysUserToken != null) {
			// 根据用户名删除
			getSysUserTokenDao().delete(sysUserToken);
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(false, null, null);
			if (sessions.size() > 0) {
				// 如果是登录进来的，则踢出已在线用户
				for (Session session : sessions) {
					logger.info("循环获取的用户的会话是11=====" + session);
					getSystemService().getSessionDao().delete(session);
					// 将所有的ip都修改为空
					getSystemService().updateAllIp(new User());
				}
				//LogUtils.saveLog(Servlets.getRequest(), null, new AuthenticationException(), "msg:账号已在其它地方登录，请重新登录。");
				LogUtils.saveLogNotUser(Servlets.getRequest(), "账号已在其它地方登录，请重新登录。","1",user.getId());
				UserUtils.clearCache();
				throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
			}
		}*/
		
		if (user != null) {
			if (Global.locked.equals(user.getLoginFlag())){
				// 根据用户的ID获取到用户的配置属性
				SysUserAttribute sysUserAttribute = new SysUserAttribute();
				sysUserAttribute.setUserId(user.getId());
				SysUserAttribute sysUser = getSysUserAttributeService().get(sysUserAttribute);
				if (sysUser != null) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
						if (m >= sysUser.getLockTime()) {
							//当用户达到解锁条件之后，同时也更新用户的状态
							User u2 = new User();
							u2.setLoginFlag("1");//锁定状态
							u2.setId(user.getId());
							getUserDao().updateLoginFlag(u2);
						}
				}
					
				UserUtils.clearCache();
				LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，该帐号已锁定","1",user.getId());
				//LogUtils.saveLog(Servlets.getRequest(), null,new AuthenticationException() ,"msg:账号已在其它地方登录，请重新登录。");
				throw new AuthenticationException("msg:该帐号已锁定，请稍后再试");
			}
			}
			if (Global.logout.equals(user.getLoginFlag())){
				UserUtils.clearCache();
				LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，该帐号已注销","1",user.getId());
				throw new AuthenticationException("msg:该帐号已注销，请联系管理员");
			}
			if (User.DEL_FLAG_DELETE.equals(user.getDelFlag())){
				UserUtils.clearCache();
				LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，该帐号已删除","1",user.getId());
				throw new AuthenticationException("msg:该帐号已删除，请联系管理员");
			}

			/*
			 * author:leijinlian
			 * date:2018-08-06
			 * decription:存储登录成功的sessionid,userid
			 * */
			SysSingleLoginManage sysSingleLoginManage  = new SysSingleLoginManage();
			sysSingleLoginManage.setUserId(user.getId());
			sysSingleLoginManage.setSessionId(UserUtils.getSession().getId().toString());
			sysSingleLoginManage.setCreateDate(new Date());
			sysSingleLoginManageService.save(sysSingleLoginManage);
			
			//记录下用户名和密码和时间，当用户名和密码存在时判断时间遳是否在时间之内
			SysToken sys = new SysToken();
			sys.setUserName(user.getLoginName());
			sys.setPassword(user.getPassword());
			sys.setAddTime(new Date());
			sys.preInsert();
			getSysTokenDao().insert(sys);
		 
			/**
			 * 1获取到备份表中的加密密码
			 * 2解密密码
			 * 3将密码串分开
			 * 4sha-1后和用户表对比，能对比上
			 */
			
			//将用户名和密文密码从数据库取出来
			SysPwdManage sysP = new SysPwdManage();
			sysP.setLoginName(user.getLoginName());
			SysPwdManage sysPwd = getSysPwdManageDao().get(sysP);
			
			//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
			String pwd = PwdUtils.decryptPsw(sysPwd.getPwd());//整密码串
			//String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
			
			//将密文密码sha-1
			//String shaPw1 = PwdUtils.entryptPassword(pwdStr);//生成sha-1密文密码
			//和用户表的对比
			
			if(StringUtils.isNotBlank(pwd)){
				String pwdStr = pwd.substring(pwd.indexOf(",")+1);//明文密码
//				System.out.println("000="+pwdStr);
//				System.out.println("111="+user.getPassword());
				boolean str = PwdUtils.validatePassword(pwdStr,user.getPassword());
				if(str==false){
					UserUtils.clearCache();
					LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，口令完整性被破坏，请联系管理员","1",user.getId());
					
					SystemConfig config = getSystemConfigService().get("1");
					if (config != null) {
						// 邮箱不空的时候才会给发邮件
						//System.out.println("0001="+user.getEmail());
						if (StringUtils.isNotBlank(user.getEmail())) {
							
							boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), ""+config.getPort(),config.getMailName(), config.getMailPassword(), user.getEmail(), "登录失败","您的账号"+user.getLoginName()+"口令完整性被破坏，请联系管理员", "1");
							if (isSuccess) {
								// 记录登录日志
								LogUtils.saveLog(Servlets.getRequest(),"邮件发送成功");
							} else {
								LogUtils.saveLog(Servlets.getRequest(),"邮件发送失败");
							}
						}
					}
					
					//LogUtils.saveLog(Servlets.getRequest(), "登录失败，口令完整性被破坏");
					throw new AuthenticationException("msg:登录失败，口令完整性被破坏，请联系管理员");
				}
			}else{
				UserUtils.clearCache();
				//LogUtils.saveLog(Servlets.getRequest(), "登录失败，口令完整性被破坏");
				SystemConfig config = getSystemConfigService().get("1");
				if (config != null) {
					//System.out.println("0002="+user.getEmail());
					// 邮箱不空的时候才会给发邮件
					if (StringUtils.isNotBlank(user.getEmail())) {
						
						boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), ""+config.getPort(),config.getMailName(), config.getMailPassword(), user.getEmail(), "登录失败","您的账号"+user.getLoginName()+"口令完整性被破坏，请联系管理员", "1");
						if (isSuccess) {
							// 记录登录日志
							LogUtils.saveLog(Servlets.getRequest(),"邮件发送成功");
						} else {
							LogUtils.saveLog(Servlets.getRequest(),"邮件发送失败");
						}
					}
				}
				LogUtils.saveLogNotUser(Servlets.getRequest(), "登录失败，口令完整性被破坏，请联系管理员","1",user.getId());
				throw new AuthenticationException("msg:登录失败，口令完整性被破坏，请联系管理员");
			}
			
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			//System.out.println("000==="+salt);
			AuthenticationInfo a = new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()),user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
			//System.out.println("1111==="+a);
//			System.out.println("000==="+a);
//			System.out.println("0001==="+ByteSource.Util.bytes(salt));
//			System.out.println("0002==="+getName());
//			System.out.println("0003==="+user);
//			System.out.println("0004==="+ token.isMobileLogin());
//			System.out.println("0006==="+ user.getPassword().substring(16));
			return a;
		} else {
			return null;
		}
		
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		logger.info("principal是====="+principal);
		Session session1 = UserUtils.getSession();
		logger.info("当前的session是====="+session1.getId());
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(false, principal, UserUtils.getSession());
			SysSingleLoginManage singleLoginMange = new SysSingleLoginManage();
			String userId = principal.getId();
			singleLoginMange.setUserId(userId);
			singleLoginMange.setSessionId(session1.getId().toString());
			/*author:leijinlian
			 *date:2018-08-06
			 *description:查询当前用户非最新一条的session历史记录，和当前active的sessionlist比较，相同则删除该历史session
			*/
			List<SysSingleLoginManage> singleLoginList = sysSingleLoginManageService.getOldList(singleLoginMange);
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						logger.info("循环获取的用户的会话是====="+session);
						for(SysSingleLoginManage sso:singleLoginList) {
							String sessionId = sso.getSessionId();
							if(sessionId.equals(session.getId())) {
								getSystemService().getSessionDao().delete(session);
								//将所有的ip都修改为空
								getSystemService().updateAllIp(new User());
							}
						}
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.clearCache();
					UserUtils.getSubject().logout();
					LogUtils.saveLogNotUser(Servlets.getRequest(), "账号已在其它地方登录，请重新登录。","1",principal.getId());
					//LogUtils.saveLog(Servlets.getRequest(), null,new AuthenticationException("msg:账号已在其它地方登录，请重新登录。"),"msg:账号已在其它地方登录，请重新登录。");
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
			/*author:leijinlian
			 *date:2018-08-06
			 *description:每个用户只保留最新一条的登录session信息，删除其他的旧session数据
			*/
			SysSingleLoginManage dsso = new SysSingleLoginManage();
			dsso.setUserId(userId);
			sysSingleLoginManageService.deleteOldSessions(dsso);
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			//重置sessionid
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()){
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}

	/**
	 * 清空所有关联认证
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	
	/**
	 * 获取系统业务对象
	 */
	public SysSessionManageService getSysSessionManageService() {
		if (sysSessionManageService == null){
			sysSessionManageService = SpringContextHolder.getBean(SysSessionManageService.class);
		}
		return sysSessionManageService;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public CacheSessionDAO getCacheSessionDAO() {
		if (cacheSessionDAO == null){
			cacheSessionDAO = SpringContextHolder.getBean(CacheSessionDAO.class);
		}
		return cacheSessionDAO;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SysUserTokenDao getSysUserTokenDao() {
		if (sysUserTokenDao == null){
			sysUserTokenDao = SpringContextHolder.getBean(SysUserTokenDao.class);
		}
		return sysUserTokenDao;
	}
	
	
	/**
	 * 获取系统业务对象
	 */
	public SysUserAttributeService getSysUserAttributeService() {
		if (sysUserAttributeService == null){
			sysUserAttributeService = SpringContextHolder.getBean(SysUserAttributeService.class);
		}
		return sysUserAttributeService;
	}
	
	
	/**
	 * 获取系统业务对象
	 */
	public SysTokenDao getSysTokenDao() {
		if (sysTokenDao == null){
			sysTokenDao = SpringContextHolder.getBean(SysTokenDao.class);
		}
		return sysTokenDao;
	}
	
	
	/**
	 * 获取系统业务对象
	 */
	public SysPwdManageDao getSysPwdManageDao() {
		if (sysPwdManageDao == null){
			sysPwdManageDao = SpringContextHolder.getBean(SysPwdManageDao.class);
		}
		return sysPwdManageDao;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SystemConfigService getSystemConfigService() {
		if (systemConfigService == null){
			systemConfigService = SpringContextHolder.getBean(SystemConfigService.class);
		}
		return systemConfigService;
	}
	
	
	
	/**
	 * 获取系统业务对象
	 */
	public UserDao getUserDao() {
		if (userDao == null){
			userDao = SpringContextHolder.getBean(UserDao.class);
		}
		return userDao;
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		
//		private Map<String, Object> cacheMap;

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return id;
		}

	}
}
