/**

 */
package com.wwls.modules.sys.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wwls.common.config.Global;
import com.wwls.common.utils.CacheUtils;
import com.wwls.common.utils.Exceptions;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.Servlets;
import com.wwls.modules.sys.dao.LogDao;
import com.wwls.modules.sys.dao.MenuDao;
import com.wwls.modules.sys.dao.SysRoleMenuDao;
import com.wwls.modules.sys.dao.SysUserRoleDao;
import com.wwls.modules.sys.entity.Log;
import com.wwls.modules.sys.entity.Menu;
import com.wwls.modules.sys.entity.SysRoleMenu;
import com.wwls.modules.sys.entity.SysUserRole;
import com.wwls.modules.sys.entity.User;

/**
 * 字典工具类

 * @version 2014-11-7
 */
public class LogUtils {
	
	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
	
	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static SysUserRoleDao sysUserRoleDao = SpringContextHolder.getBean(SysUserRoleDao.class);
	private static SysRoleMenuDao sysRoleMenuDao = SpringContextHolder.getBean(SysRoleMenuDao.class);
	
	
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		User user = UserUtils.getUser();
		if (user != null && user.getId() != null){
			Log log = new Log();
			log.setTitle(title);
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setAbnormalType("5");//正常日志

			
			if(user.getId().equals("57818d419ca744c699d1340f94fb61f3") 
					||request.getRequestURI().equals("/ec-power-server/a") 
					||request.getRequestURI().equals("/ec-power-server/a/logout")
					||request.getRequestURI().contains("/ec-power-server/a/login")){
				log.setLogType("1");
			}else{
				log.setLogType("2");
			}
			
			if(request.getRequestURI().contains("/ec-power-server/a/sys/user/")){
				log.setLogType("1");
			}
			log.setResultType("1");//成功
			
			//0、将访问进来的链接放到当前菜单中去遍历，获取到菜单的id，若是能获取到id，则表明不是越权，否则就是越权
			String visitHref = request.getRequestURI();//原始链接
			String URICompare = "";//过滤掉相同的前链接
	    	String URI = "";//替换和截取后的最终链接
	    	String menuId = "";
	    	//System.out.println("进来的原始链接==="+visitHref);
	    	if(visitHref!=null){
	    		if (visitHref.contains("/ec-power-server/a")) {
					URICompare = visitHref.replaceAll("/ec-power-server/a", "");
					//System.out.println("替换后的链接==="+URICompare);
					if(StringUtils.isNotBlank(URICompare)){
						URI = URICompare.substring(0,URICompare.lastIndexOf("/")+1);
						//System.out.println("截取后的链接==="+URI);
					}
					
				}
	    	}
			
	    	
	    	if(!visitHref.equals("/ec-power-server/a/sys/user/treeData")
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/updatePwd")
	    			&& !visitHref.equals("/ec-power-server/a/logout")
	    			&& !visitHref.equals("/ec-power-server/a/login")){
	    		
	    		if(StringUtils.isNotBlank(URI)){
		    		List<Menu> menuList = UserUtils.getMenuList();
					if(menuList!=null && menuList.size()>0){
						for(Menu m:menuList){
							//System.out.println("数据库的链接==="+m.getHref());
							if(StringUtils.isNotBlank(m.getHref())){
								if(m.getHref().contains(URI)){
								//if(URI.contains(m.getHref())){
									//System.out.println("进来了");
									menuId = m.getId();
									
									Menu mu = new Menu();
						    		mu.setParent(new Menu(menuId));
						    		break;
//						    		List<Menu> mList = menuDao.findByParentId(mu);
//						    		if(mList!=null && mList.size()>0){
//						    			break;
//						    		}else{
//						    			if(!m.getHref().equals(visitHref)){
//						    				System.out.println("又进来了");
//						    			}
//						    		}
								}
							}
						}
					}
		    	}
	    	}
	    	
	    	if(!visitHref.equals("/ec-power-server/a/sys/user/treeData")
	    			&& !visitHref.equals("/ec-power-server/a/sys/user/updatePwd")
	    			&& !visitHref.equals("/ec-power-server/a")
	    			&& !visitHref.equals("/ec-power-server/a/login")
	    			&& !visitHref.equals("/ec-power-server/a/logout")){
	    		//第一种判断，若是id为空，则直接是越权的
		    	if(StringUtils.isNotBlank(menuId)){
		    		//将获取到的id作为父级ID,在菜单中检索，将检索出来的id+当前的用户id到权限和菜单的关联中去检索，有则没有越权，否则越权
		    		Menu mu = new Menu();
		    		mu.setParent(new Menu(menuId));
		    		List<Menu> mList = menuDao.findByParentId(mu);
		    		if(mList!=null && mList.size()>0){
		    			//System.out.println("是否来到这里");
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
		    							
		    							// 异步保存日志
		    							if(log.getMethod().equals("GET") && !log.getRequestUri().contains("delete")){
		    								new SaveLogThread(log, handler, ex).start();
		    							}
		    							
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
//		    		else{
//		    			
//		    		}
//		    			else{
//		    			log.setAbnormalType("2");//越权日志
//		    			//LogUtils.saveLogNotUser(Servlets.getRequest(), null, null, "越权访问", "2",null);
//		    		}
		    	}else{
		    		LogUtils.saveLogNotUser(Servlets.getRequest(), null, null, "越权访问", "2",null);
		    		//log.setAbnormalType("2");//越权日志
		    		//System.out.println("越权7");
		    	}
	    	}else if(log.getRequestUri().equals("/ec-power-server/a") || log.getRequestUri().equals("/ec-power-server/a/login") ||
	    			log.getRequestUri().equals("/ec-power-server/a/logout")){
	    		new SaveLogThread(log, handler, ex).start();
	    	}
	    	
			
		}
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					Method m = ((HandlerMethod)handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
					
				}
				log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
				return;
			}
			if(StringUtils.isNotBlank(log.getException())){
				log.setResultType("2");//结果失败
				log.setAbnormalType("3");
			}else{
				if(StringUtils.isNotBlank(log.getAbnormalType())){
					log.setAbnormalType(log.getAbnormalType());
				}
//				else{
//					log.setAbnormalType("2");
//					//LogUtils.saveLogNotUser(Servlets.getRequest(), null, null, "越权访问", "2",null);
//				}
				
			}
			// 保存日志信息
			log.preInsert();
			logDao.insert(log);
		}
	}

	/**
	 * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(String requestUri, String permission){
		String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
		@SuppressWarnings("unchecked")
		Map<String, String> menuMap = (Map<String, String>)CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
		if (menuMap == null){
			menuMap = Maps.newHashMap();
			List<Menu> menuList = menuDao.findAllList(new Menu());
			for (Menu menu : menuList){
				// 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
				String namePath = "";
				if (menu.getParentIds() != null){
					List<String> namePathList = Lists.newArrayList();
					for (String id : StringUtils.split(menu.getParentIds(), ",")){
						if (Menu.getRootId().equals(id)){
							continue; // 过滤跟节点
						}
						for (Menu m : menuList){
							if (m.getId().equals(id)){
								namePathList.add(m.getName());
								break;
							}
						}
					}
					namePathList.add(menu.getName());
					namePath = StringUtils.join(namePathList, "-");
				}
				// 设置菜单名称路径
				if (StringUtils.isNotBlank(menu.getHref())){
					menuMap.put(menu.getHref(), namePath);
				}else if (StringUtils.isNotBlank(menu.getPermission())){
					for (String p : StringUtils.split(menu.getPermission())){
						menuMap.put(p, namePath);
					}
				}
				
			}
			CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
		}
		String menuNamePath = menuMap.get(href);
		if (menuNamePath == null){
			for (String p : StringUtils.split(permission)){
				menuNamePath = menuMap.get(p);
				if (StringUtils.isNotBlank(menuNamePath)){
					break;
				}
			}
			if (menuNamePath == null){
				return "";
			}
		}
		return menuNamePath;
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLogNotUser(HttpServletRequest request, String title,String abnormalType,String userId){
		saveLogNotUser(request, null, null, title,abnormalType,userId);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLogNotUser(HttpServletRequest request, Object handler, Exception ex, String title,String abnormalType,String userId){
		//if (user != null && user.getId() != null){
			Log log = new Log();
			log.setTitle(title);
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);//1正常日志2错误日志
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setAbnormalType(abnormalType);
			log.setResultType("2");//失败
			log.setLogType("1");//系统日志
			log.setCreateBy(new User(userId));
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		//}
	}
	
	
	
	/**
	 * 保存详细日志
	 */
	public static void saveLogUser(HttpServletRequest request, String title,String updateBegin,String updateAfter,String logType,String abnormalType){
		saveLogUser(request, null, null, title,updateBegin,updateAfter,logType,abnormalType);
	}
	
	/**
	 * 保存详细日志
	 */
	public static void saveLogUser(HttpServletRequest request, Object handler, Exception ex, String title,String updateBegin,String updateAfter,String logType,String abnormalType){
		
		Log log = new Log();
		log.setTitle(title);
		log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);// 1正常日志2错误日志
		log.setRemoteAddr(StringUtils.getRemoteAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setRequestUri(request.getRequestURI());
		log.setParams(request.getParameterMap());
		log.setMethod(request.getMethod());
		log.setAbnormalType(abnormalType);
		log.setResultType("1");// 成功
		log.setLogType(logType);// 日志类型，1系统日志2业务日志
		log.setUpdateBegin(updateBegin);// 修改前的数据
		log.setUpdateAfter(updateAfter);// 修改后的数据

		log.preInsert();
		logDao.insert(log);
		// 异步保存日志
		//new SaveLogThreads(log, handler, ex).start();
	}
	
}
