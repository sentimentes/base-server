package com.wwls.modules.sys.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.utils.DateUtils;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.utils.excel.ExportExcel;
import com.wwls.common.utils.excel.ImportExcel;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.entity.SystemConfig;
import com.wwls.modules.sys.service.SystemConfigService;
import com.wwls.modules.sys.utils.FilterString;
import com.wwls.modules.sys.utils.LogUtils;
import com.wwls.modules.sys.utils.PwdUtils;

/**
 * 系统配置Controller
 * @author liugf
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/systemConfig")
public class SystemConfigController extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@ModelAttribute
	public SystemConfig get(@RequestParam(required=false) String id) {
		SystemConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = systemConfigService.get(id);
		}
		if (entity == null){
			entity = new SystemConfig();
		}
		return entity;
	}
	
	/**
	 * 系统配置列表页面
	 */
	@RequiresPermissions("sys:systemConfig:view")
	@RequestMapping(value = {"list", ""})
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemConfig systemConfig = systemConfigService.get("1");
		model.addAttribute("systemConfig", systemConfig);
		return "modules/sys/systemConfigList";
	}

	
	/**
	 * 邮件后台账号配置表单
	 * @param area
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:systemConfig:view")
	@RequestMapping(value = "form")
	public String form(SystemConfig systemConfig, Model model) {
		model.addAttribute("systemConfig", systemConfig);
		return "modules/sys/systemConfigForm";
	}

	/**
	 * 邮件信息添加
	 * @param area
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:systemConfig:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,SystemConfig systemConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, systemConfig)){
			return form(systemConfig, model);
		}
		
		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd = PwdUtils.decryptPsw(systemConfig.getMailPassword());//整密码串
		String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
		systemConfig.setMailPassword(pwdMing);
		
		String filterStr = FilterString.FilterEmail(systemConfig.getMailName());
		systemConfig.setMailName(filterStr);
		if(!systemConfig.getSmtp().trim().equals("smtp.163.com") && 
				!systemConfig.getSmtp().trim().equals("smtp.126.com") &&
				!systemConfig.getSmtp().trim().equals("smtp.qq.com")){
			
			addMessage(redirectAttributes, "保存失败，请输入正确的邮箱服务地址");
			return "redirect:" + adminPath + "/sys/systemConfig/form?id="+1;
		}
		
		String updateBegin = "";//修改前数据
		String updateAfter = "";//修改后数据
		SystemConfig systemConfigs = systemConfigService.get("1");
		if(systemConfigs!=null){
			updateBegin = "[邮箱服务器地址]="+systemConfigs.getSmtp()+
					"[邮箱服务器端口]="+systemConfigs.getPort()+
					"[系统邮箱地址]="+systemConfigs.getMailName();
		}
		
		systemConfigService.save(systemConfig);
		updateAfter = "[邮箱服务器地址]="+systemConfig.getSmtp()+
				"[邮箱服务器端口]="+systemConfig.getPort()+
				"[系统邮箱地址]="+systemConfig.getMailName();
		LogUtils.saveLogUser(request,"系统设置-系统设置-邮箱配置-修改", updateBegin, updateAfter,"1","5");
		addMessage(redirectAttributes, "保存配置成功");
		return "redirect:" + adminPath + "/sys/systemConfig/form?id="+1;
	}
	
	/**
	 * 删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SystemConfig systemConfig, RedirectAttributes redirectAttributes) {
		systemConfigService.delete(systemConfig);
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 批量删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			systemConfigService.delete(systemConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:systemConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SystemConfig systemConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SystemConfig> page = systemConfigService.findPage(new Page<SystemConfig>(request, response, -1), systemConfig);
    		new ExportExcel("系统配置", SystemConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SystemConfig> list = ei.getDataList(SystemConfig.class);
			for (SystemConfig systemConfig : list){
				systemConfigService.save(systemConfig);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统配置记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	
	/**
	 * 下载导入系统配置数据模板
	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置数据导入模板.xlsx";
    		List<SystemConfig> list = Lists.newArrayList(); 
    		new ExportExcel("系统配置数据", SystemConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	

}