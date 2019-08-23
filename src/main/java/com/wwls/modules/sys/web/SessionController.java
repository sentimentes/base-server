package com.wwls.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wwls.common.utils.SpringContextHolder;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.entity.Log;
import com.wwls.modules.sys.service.SystemService;

/**
 * 日志Controller

 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/session")
public class SessionController extends BaseController {
	private SystemService systemService;
	@RequiresPermissions("sys:session:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		 	String FileName="";
	        HttpSession session=request.getSession();//获取session
	        Object name=session.getAttribute("username");
	        System.out.println(name);
//	        Principal principal = (Principal) getAvailablePrincipal(principals);
//	        Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
//	        Enumeration enumeration =session.getAttributeNames();//获取session中所有的键值对
//	        //遍历enumeration中的键值对
//	        String[] names=session.getValueNames();
//	        for(int i=0;i<names.length;i++){
//	            if(names[i].equals("AddFileName")){
//	                FileName+=session.getValue(names[i])+"@";
//	            System.out.println(FileName);
//	            }
//	        }
		//Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
        //model.addAttribute("page", page);
		return "modules/sys/sessionList";
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

}
