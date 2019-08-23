package com.wwls.modules.sys.interceptor;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.wwls.common.service.BaseService;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.sys.dao.MenuDao;
import com.wwls.modules.sys.dao.SysRoleMenuDao;
import com.wwls.modules.sys.dao.SysUserRoleDao;
import com.wwls.modules.sys.entity.Menu;
import com.wwls.modules.sys.entity.SysRoleMenu;
import com.wwls.modules.sys.entity.SysUserRole;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 手机端视图拦截器
 * 
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static SysUserRoleDao sysUserRoleDao = SpringContextHolder.getBean(SysUserRoleDao.class);
	private static SysRoleMenuDao sysRoleMenuDao = SpringContextHolder.getBean(SysRoleMenuDao.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
////		Session session1 = UserUtils.getSession();
//		String id = IdGen.uuid();
////		session1.setAttribute("id", id);
////		session1.set
//		HttpSession session1 = request.getSession();
//		session1.setAttribute("id", id);
//		System.out.println("7778=="+session1.getAttribute("id"));
//		System.out.println("7779=="+id);
//		System.out.println("7777=="+session1.getAttributeNames());
//		CookieUtils.setCookie(response, "LOGINED", "true");
//		CookieUtils.setCookie(response, "jeesite.session.id", ""+session1.getAttribute("id"),"/ec-power-server");
//		System.out.println(CookieUtils.getCookie(request, "jeesite.session.id"));
		
		
		/*logger.info("进来了");
		String URI = request.getRequestURI();
		User user = UserUtils.getUser();
		logger.info("用户信息=="+user);
		if (user != null && user.getId() != null) {
			//0、将访问进来的链接放到当前菜单中去遍历，获取到菜单的id，若是能获取到id，则表明不是越权，否则就是越权
			String visitHref = request.getRequestURI();//原始链接
			String URICompare = "";//过滤掉相同的前链接
	    	String URIS = "";//替换和截取后的最终链接
	    	String menuId = "";
	    	//System.out.println("进来的原始链接==="+visitHref);
	    	if(visitHref!=null){
	    		if (visitHref.contains("/ec-power-server/a")) {
					URICompare = visitHref.replaceAll("/ec-power-server/a", "");
					//System.out.println("替换后的链接==="+URICompare);
					if(StringUtils.isNotBlank(URICompare)){
						URIS = URICompare.substring(0,URICompare.lastIndexOf("/")+1);
						//System.out.println("截取后的链接==="+URI);
					}
					
				}
	    	}
			
	    	
	    	if(!visitHref.equals("/ec-power-server/a/sys/user/info") 
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/modifyPwd") 
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/treeData")
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/updatePwd")){
	    		
	    		if(StringUtils.isNotBlank(URIS)){
		    		List<Menu> menuList = UserUtils.getMenuList();
					if(menuList!=null && menuList.size()>0){
						for(Menu m:menuList){
							//System.out.println("数据库的链接==="+m.getHref());
							if(StringUtils.isNotBlank(m.getHref())){
								if(m.getHref().contains(URI)){
									//System.out.println("进来了");
									menuId = m.getId();
									break;
								}
							}
						}
					}
		    	}
	    	}
	    	
	    	if(!visitHref.equals("/ec-power-server/a/sys/user/info") 
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/modifyPwd") 
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/treeData")
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/updatePwd")
	    			&& !visitHref.equals("/ec-power-server/a")){
	    		//第一种判断，若是id为空，则直接是越权的
		    	if(StringUtils.isNotBlank(menuId)){
		    		//将获取到的id作为父级ID,在菜单中检索，将检索出来的id+当前的用户id到权限和菜单的关联中去检索，有则没有越权，否则越权
		    		Menu mu = new Menu();
		    		mu.setParent(new Menu(menuId));
		    		List<Menu> mList = menuDao.findByParentId(mu);
		    		if(mList!=null && mList.size()>0){
		    			for(Menu menu:mList){
		    				boolean tag=false;
		    				//获取到权限id
		    				SysUserRole role = new SysUserRole();
		    				role.setUserId(UserUtils.getUser().getId());
		    				List<SysUserRole> rList = sysUserRoleDao.findList(role);
		    				if(rList!=null && rList.size()>0){
		    					for(SysUserRole r:rList){
		    						SysRoleMenu roleMenu = new SysRoleMenu();
		    						roleMenu.setRoleId(r.getRoleId());
		    						roleMenu.setMenuId(menu.getId());
		    						SysRoleMenu roleMenus = sysRoleMenuDao.get(roleMenu);
		    						if(roleMenus!=null){
		    							//System.out.println("已有权限");
		    							tag = true;
		    							break;
		    						}
		    					}	
		    				}
		    				
		    				if(tag==true){
		    					break;
		    				}
		    			}
		    		}
		    	}else{
		    		//log.setAbnormalType("2");//越权日志
		    		//System.out.println("越权7");
		    		//LogUtils.saveLogNotUser(Servlets.getRequest(), null, null, "越权访问", "2");
		    	}
	    	}
		} else {
			//LogUtils.saveLogNotUser(Servlets.getRequest(), null, null, "越权访问", "2");
		}
		if (logger.isDebugEnabled()) {
			long beginTime = System.currentTimeMillis();// 1、开始时间
			startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
			logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime),request.getRequestURI());
		}*/
		return true;
	}

	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*
		 * if (modelAndView != null){ // 如果是手机或平板访问的话，则跳转到手机视图页面。
		 * if(UserAgentUtils.isMobileOrTablet(request) &&
		 * !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(),
		 * "redirect:")){ modelAndView.setViewName("mobile/" +
		 * modelAndView.getViewName()); } }
		 */
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
